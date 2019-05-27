package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.GetWechatPhoneDTO;
import cc.lyceum.umbrella.dto.SignupDTO;
import cc.lyceum.umbrella.dto.UpdateUserInfoDTO;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.entity.Wallet;
import cc.lyceum.umbrella.service.FollowService;
import cc.lyceum.umbrella.service.PayService;
import cc.lyceum.umbrella.service.UserService;
import cc.lyceum.umbrella.tripartite.qiniu.QiniuApi;
import cc.lyceum.umbrella.tripartite.wechat.pojo.AuthorizationInfo;
import cc.lyceum.umbrella.vo.GetMyUserInfoVO;
import cc.lyceum.umbrella.vo.GetOtherUserInfo;
import cc.lyceum.umbrella.vo.GetUserInfoVO;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 12:51
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    private QiniuApi qiniuApi = new QiniuApi();

    private final UserService userService;
    private final PayService payService;
    private final FollowService followService;

    @Autowired
    public UserController(UserService userService, PayService payService, FollowService followService) {
        this.userService = userService;
        this.payService = payService;
        this.followService = followService;
    }

    @PostMapping("wechat/getPhone")
    public ResponseMessage getWechatPhone(@RequestBody GetWechatPhoneDTO dto, HttpSession session) {
        AuthorizationInfo authorizationInfo = (AuthorizationInfo) session.getAttribute(SessionType.WECHAT_OAUTH_MINI_PROGRAMS);
        Assert.notNull(authorizationInfo, "未使用微信授权登录");
        ResponseMessage responseMessage = userService.getWechatPhone(dto, authorizationInfo.getSession_key());
        if (responseMessage.isSuccess()) {
            String json = (String) responseMessage.getData();
            // 序列化
            // TODO 还能获取 countryCode
            String purePhoneNumber = new JsonParser().parse(json).getAsJsonObject()
                    .get("purePhoneNumber").getAsString();
            return ResponseMessage.createBySuccess(purePhoneNumber);
        }
        return responseMessage;
    }

    /**
     * 检查 username 是否被注册
     */
    @GetMapping("phoneIsExist")
    public ResponseMessage phoneIsExist(@RequestParam("phone") String phone) {
        return userService.phoneIsExist(phone);
    }

    /**
     * 检查 username 是否被注册
     */
    @GetMapping("usernameIsExist")
    public ResponseMessage usernameIsExist(@RequestParam("username") String username) {
        return userService.usernameIsExist(username);
    }

    /**
     * 注册
     */
    @PostMapping("signup")
    public ResponseMessage signup(@RequestBody SignupDTO dto, HttpSession session) {
        String phone = dto.getPhone();
        String username = dto.getUsername();
        String password = dto.getPassword();
        Assert.hasLength(phone, "手机号码不能为空");
        Assert.hasLength(username, "用户名不能为空");
        Assert.hasLength(password, "密码不能为空");

        ResponseMessage responseMessage = userService.signup(phone, username, password);
        if (responseMessage.isSuccess()) {
            User user = (User) responseMessage.getData();
            session.setAttribute(SessionType.USER, user);
            // 注册成功
            // 绑定微信
            AuthorizationInfo authorizationInfo = (AuthorizationInfo) session.getAttribute(SessionType.WECHAT_OAUTH_MINI_PROGRAMS);
            if (null != authorizationInfo) {
                userService.bindWechat(user.getId(), authorizationInfo.getOpenid(), authorizationInfo.getUnionid());
            }
        }

        return responseMessage;
    }

    /**
     * 获取图片上传token
     */
    @GetMapping("getImageUploadToken")
    public ResponseMessage getImageUploadToken() {
        String uploadToken = qiniuApi.getUploadToken();
        return ResponseMessage.createBySuccess(uploadToken);
    }

    /**
     * 更新用户信息
     */
    @PostMapping("updateUserInfo")
    public ResponseMessage updateUserInfo(@RequestBody UpdateUserInfoDTO dto, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        dto.setId(user.getId());
        ResponseMessage responseMessage = userService.updateUserInfo(dto);
        if (responseMessage.isSuccess()) {
            // 更新session
            User u = userService.getUser(user.getId()).getData();
            session.setAttribute(SessionType.USER, u);
        }
        return responseMessage;
    }

    /**
     * 获取用户信息
     */
    @GetMapping("getUserInfo")
    public ResponseMessage<GetUserInfoVO> getUserInfo(@RequestParam("userId") Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 获取自己的用户信息
     */
    @GetMapping("getMyUserInfo")
    public ResponseMessage<GetMyUserInfoVO> getMyUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        User u = userService.getUser(user.getId()).getData();
        GetMyUserInfoVO vo = new GetMyUserInfoVO();
        BeanUtils.copyProperties(u, vo);
        Wallet wallet = payService.getWallet(u.getId()).getData();
        vo.setWalletMoney(wallet.getMoney());
        return ResponseMessage.createBySuccess(vo);
    }

    /**
     * 获取其他用户信息
     */
    @GetMapping("getOtherUserInfo")
    public ResponseMessage<GetOtherUserInfo> getOtherUserInfo(@RequestParam("userId") Long userId, HttpSession session) {
        User user = userService.getUser(userId).getData();
        GetOtherUserInfo vo = new GetOtherUserInfo();
        BeanUtils.copyProperties(user, vo);
        User visitorUser = (User) session.getAttribute(SessionType.USER);
        // 已经登录 而且 user_id不相同
        if (null != visitorUser && !Objects.equals(userId, visitorUser.getId())) {
            // 获取是否已关注
            Boolean isFollowing = followService.visitorUserIsFollowingThisUser(visitorUser.getId(), userId);
            vo.setIsFollowing(isFollowing);
        }
        return ResponseMessage.createBySuccess(vo);
    }

    /**
     * 获取用户其他信息(推文数、粉丝数、关注数)
     */
    @GetMapping("getUserOtherInfo")
    public ResponseMessage getUserOtherInfo(@RequestParam("userId") Long userId) {
        return userService.getUserOtherInfo(userId);
    }


}
