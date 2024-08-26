package zjnu.huawei.pcb.controller.basic;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.dto.basic.UserDTO;
import zjnu.huawei.pcb.service.basic.UserService;
import zjnu.huawei.pcb.utils.JWTUtil;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public JSONObject updateUser(@RequestBody UserDTO userDTO) {
        userDTO.setDdId(JWTUtil.verifyHarmonyToken(token).getIat());
        JSONObject resultJson;
        try {
            resultJson = CommonUtil.successJson(userService.updateUser(userDTO));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
        return resultJson;
    }
}
