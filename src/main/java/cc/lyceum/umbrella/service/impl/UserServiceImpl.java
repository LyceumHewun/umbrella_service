package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dao.FollowCountMapper;
import cc.lyceum.umbrella.dao.TweetsMapper;
import cc.lyceum.umbrella.dao.UserMapper;
import cc.lyceum.umbrella.dao.WechatOauthMapper;
import cc.lyceum.umbrella.dto.GetWechatPhoneDTO;
import cc.lyceum.umbrella.dto.UpdateUserInfoDTO;
import cc.lyceum.umbrella.entity.FollowCount;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.entity.WechatOauth;
import cc.lyceum.umbrella.service.UserService;
import cc.lyceum.umbrella.tripartite.wechat.WechatApi;
import cc.lyceum.umbrella.vo.GetUserInfoVO;
import cc.lyceum.umbrella.vo.GetUserOtherInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static cc.lyceum.umbrella.constants.ErrorCode.NOT_BIND_WECHAT;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 12:08
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private WechatApi wechatApi = new WechatApi();

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserMapper userMapper;
    private final WechatOauthMapper wechatOauthMapper;
    private final FollowCountMapper followCountMapper;
    private final TweetsMapper tweetsMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, WechatOauthMapper wechatOauthMapper, BCryptPasswordEncoder bCryptPasswordEncoder, FollowCountMapper followCountMapper, TweetsMapper tweetsMapper) {
        this.userMapper = userMapper;
        this.wechatOauthMapper = wechatOauthMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.followCountMapper = followCountMapper;
        this.tweetsMapper = tweetsMapper;
    }

    @Override
    public ResponseMessage getUserByWechatOauth(String unionId) {
        WechatOauth wechatOauth = wechatOauthMapper.getByUnionId(unionId);
        if (null != wechatOauth) {
            Long userId = wechatOauth.getUserId();
            User user = userMapper.getByUserId(userId);
            if (null != user) {
                return ResponseMessage.createBySuccess(user);
            }
        }
        return ResponseMessage.createByErrorCodeMessage(NOT_BIND_WECHAT, "该微信未绑定账号");
    }

    @Override
    public ResponseMessage getWechatPhone(GetWechatPhoneDTO dto, String sessionKey) {
        String encryptedData = dto.getEncryptedData();
        String iv = dto.getIv();
        String string = wechatApi.decrypt(sessionKey, iv, encryptedData);
        log.info("解密后: {}", string);
        return ResponseMessage.createBySuccess(string);
    }

    @Override
    public ResponseMessage phoneIsExist(String phone) {
        Boolean result = userMapper.phoneIsExist(phone);
        if (!result) {
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByError();
    }

    @Override
    public ResponseMessage usernameIsExist(String username) {
        Boolean result = userMapper.usernameIsExist(username);
        if (!result) {
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByError();
    }

    @Override
    public ResponseMessage signup(String phone, String username, String password) {
        Boolean phoneIsExist = userMapper.phoneIsExist(phone);
        Assert.isTrue(!phoneIsExist, "该手机号已被注册");
        Boolean usernameIsExist = userMapper.usernameIsExist(username);
        Assert.isTrue(!usernameIsExist, "该用户名已被使用");
        boolean b1 = username.length() < 2;
        Assert.isTrue(!b1, "用户名不少于2个字符");
        boolean b2 = password.length() < 8;
        Assert.isTrue(!b2, "密码不少于8位");

        // 构造 User
        User user = new User();
        user.setPhone(phone);
        user.setUsername(username);
        // 加密密码
        user.setPassword(bCryptPasswordEncoder.encode(password));
        Boolean result = userMapper.signup(user);
        if (result) {
            return ResponseMessage.createBySuccess(user);
        }

        return ResponseMessage.createByError();
    }

    @Override
    public ResponseMessage bindWechat(Long userId, String miniProgramsOpenId, String unionId) {
        Boolean result = wechatOauthMapper.insert(userId, miniProgramsOpenId, unionId);
        if (result) {
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByError();
    }

    @Override
    public ResponseMessage unifiedLogin(String account, String password) {
        log.info("用户登录: {}", account);
        if (StringUtils.isNumeric(account)) {
            // 全数字
            // 使用手机号码方式登录
            User user = userMapper.getByPhone(account);
            if (null != user && bCryptPasswordEncoder.matches(password, user.getPassword())) {
                return ResponseMessage.createBySuccess(user);
            }
        }

        // 全数字的用户名
        User user = userMapper.getByUsername(account);
        if (null != user && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return ResponseMessage.createBySuccess(user);
        }

        return ResponseMessage.createByErrorMessage("账号或密码错误");
    }

    @Override
    public ResponseMessage updateUserInfo(UpdateUserInfoDTO dto) {
        if (null != dto.getPassword()) {
            // 加密密码
            dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        }
        Boolean result = userMapper.updateUserInfo(dto);
        if (result) {
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByError();
    }

    @Override
    public ResponseMessage<GetUserInfoVO> getUserInfo(Long userId) {
        User user = userMapper.getByUserId(userId);
        Assert.notNull(user, "用户不存在");
        GetUserInfoVO vo = new GetUserInfoVO();
        BeanUtils.copyProperties(user, vo);
        return ResponseMessage.createBySuccess(vo);
    }

    @Override
    public ResponseMessage<User> getUser(Long userId) {
        Assert.notNull(userId, "userId不能为空");
        User user = userMapper.getByUserId(userId);
        Assert.notNull(user, "用户不存在");
        return ResponseMessage.createBySuccess(user);
    }

    @Override
    public ResponseMessage<GetUserOtherInfoVO> getUserOtherInfo(Long userId) {
        User user = userMapper.getByUserId(userId);
        Assert.notNull(user, "用户不存在");
        // 获取关注数和粉丝数
        FollowCount followCount = followCountMapper.getByUserId(userId);
        if (null == followCount) {
            followCount = new FollowCount();
            followCount.setUserId(userId);
            followCount.setFollower(0L);
            followCount.setFollowing(0L);
            Boolean result = followCountMapper.insert(followCount);
            Assert.isTrue(result, "获取失败");
        }
        // 获取推文数
        Integer tweetsCount = tweetsMapper.getTweetsCountByUserId(userId);
        // 组装
        GetUserOtherInfoVO vo = new GetUserOtherInfoVO();
        vo.setFollower(followCount.getFollower());
        vo.setFollowing(followCount.getFollowing());
        vo.setTweetsCount(tweetsCount);
        return ResponseMessage.createBySuccess(vo);
    }
}
