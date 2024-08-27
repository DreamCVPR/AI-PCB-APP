package zjnu.huawei.pcb.config.aop;

import java.lang.annotation.*;

/*
 * @Description （过期）自动获取x-auth-token
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoServiceToken
{
}
