package cc.lyceum.umbrella.interceptor;

import cc.lyceum.umbrella.constants.SessionType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lamze
 * @version 1.0
 * @date 2019-04-30 02:54
 */
@Slf4j
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static List<String> publicUri;

    static {
        publicUri = new ArrayList<>();
        publicUri.add("/login/wechat/login");
        publicUri.add("/login/unifiedLogin");
        publicUri.add("/user/wechat/getPhone");
        publicUri.add("/user/phoneIsExist");
        publicUri.add("/user/usernameIsExist");
        publicUri.add("/user/signup");
        publicUri.add("/user/getUserInfo");
        publicUri.add("/user/getUserOtherInfo");
        publicUri.add("/user/getOtherUserInfo");
        publicUri.add("/tweets/getSquareTweets");
        publicUri.add("/tweets/getTweets");
        publicUri.add("/tweets/getTweetsListByUserId");
        publicUri.add("/comment/getComments");
        publicUri.add("/comment/getSubComments");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String remoteAddr = request.getRemoteAddr();
        // 放行公开的uri
        String uri = request.getRequestURI();
        log.info("{}访问: {}", remoteAddr, uri);
        for (String u : publicUri) {
            if (uri.startsWith(u)) {
                return true;
            }
        }


        // 有登录session才放行
        HttpSession session = request.getSession();
        Object user = session.getAttribute(SessionType.USER);
        if (ObjectUtils.allNotNull(user)) {
            return true;
        }
        response.setStatus(403);
        return false;
    }
}
