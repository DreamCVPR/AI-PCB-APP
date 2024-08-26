package zjnu.huawei.pcb.utils;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import zjnu.huawei.pcb.dto.harmony.HarmonyTokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {

    // 加密秘钥
    private static String secretKey;

    @Value(value = "${application.jwt.secret}")
    public void setSecretKey(String secret) {
        JWTUtil.secretKey = secret;
    }

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * 生成签名
     *
     * @param Seed种子值
     * @return 加密的Token
     */
    public static String createSign(String seed, Integer expire) {
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, expire);
        Date expiresDate = nowTime.getTime();

        return JWT.create()
                .withClaim("seed", null == seed ? null : seed)
                .withIssuedAt(iatDate)           // sign time
                .withExpiresAt(expiresDate)      // expire time
                .sign(Algorithm.HMAC256(secretKey));
    }

    public static HarmonyTokenDTO verifyHarmonyToken(String token) {
        //根据传来的Token获取Seed
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        HarmonyTokenDTO harmonyTokenDTO = new HarmonyTokenDTO();
        try {
            Map<String, Claim> claims = verifier.verify(token).getClaims();
            Claim seed_claim = claims.get("seed");
            String jsonSign = seed_claim.asString();
            JSONObject jsonObject = JSONObject.parseObject(jsonSign);
            harmonyTokenDTO.setSub(jsonObject.getString("sub"));
            harmonyTokenDTO.setAud(jsonObject.getString("aud"));
        } catch (Exception e) {
            logger.error("Token验证======>" + harmonyTokenDTO);
            logger.error(e.toString());
            harmonyTokenDTO = null;
        }
        return harmonyTokenDTO;
    }
    /**
     * 校验Token是否正确
     *
     * @return 是否正确
     */
    public static HarmonyTokenDTO verifyIdToken(String token, String secretKey) {
        //根据传来的Token获取Seed
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
        HarmonyTokenDTO harmonyTokenDTO = new HarmonyTokenDTO();
        try {
            Map<String, Claim> claims = verifier.verify(token).getClaims();
            Claim seed_claim = claims.get("seed");
            String jsonSign = seed_claim.asString();
            JSONObject jsonObject = JSONObject.parseObject(jsonSign);
            harmonyTokenDTO.setSub(jsonObject.getString("sub"));
            harmonyTokenDTO.setAud(jsonObject.getString("aud"));
        } catch (Exception e) {
            logger.error("Token验证======>" + harmonyTokenDTO);
            logger.error(e.toString());
            harmonyTokenDTO = null;
        }
        return harmonyTokenDTO;
    }
}
