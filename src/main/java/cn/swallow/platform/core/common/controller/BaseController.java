package cn.swallow.platform.core.common.controller;

import cn.swallow.platform.core.common.resp.BaseResp;
import cn.swallow.platform.core.util.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected final String REDIRECT = "redirect:";

    protected HttpSession getSession() {
        return ((HttpServletRequest) Objects.requireNonNull(HttpContext.getRequest())).getSession();
    }

    protected HttpSession getSession(Boolean flag) {
        return ((HttpServletRequest)Objects.requireNonNull(HttpContext.getRequest())).getSession(flag);
    }

    protected String getPara(String name) {
        return ((HttpServletRequest)Objects.requireNonNull(HttpContext.getRequest())).getParameter(name);
    }

    protected void setAttr(String name, Object value) {
        ((HttpServletRequest)Objects.requireNonNull(HttpContext.getRequest())).setAttribute(name, value);
    }

    protected Map<String,?> getInputFlashMap(HttpServletRequest request){
        return RequestContextUtils.getInputFlashMap(request);
    }

    protected Boolean validateBindingResult(BindingResult bindingResult, BaseResp baseResp){
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError error : errors){
                sb.append(error.getDefaultMessage());
                sb.append(";");
            }
            baseResp.error();
            baseResp.setMessage(sb.toString());
            return false;
        }else return true;
    }
}
