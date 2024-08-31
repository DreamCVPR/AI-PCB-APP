package zjnu.huawei.pcb.config.aop;

import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.config.exception.PreAuthorizeException;
import zjnu.huawei.pcb.dto.system.HarmonyTokenDTO;
import zjnu.huawei.pcb.utils.JWTUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import java.lang.reflect.Method;

@Aspect
@Component
public class PreAuthorizeAspect {
    @Around("@within(zjnu.huawei.pcb.config.aop.PreAuthorize)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        zjnu.huawei.pcb.config.aop.PreAuthorize typeAnnotation = method.getDeclaringClass().getAnnotation(zjnu.huawei.pcb.config.aop.PreAuthorize.class);
        zjnu.huawei.pcb.config.aop.PreAuthorize methodAnnotation = method.getAnnotation(zjnu.huawei.pcb.config.aop.PreAuthorize.class);
        // 以方法上的注解为准
        zjnu.huawei.pcb.config.aop.PreAuthorize annotation = methodAnnotation != null ? methodAnnotation : typeAnnotation;
        if (annotation == null || !annotation.value()) {
            return point.proceed();
        }
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes();
            String token = attributes.getRequest().getHeader("Access-Token");
            HarmonyTokenDTO harmonyTokenDTO = JWTUtil.verifyHarmonyToken(token);
            attributes.getResponse().setHeader("token_status", "0");
            if (harmonyTokenDTO == null || harmonyTokenDTO.getHarmonyUserId() == null) {
                throw new CommonJsonException(ErrorEnum.E_401);
            }
            attributes.getResponse().setHeader("token_status", "1");
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            throw new PreAuthorizeException();
        }
        return point.proceed();
    }
}
