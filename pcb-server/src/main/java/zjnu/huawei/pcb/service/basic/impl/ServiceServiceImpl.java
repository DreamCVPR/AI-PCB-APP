package zjnu.huawei.pcb.service.basic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BufferedHeader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zjnu.huawei.pcb.config.aop.AutoServiceToken;
import zjnu.huawei.pcb.service.basic.ServiceService;

import zjnu.huawei.pcb.utils.harmony.HarmonyUtil;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {
    static public JSONObject serviceToken;

    static public JSONObject getServiceToken() throws Exception {
        String url = "https://iam.cn-southwest-2.myhuaweicloud.com/v3/auth/tokens";
        String json = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":{\"user\":{\"domain\":{\"name\":\"hid_f3k68vkxutitlas\"},\"name\":\"syz123\",\"password\":\"syz123456\"}}},\"scope\":{\"project\":{\"name\":\"cn-southwest-2\"}}}}";
        return HarmonyUtil.sendPostJson(url,  json);
    }

    @AutoServiceToken
    @Override
    public JSONObject predict(MultipartFile file) throws Exception {
        String url = "https://infer-modelarts-cn-southwest-2.myhuaweicloud.com/v1/infers/027d2872-04c6-45b3-a9b9-b0c050676c29";
        return HarmonyUtil.sendPostFile(url, file);
    }

    @AutoServiceToken
    @Override
    public List<JSONObject> predictBatch(List<MultipartFile> files) throws Exception {
        String url = "https://infer-modelarts-cn-southwest-2.myhuaweicloud.com/v1/infers/027d2872-04c6-45b3-a9b9-b0c050676c29";
        List<JSONObject> res = new ArrayList<>();
        for (MultipartFile file : files) {
            res.add(HarmonyUtil.sendPostFile(url, file));
        }
        return res;
    }
}
