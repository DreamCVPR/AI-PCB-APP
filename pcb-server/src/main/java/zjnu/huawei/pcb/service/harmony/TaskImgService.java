package zjnu.huawei.pcb.service.harmony;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.dto.harmony.TaskImgDTO;
import zjnu.huawei.pcb.entity.harmony.TaskImgEntity;

import java.util.List;

public interface TaskImgService {

    List<TaskImgEntity> query(Long taskId) throws Exception;

    Integer add(JSONObject jsonObject) throws Exception;

    Integer addList(List<TaskImgDTO> taskImgList) throws Exception;

    Integer update(TaskImgDTO taskImgDTO) throws Exception;

    Integer remove(String taskImgIds) throws Exception;

    Integer changeTask(JSONObject jsonObject) throws Exception;

    Integer detect(List<TaskImgEntity> taskImgList) throws Exception;
}
