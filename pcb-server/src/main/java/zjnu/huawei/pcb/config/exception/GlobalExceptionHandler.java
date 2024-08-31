package zjnu.huawei.pcb.config.exception;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.utils.HttpStatus;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public JSONObject handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return CommonUtil.errorJson(HttpStatus.BAD_METHOD);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public JSONObject handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return CommonUtil.errorJson(HttpStatus.ERROR);
    }

    @ExceptionHandler(CommonJsonException.class)
    public JSONObject handleRuntimeException(CommonJsonException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return e.getResultJson();
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public JSONObject handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return CommonUtil.errorJson(HttpStatus.ERROR);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public JSONObject handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return CommonUtil.errorJson(HttpStatus.ERROR);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return CommonUtil.errorJson(HttpStatus.ERROR);
    }
}
