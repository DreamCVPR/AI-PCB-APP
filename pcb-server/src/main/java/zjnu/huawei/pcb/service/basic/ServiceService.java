package zjnu.huawei.pcb.service.basic;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServiceService {

    List<JSONObject> predictBatch(List<MultipartFile> files) throws Exception;

    JSONObject predict(MultipartFile file) throws Exception;
}
