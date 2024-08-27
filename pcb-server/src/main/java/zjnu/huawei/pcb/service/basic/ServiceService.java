package zjnu.huawei.pcb.service.basic;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import zjnu.huawei.pcb.dto.basic.IAMUserDTO;
import zjnu.huawei.pcb.dto.harmony.HuaweiUserInfoDTO;

import java.util.List;

public interface ServiceService {

    List<JSONObject> predict(List<MultipartFile> files) throws Exception;

}
