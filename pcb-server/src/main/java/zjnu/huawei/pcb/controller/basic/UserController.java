package zjnu.huawei.pcb.controller.basic;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import zjnu.huawei.pcb.dto.basic.HarmonyUserDTO;
import zjnu.huawei.pcb.service.basic.UserService;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public JSONObject getUserInfo() {
        try {
            return CommonUtil.successJson(userService.getByUserId(harmonyUserId));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public JSONObject updateUser(@RequestBody HarmonyUserDTO harmonyUserDTO) {
        try {
            harmonyUserDTO.setHarmonyUserId(harmonyUserId);
            return CommonUtil.successJson(userService.update(harmonyUserDTO));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    @RequestMapping(value = "/changeImage", method = RequestMethod.POST)
    public JSONObject changeImage(@RequestBody JSONObject jsonObject) {
        try {
            jsonObject.put("harmonyUserId", harmonyUserId);
            return CommonUtil.successJson(userService.changeImage(jsonObject));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public JSONObject resetPwd(@RequestBody HarmonyUserDTO harmonyUserDTO) {
        try {
            harmonyUserDTO.setHarmonyUserId(harmonyUserId);
            return CommonUtil.successJson(userService.resetPwd(harmonyUserDTO));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
