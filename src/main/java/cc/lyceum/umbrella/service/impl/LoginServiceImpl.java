package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dto.WechatLoginDTO;
import cc.lyceum.umbrella.service.LoginService;
import cc.lyceum.umbrella.tripartite.wechat.WechatApi;
import cc.lyceum.umbrella.tripartite.wechat.pojo.AuthorizationInfo;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 11:14
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private WechatApi wechatApi = new WechatApi();

    @Override
    public ResponseMessage wechatLogin(WechatLoginDTO dto) {
        AuthorizationInfo authorizationInfo = wechatApi.code2Session(dto.getCode());
        if (null == authorizationInfo.getUnionid()) {
            // 解码获取 unionid
            String json = wechatApi.decrypt(authorizationInfo.getSession_key(), dto.getIv(), dto.getEncryptedData());
            log.info("解码: {}", json);
            String unionid = new JsonParser().parse(json).getAsJsonObject().get("unionId").getAsString();
            authorizationInfo.setUnionid(unionid);
        }
        if (null == authorizationInfo.getErrcode() || authorizationInfo.getErrcode() == 0) {
            return ResponseMessage.createBySuccess(authorizationInfo);
        }
        return ResponseMessage.createByError(authorizationInfo.getErrmsg());
    }
}
