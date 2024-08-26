package zjnu.huawei.pcb.service.harmony.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.dto.harmony.*;
import zjnu.huawei.pcb.entity.system.HarmonyUserEntity;
import zjnu.huawei.pcb.mapper.harmony.HarmonyMapper;
import zjnu.huawei.pcb.service.harmony.HarmonyService;
import zjnu.huawei.pcb.utils.JWTUtil;
import zjnu.huawei.pcb.utils.harmony.HarmonyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class HarmonyServiceImpl implements HarmonyService {
    @Value(value = "${application.jwt.appKey}")
    private String appKey;
    @Value(value = "${application.jwt.appSecret}")
    private String appSecret;

    @Resource
    private HarmonyMapper harmonyMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject getHuaweiUserInfo(HuaweiUserInfoDTO huaweiUserInfoDTO) throws Exception {
        String idToken = huaweiUserInfoDTO.getIdToken();
        return HarmonyUtil.verifyIdToken(idToken);
//        String token = HarmonyUtil.getAccessToken();
//        System.out.println("----appKey:"+appKey+"----appSecret:"+appSecret+"----code:"+code);
//        String ddUserId = HarmonyUtil.getUserId(token, code);
//        if (ddUserId == null) {
//            return new HarmonyDTO(-1L);
//        }
//        System.out.println("----ddUserId:"+ddUserId);
//        HarmonyDTO nowUser = harmonyMapper.queryWxUser(ddUserId);
//        if (nowUser == null) {
//            Long ddId = 0L;
//            HarmonyUserEntity ddUserEntity = new HarmonyUserEntity();
//            ddUserEntity.setGmtCreate(new Date());
//            ddUserEntity.setDdUserId(ddUserId);
//            harmonyMapper.addDdUser(ddUserEntity);
//            nowUser = new HarmonyDTO();
//            nowUser.setDdUserId(ddUserId);
//            nowUser.setDdId(ddUserEntity.getDdId());
//        }
//        HarmonyTokenDTO ddTokenDTO = new HarmonyTokenDTO(nowUser.getDdId(),nowUser.getEnterpriseId(), nowUser.getDdUserId());
//        System.out.println("ddTokenDTO:----"+JSON.toJSONString(ddTokenDTO));
//        nowUser.setToken(JWTUtil.createSign(ddTokenDTO.toString(), 240));
//        System.out.println("token-----"+nowUser.getToken());
//        if (nowUser.getEnterpriseId() == null) {
//            nowUser.setEnterpriseId(-1L);
//        }
//        return nowUser;
    }
}
