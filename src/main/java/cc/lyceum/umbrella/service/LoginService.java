package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dto.WechatLoginDTO;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 11:14
 */
public interface LoginService {

    ResponseMessage wechatLogin(WechatLoginDTO dto);
}
