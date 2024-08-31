package zjnu.huawei.pcb.controller.system;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import zjnu.huawei.pcb.config.aop.PreAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.dto.system.HarmonyTokenDTO;
import zjnu.huawei.pcb.dto.system.HarmonyUserDTO;
import zjnu.huawei.pcb.service.system.AccountService;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/account")
@PreAuthorize
public class AccountController extends BaseController {
    @Resource
    private AccountService accountService;


    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @PreAuthorize(false)
    public JSONObject login(@RequestBody HarmonyUserDTO harmonyUserDTO) {
        try {
            HarmonyTokenDTO harmonyTokenDTO = new HarmonyTokenDTO();
            if (harmonyUserId != null && harmonyUserId > 0) {
                harmonyTokenDTO.setHarmonyUserId(harmonyUserId);
            }
            return CommonUtil.successJson(accountService.login(harmonyUserDTO));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
