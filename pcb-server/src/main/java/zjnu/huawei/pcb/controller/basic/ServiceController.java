package zjnu.huawei.pcb.controller.basic;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.dto.basic.IAMUserDTO;
import zjnu.huawei.pcb.dto.harmony.HuaweiUserInfoDTO;
import zjnu.huawei.pcb.service.basic.ServiceService;
import zjnu.huawei.pcb.service.harmony.HarmonyService;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/service")
public class ServiceController extends BaseController {
    @Resource
    private ServiceService serviceService;
    private final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @RequestMapping("/getServiceToken")
    public JSONObject getToken(@RequestBody JSONObject jsonObject) {
        try{
//            if(huaweiUserInfoDTO.getIdToken() == null ){
//                return CommonUtil.errorJson(ErrorEnum.E_90003);
//            }else{
//                return CommonUtil.successJson(serviceService.getServiceToken(jsonObject));
//            }
            return CommonUtil.successJson(serviceService.getServiceToken(jsonObject));
        }catch (CommonJsonException e) {
            throw e;
        }catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
