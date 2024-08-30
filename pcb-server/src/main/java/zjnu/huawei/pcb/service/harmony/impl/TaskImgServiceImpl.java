package zjnu.huawei.pcb.service.harmony.impl;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import zjnu.huawei.pcb.dto.harmony.TaskImgDTO;
import zjnu.huawei.pcb.entity.harmony.TaskImgEntity;
import zjnu.huawei.pcb.mapper.harmony.TaskImgMapper;
import zjnu.huawei.pcb.mapper.harmony.TaskMapper;
import zjnu.huawei.pcb.service.basic.ServiceService;
import zjnu.huawei.pcb.service.harmony.TaskImgService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Integer add(TaskImgDTO taskImgDTO) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        TaskImgEntity taskImgEntity = mapperFactory.getMapperFacade().map(taskImgDTO, TaskImgEntity.class);
        taskImgEntity.setIsDetect(0);
        taskImgEntity.setGmtCreate(new Date());
        return taskImgMapper.add(taskImgEntity);
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
        Integer res = 0;
        for (String id : taskImgId) {
            res += taskImgMapper.remove(Long.parseLong(id));
        }
        return res;
    }
}
