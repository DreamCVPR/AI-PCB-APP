package zjnu.huawei.pcb.service.harmony;

import zjnu.huawei.pcb.dto.harmony.TaskDTO;
import zjnu.huawei.pcb.entity.harmony.TaskEntity;

import java.util.List;

public interface TaskService {

    List<TaskDTO> query(TaskDTO taskDTO) throws Exception;

    Integer add(TaskDTO taskDTO) throws Exception;

    Integer update(TaskDTO taskDTO) throws Exception;

    Integer updateState(Long taskId, Integer state) throws Exception;

    Integer remove(String taskIds) throws Exception;

    Integer detect(Long taskId) throws Exception;
}
