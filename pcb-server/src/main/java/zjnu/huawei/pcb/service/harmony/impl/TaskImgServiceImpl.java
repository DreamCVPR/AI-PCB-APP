package zjnu.huawei.pcb.service.harmony.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import org.springframework.web.multipart.MultipartFile;
import zjnu.huawei.pcb.dto.harmony.TaskImgDTO;
import zjnu.huawei.pcb.entity.harmony.TaskEntity;
import zjnu.huawei.pcb.entity.harmony.TaskImgEntity;
import zjnu.huawei.pcb.mapper.harmony.TaskImgMapper;
import zjnu.huawei.pcb.mapper.harmony.TaskMapper;
import zjnu.huawei.pcb.service.basic.ServiceService;
import zjnu.huawei.pcb.service.harmony.TaskImgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zjnu.huawei.pcb.utils.file.FileUtils;
import zjnu.huawei.pcb.utils.file.MinioUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskImgServiceImpl implements TaskImgService {
    @Resource
    private ServiceService serviceService;

    @Resource
    private TaskImgMapper taskImgMapper;
    @Resource
    private TaskMapper taskMapper;

    @Resource
    private MinioUtil minioUtil;
    @Override
    public List<TaskImgEntity> query(Long taskId) throws Exception {
        return taskImgMapper.queryById(taskId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(JSONObject jsonObject) throws Exception {
        JSONArray imgBase64List = jsonObject.getJSONArray("files");
        JSONArray imgNameList = jsonObject.getJSONArray("fileNames");
        List<MultipartFile> files = FileUtils.base642MultipartFileList(imgBase64List, imgNameList);
        if (files.size() > 0) {
            List<TaskImgDTO> taskImgList = new ArrayList<>();
            List<String> fileUrlList = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                String fileName = files.get(i).getOriginalFilename();
                String saveName= jsonObject.getString("harmonyUserId") + "_"+i+(System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf('.')));
                String fileUrl = minioUtil.upload(files.get(i), saveName);
                if (fileUrl != null) fileUrlList.add(fileUrl);
                taskImgList.add(new TaskImgDTO(fileName, saveName, 0, jsonObject.getLong("taskId"), new Date()));
            }
            try {
                addList(taskImgList);
            } catch (Exception e) {
                for (String fileUrl : fileUrlList) {
                    minioUtil.remove(fileUrl);
                }
                throw e;
            }
        }
        return imgNameList.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addList(List<TaskImgDTO> taskImgList) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        List<TaskImgEntity> entityList = new ArrayList<>();
        for (TaskImgDTO taskImgDTO : taskImgList) {
            TaskImgEntity taskImgEntity = mapperFactory.getMapperFacade().map(taskImgDTO, TaskImgEntity.class);
            taskImgEntity.setIsDetect(0);
            taskImgEntity.setGmtCreate(new Date());
            entityList.add(taskImgEntity);
        }
        return taskImgMapper.addList(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TaskImgDTO taskImgDTO) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        TaskImgEntity taskImgEntity = mapperFactory.getMapperFacade().map(taskImgDTO, TaskImgEntity.class);
        return taskImgMapper.update(taskImgEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer remove(String taskImgIds) throws Exception {
        String[] taskImgId = taskImgIds.split(",");
        Date removeDate = new Date();
        Integer res = 0;
        for (String id : taskImgId) {
            res += taskImgMapper.softRemove(Long.parseLong(id), removeDate);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer changeTask(JSONObject jsonObject) throws Exception {
        String[] imgIds = jsonObject.getString("imgIds").split(",");
        for (String imgId : imgIds) {
            taskImgMapper.changeTask(imgId, jsonObject.getLong("taskId"));
        }
        return imgIds.length;
    }

    @Override
    public Integer detect(List<TaskImgEntity> taskImgList) throws Exception {
        Integer res = 0;
        if (taskImgList.size() > 0) {
            taskMapper.updateState(new TaskEntity(taskImgList.get(0).getTaskId(), 2));
            try {
                for (TaskImgEntity taskImgEntity : taskImgList) {
                    res += detectOne(taskImgEntity);
                }
            } finally {
                taskMapper.updateState(new TaskEntity(taskImgList.get(0).getTaskId(), 0));
            }
        }
        return res;
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer detectOne(TaskImgEntity taskImgEntity) throws Exception {
        JSONObject pred = serviceService.predict(minioUtil.getFile(taskImgEntity.getImgUrl(), taskImgEntity.getImgName()));
        if (pred.getString("detection_classes") == null) {
            return 0;
        }
        taskImgEntity.setIsDetect(1);
        taskImgEntity.setDetectionClasses(pred.getString("detection_classes"));
        taskImgEntity.setDetectionBoxes(pred.getString("detection_boxes"));
        taskImgEntity.setDetectionScores(pred.getString("detection_scores"));
        return taskImgMapper.updateDetect(taskImgEntity);
    }
}
