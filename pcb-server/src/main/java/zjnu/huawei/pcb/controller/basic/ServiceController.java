package zjnu.huawei.pcb.controller.basic;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.service.basic.ServiceService;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/service")
public class ServiceController extends BaseController {
    @Resource
    private ServiceService serviceService;

    private final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @RequestMapping("/predict")
    public JSONObject predict(HttpServletRequest request) {
        try{
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            return CommonUtil.successJson(serviceService.predictBatch(files));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
