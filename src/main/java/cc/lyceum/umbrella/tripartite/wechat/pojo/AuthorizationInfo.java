package cc.lyceum.umbrella.tripartite.wechat.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 微信code2Session接口返回授权信息
 */
@Data
public class AuthorizationInfo implements Serializable {

    /**
     * user表id
     */
    private Integer id;
    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 会话密钥
     */
    private String session_key;
    /**
     * 用户在开放平台的唯一标识符
     */
    private String unionid;
    /**
     * 错误码
     * -1	    系统繁忙，此时请开发者稍候再试
     * 0	    请求成功
     * 40029	code 无效
     * 45011	频率限制，每个用户每分钟100次
     */
    private Integer errcode;
    /**
     * 错误信息
     */
    private String errmsg;
    private long date = System.currentTimeMillis();
}
