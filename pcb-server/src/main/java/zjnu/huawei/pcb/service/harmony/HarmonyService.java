package zjnu.huawei.pcb.service.harmony;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.dto.harmony.HarmonyDTO;
import zjnu.huawei.pcb.dto.harmony.HarmonyInfoDTO;
import zjnu.huawei.pcb.dto.harmony.HuaweiUserInfoDTO;

public interface HarmonyService {

    JSONObject getHuaweiUserInfo(HuaweiUserInfoDTO huaweiUserInfoDTO) throws Exception;
}
