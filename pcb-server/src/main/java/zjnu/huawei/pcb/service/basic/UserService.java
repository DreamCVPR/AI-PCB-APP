package zjnu.huawei.pcb.service.basic;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.dto.basic.HarmonyUserDTO;
import zjnu.huawei.pcb.entity.basic.HarmonyUserEntity;


public interface UserService {


    Integer update(HarmonyUserDTO harmonyUserDTO) throws Exception;


    Integer resetPwd(HarmonyUserDTO harmonyUserDTO) throws Exception;


    HarmonyUserEntity getByUserId(Long harmonyUserId) throws Exception;


    String changeImage(JSONObject jsonObject) throws Exception;
}
