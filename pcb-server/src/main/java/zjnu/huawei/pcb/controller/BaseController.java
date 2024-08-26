package zjnu.huawei.pcb.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import zjnu.huawei.pcb.utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String token;
    protected String serviceToken;
    protected Long accountId;

    @ModelAttribute
    public void setBaseParams(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String token = request.getHeader("Access-Token");
        String serviceToken = request.getHeader("x-auth-token");

        if (token != null) {
            this.token = token;
        }
        if (serviceToken != null) {
            this.serviceToken = serviceToken;
        }
    }
}
