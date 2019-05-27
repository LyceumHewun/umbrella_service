package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.vo.GetUserInfoVO;
import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dto.GetWechatPhoneDTO;
import cc.lyceum.umbrella.dto.UpdateUserInfoDTO;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.vo.GetUserOtherInfoVO;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 12:08
 */
public interface UserService {

    ResponseMessage getUserByWechatOauth(String unionId);

    ResponseMessage getWechatPhone(GetWechatPhoneDTO dto, String sessionKey);

    ResponseMessage phoneIsExist(String phone);

    ResponseMessage usernameIsExist(String username);

    ResponseMessage signup(String phone, String username, String password);

    /**
     * 绑定微信（通过小程序）
     */
    ResponseMessage bindWechat(Long userId, String miniProgramsOpenId, String unionId);

    ResponseMessage unifiedLogin(String account, String password);

    ResponseMessage updateUserInfo(UpdateUserInfoDTO dto);

    ResponseMessage<GetUserInfoVO> getUserInfo(Long userId);

    ResponseMessage<User> getUser(Long userId);

    /**
     * 获取用户其他信息(推文数、粉丝数、关注数)
     */
    ResponseMessage<GetUserOtherInfoVO> getUserOtherInfo(Long userId);
}
