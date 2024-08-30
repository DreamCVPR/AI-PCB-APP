package zjnu.huawei.pcb.controller.system;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.config.aop.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.dto.harmony.HarmonyTokenDTO;
import zjnu.huawei.pcb.utils.HttpRequestReader;
import zjnu.huawei.pcb.utils.JWTUtil;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/account")
@PreAuthorize
public class AccountController extends BaseController {


    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @PreAuthorize(false)
    public JSONObject login(HttpServletRequest request) {
        try {
            HarmonyTokenDTO harmonyTokenDTO = new HarmonyTokenDTO();
            if (harmonyUserId != null && harmonyUserId > 0) {
                harmonyTokenDTO.setHarmonyUserId(harmonyUserId);
                return CommonUtil.successJson(JWTUtil.createSign(harmonyTokenDTO.toString(), 60*24*365*100));
            }
            harmonyTokenDTO.setHarmonyUserId(1L);
            return CommonUtil.successJson(JWTUtil.createSign(harmonyTokenDTO.toString(), 60*24*365*100));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
