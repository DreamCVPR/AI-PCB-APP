package zjnu.huawei.pcb.controller.harmony;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.dto.harmony.HuaweiUserInfoDTO;
import zjnu.huawei.pcb.service.harmony.HarmonyService;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/account")
public class HarmonyController extends BaseController {
    @Resource
    private HarmonyService harmonyService;
    private final Logger logger = LoggerFactory.getLogger(HarmonyController.class);

    @RequestMapping("/getUserInfo")
    public JSONObject getUserInfo(@RequestBody HuaweiUserInfoDTO huaweiUserInfoDTO) {
        try{
            if(huaweiUserInfoDTO.getIdToken() == null ){
                return CommonUtil.errorJson(ErrorEnum.E_90003);
            }else{
                return CommonUtil.successJson(harmonyService.getHuaweiUserInfo(huaweiUserInfoDTO));
            }
        }catch (CommonJsonException e) {
            throw e;
        }catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
