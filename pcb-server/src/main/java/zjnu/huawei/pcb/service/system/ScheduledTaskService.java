package zjnu.huawei.pcb.service.system;

import zjnu.huawei.pcb.dto.system.HarmonyUserDTO;

public interface ScheduledTaskService {
    void removeExpired() throws Exception;
}
