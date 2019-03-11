package cn.swallow.platform.modular.system.controller;

import cn.swallow.platform.core.common.controller.BaseController;
import cn.swallow.platform.core.common.resp.BaseResp;
import cn.swallow.platform.core.constant.state.UserAuthState;
import cn.swallow.platform.core.shiro.ShiroKit;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("${swallow.admin-path}")
public class LoginController extends BaseController {

    @RequestMapping(value = "login")
    @ResponseBody
    public BaseResp login(@RequestParam(value = "account") String account,
                          @RequestParam(value = "password") String password, Boolean rememberme){
        BaseResp baseResp = BaseResp.newSuccess();
        Subject subject = ShiroKit.getSubject();
        try {
            subject.login(new UsernamePasswordToken(account,password,rememberme == null ? false : rememberme));
            baseResp.success();
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            baseResp.putState(UserAuthState.INCORRECT_CREDENTIALS);
        } catch (Exception e2){
            e2.printStackTrace();
            baseResp.error();
            baseResp.setMessage("登录失败");
        } finally {
            return baseResp;
        }
    }
}
