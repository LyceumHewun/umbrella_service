package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.UnifiedLoginDTO;
import cc.lyceum.umbrella.dto.WechatLoginDTO;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.service.LoginService;
import cc.lyceum.umbrella.service.UserService;
import cc.lyceum.umbrella.tripartite.wechat.pojo.AuthorizationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 0:52
 */
@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;

    @Autowired
    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    /**
     * 微信登录
     */
    @PostMapping("wechat/login")
    public ResponseMessage wechatLogin(@RequestBody WechatLoginDTO dto, HttpSession session) {
        ResponseMessage responseMessage = loginService.wechatLogin(dto);
        log.info("dto: {}, responseMessage: {}", dto, responseMessage);
        if (responseMessage.isSuccess()) {
            AuthorizationInfo authorizationInfo = (AuthorizationInfo) responseMessage.getData();
            // 授权信息入库
            session.setAttribute(SessionType.WECHAT_OAUTH_MINI_PROGRAMS, authorizationInfo);
            // 根据 unionid 获取 关联的user
            responseMessage = userService.getUserByWechatOauth(authorizationInfo.getUnionid());
            if (responseMessage.isSuccess()) {
                User user = (User) responseMessage.getData();
                session.setAttribute(SessionType.USER, user);
                return ResponseMessage.createBySuccess();
            } else {
                ResponseMessage.createByError();
            }
        }
        return responseMessage;
    }

    /**
     * 统一登录接口(手机号码、用户名、密码)
     */
    @PostMapping("unifiedLogin")
    public ResponseMessage unifiedLogin(@RequestBody UnifiedLoginDTO dto, HttpSession session) {
        String account = dto.getAccount();
        String password = dto.getPassword();

        Assert.hasLength(account, "账号不能为空");
        Assert.hasLength(password, "密码不能为空");

        ResponseMessage responseMessage = userService.unifiedLogin(account, password);
        if (responseMessage.isSuccess()) {
            session.setAttribute(SessionType.USER, responseMessage.getData());
            return ResponseMessage.createBySuccess();
        }
        return responseMessage;
    }
}
