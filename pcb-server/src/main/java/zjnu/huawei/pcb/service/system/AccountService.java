package zjnu.huawei.pcb.service.system;

import zjnu.huawei.pcb.dto.basic.HarmonyUserDTO;

public interface AccountService {
    HarmonyUserDTO login(HarmonyUserDTO harmonyUserDTO) throws Exception;
}
