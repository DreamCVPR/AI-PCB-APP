package zjnu.huawei.pcb.utils.harmony;

/**
 * 错误码汇总
 */
public enum ErrorEnum {
    /*
     * 错误信息
     * */
    E_400(400L, "请求处理异常，请稍后再试"),
    E_401(401L, "凭据已失效"),

    E_500(500L, "请求方式有误,请检查"),
    E_501(501L, "请求路径不存在"),
    E_502(502L, "权限不足"),

    E_10008(10008L, "角色删除失败,尚有用户属于此角色"),
    E_10009(10009L, "账户已存在"),
    E_10010(10010L, "账号/密码错误"),
    E_10011(10010L, "账户已存在或账号/密码错误"),

    E_20011(20011L, "登陆已过期,请重新登陆"),
    E_20012(20012L, "当前请求人数过多，请稍后再试"),
    E_20013(20013L, "已在其他设备登陆，请确认是否本人登陆"),

    E_90003(90003L, "缺少必填参数"),
    E_90004(90004L, "获取微信信息失败");

    private final Long errorCode;

    private final String errorMsg;

    ErrorEnum(Long errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
