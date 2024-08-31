package zjnu.huawei.pcb.service.system;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.dto.system.HarmonyUserDTO;

public interface AccountService {
    HarmonyUserDTO login(HarmonyUserDTO harmonyUserDTO) throws Exception;
}
