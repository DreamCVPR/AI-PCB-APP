package zjnu.huawei.pcb.service.basic.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import zjnu.huawei.pcb.service.basic.ServiceService;

import zjnu.huawei.pcb.utils.harmony.HarmonyUtil;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Override
    public JSONObject getServiceToken(JSONObject jsonObject) throws Exception {
        String url = "https://iam.cn-southwest-2.myhuaweicloud.com/v3/auth/tokens";
        JSONObject request = HarmonyUtil.sendRequest(url, "POST", jsonObject);
        return request;
    }
}
