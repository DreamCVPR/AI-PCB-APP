package zjnu.huawei.pcb.config.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 微信接口验证注解
 * @author SYD
 * @date 2021/8/17
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {
    /**
     * 注解是否生效
     */
    public boolean value() default true;

    /**
     * 判断用户是否拥有某个角色
     */
    public int hasRole() default -1;

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反
     */
    public int lacksRole() default -1;

    /**
     * 验证用户是否具有以下任意一个角色
     */
    public int[] hasAnyRoles() default {};
}
