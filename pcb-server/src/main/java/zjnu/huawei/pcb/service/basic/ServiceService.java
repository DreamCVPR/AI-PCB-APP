package zjnu.huawei.pcb.service.basic;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.dto.basic.IAMUserDTO;
import zjnu.huawei.pcb.dto.harmony.HuaweiUserInfoDTO;

public interface ServiceService {

    JSONObject getServiceToken(JSONObject jsonObject) throws Exception;
}
