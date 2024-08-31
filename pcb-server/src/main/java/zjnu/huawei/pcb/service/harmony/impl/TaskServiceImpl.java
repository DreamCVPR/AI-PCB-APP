package zjnu.huawei.pcb.service.harmony.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
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
import zjnu.huawei.pcb.utils.file.FileUtils;
import zjnu.huawei.pcb.utils.file.MinioUtil;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Array;
import java.util.*;

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
        List<TaskEntity> counts = taskMapper.countDetectImg(harmonyUserId);
        List<TaskEntity> taskList = taskMapper.queryById(harmonyUserId);
        JSONObject taskMapCount = new JSONObject();
        for (TaskEntity count : counts) {
            taskMapCount.put(count.getTaskId().toString(), new Integer[]{count.getCountDetectImg(), count.getCountAllImg()});
        }
        for (TaskEntity taskEntity : taskList) {
            JSONArray count = taskMapCount.getJSONArray(taskEntity.getTaskId().toString());
            taskEntity.setCountDetectImg(count.getInteger(0));
            taskEntity.setCountAllImg(count.getInteger(1));
        }
        return taskList;
    }

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public Integer add(TaskDTO taskDTO) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        TaskEntity taskEntity = mapperFactory.getMapperFacade().map(taskDTO, TaskEntity.class);
        taskEntity.setTaskState(1);
        taskEntity.setGmtCreate(new Date());
        Integer res = taskMapper.add(taskEntity);
        JSONObject base64Files = taskDTO.getBase64Files();
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    List<MultipartFile> files = FileUtils.base642MultipartFileList(base64Files.getJSONArray("files"), base64Files.getJSONArray("fileNames"));
                    if (files.size() > 0) {
                        List<TaskImgDTO> taskImgList = new ArrayList<>();
                        for (int i = 0; i < files.size(); i++) {
                            String fileName = files.get(i).getOriginalFilename();
                            String saveName= taskDTO.getHarmonyUserId() + "_"+i+(System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf('.')));
                            minioUtil.upload(files.get(i), "image/" + saveName);
                            taskImgList.add(new TaskImgDTO(fileName, saveName, 0, taskEntity.getTaskId(), new Date()));
                        }
                        taskImgService.addList(taskImgList);
                    }
                } finally {
                    taskEntity.setTaskState(0);
                    taskMapper.update(taskEntity);
                }
            }
        });
        thread.start();
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
//    @Transactional(rollbackFor = Exception.class)
    public Integer detect(Long taskId) throws Exception {
        List<TaskImgEntity> taskImgList = taskImgMapper.queryNotDetectById(taskId);
        if (taskImgList.size() > 0) {
            taskMapper.updateState(new TaskEntity(taskId, 2));
            Thread thread = new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    try {
                        for (TaskImgEntity taskImgEntity : taskImgList) {
                            detectOne(taskImgEntity);
                        }
                    } finally {
                        taskMapper.updateState(new TaskEntity(taskId, 0));
                    }
                }
            });
            thread.start();
        }
        return taskImgList.size();
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
        taskImgMapper.update(taskImgEntity);
        return 1;
    }
}
