package zjnu.huawei.pcb.service.harmony.impl;

import com.alibaba.fastjson.JSONObject;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import zjnu.huawei.pcb.dto.harmony.TaskDTO;
import zjnu.huawei.pcb.dto.harmony.TaskImgDTO;
import zjnu.huawei.pcb.entity.harmony.TaskEntity;
import zjnu.huawei.pcb.entity.harmony.TaskImgEntity;
import zjnu.huawei.pcb.mapper.harmony.TaskImgMapper;
import zjnu.huawei.pcb.mapper.harmony.TaskMapper;
import zjnu.huawei.pcb.service.basic.ServiceService;
import zjnu.huawei.pcb.service.harmony.TaskImgService;
import zjnu.huawei.pcb.service.harmony.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zjnu.huawei.pcb.utils.file.MinioUtil;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskImgMapper taskImgMapper;

    @Resource
    private TaskImgService taskImgService;
    @Resource
    private ServiceService serviceService;

    @Resource
    private MinioUtil minioUtil;

    @Override
    public List<TaskEntity> query(Long harmonyUserId) throws Exception {
        return taskMapper.queryById(harmonyUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer add(TaskDTO taskDTO) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        TaskEntity taskEntity = mapperFactory.getMapperFacade().map(taskDTO, TaskEntity.class);
        taskEntity.setTaskState(1);
        taskEntity.setGmtCreate(new Date());
        Integer res = taskMapper.add(taskEntity);
        List<MultipartFile> files = taskDTO.getFiles();
        if (files != null && files.size() > 0) {
            try {
                List<TaskImgDTO> taskImgList = new ArrayList<>();
                for (int i = 0; i < files.size(); i++) {
                    String fileName = files.get(i).getOriginalFilename();
                    String saveName= taskDTO.getHarmonyUserId() + "_"+i+(System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf('.')));
                    minioUtil.upload(files.get(i), "image/" + saveName);
                    taskImgList.add(new TaskImgDTO(fileName, saveName, 0, taskEntity.getTaskId(), new Date()));
                }
                taskImgService.addList(taskImgList);
            } finally {
                taskEntity.setTaskState(0);
                taskMapper.update(taskEntity);
            }
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(TaskDTO taskDTO) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        TaskEntity taskEntity = mapperFactory.getMapperFacade().map(taskDTO, TaskEntity.class);
        return taskMapper.update(taskEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateState(Long taskId, Integer state) throws Exception {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskId(taskId);
        taskEntity.setTaskState(state);
        return taskMapper.updateState(taskEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer remove(String taskIds) throws Exception {
        String[] taskId = taskIds.split(",");
        Integer res = 0;
        for (String id : taskId) {
            res += taskMapper.remove(Long.parseLong(id));
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer detect(Long taskId) throws Exception {
        List<TaskImgEntity> taskImgList = taskImgMapper.queryById(taskId);
        if (taskImgList.size() > 0) {
            taskMapper.updateState(new TaskEntity(taskId, 2));
            for (TaskImgEntity taskImgEntity : taskImgList) {
                JSONObject pred = serviceService.predict(minioUtil.getFile(taskImgEntity.getImgUrl(), taskImgEntity.getImgName()));
                if (pred.getString("detection_classes") == null) {
                    continue;
                }
                taskImgEntity.setIsDetect(1);
                taskImgEntity.setDetectionClasses(pred.getString("detection_classes"));
                taskImgEntity.setDetectionBoxes(pred.getString("detection_boxes"));
                taskImgEntity.setDetectionScores(pred.getString("detection_scores"));
                taskImgMapper.update(taskImgEntity);
            }
            taskMapper.updateState(new TaskEntity(taskId, 0));
        }
        return taskImgList.size();
    }
}
