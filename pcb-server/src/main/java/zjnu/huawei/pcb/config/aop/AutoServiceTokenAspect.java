package zjnu.huawei.pcb.config.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import zjnu.huawei.pcb.service.basic.impl.ServiceServiceImpl;

import java.time.Instant;
import java.time.ZonedDateTime;

/*
 * @Description （过期）自动获取x-auth-token
 **/
@Aspect
@Component
public class AutoServiceTokenAspect {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(zjnu.huawei.pcb.config.aop.AutoServiceToken)"
            + "|| @within(zjnu.huawei.pcb.config.aop.AutoServiceToken)")
    public void rdPointCut() {}

    @Before("rdPointCut()")
    public void doBefore(JoinPoint point){
        JSONObject serviceToken = ServiceServiceImpl.serviceToken;
        if (serviceToken == null || is_expired(serviceToken.getString("expires_at"))) {
            try {
                serviceToken = new JSONObject();
                JSONObject res = ServiceServiceImpl.getServiceToken();
                serviceToken.put("expires_at", res.getJSONObject("body").getJSONObject("token").getString("expires_at"));
                for (Object o : res.getJSONArray("headers")) {
                    JSONObject header = JSONObject.parseObject(JSON.toJSONString(o));
                    if (header.getString("name").equals("X-Subject-Token")) {
                        serviceToken.put("token", header.getString("value"));
                        break;
                    }
                }
                ServiceServiceImpl.serviceToken = serviceToken;
            } catch (Exception e) {
                System.out.println("ServiceToken Error: " + e.toString());
            }
        }
    }

    private boolean is_expired(String expires_at) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(expires_at);
        Instant instant = zonedDateTime.toInstant();
        long timestamp = instant.toEpochMilli();
        long nowtime = System.currentTimeMillis();
        return timestamp-nowtime < 60*1000 ;
    }
}
