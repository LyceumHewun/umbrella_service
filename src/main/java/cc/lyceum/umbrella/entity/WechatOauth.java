package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.sql.Date;

/**
 * 微信授权登录表
 *
 * @author Lyceum Hewun
 * @date 2019-05-13 23:48
 */
@Data
public class WechatOauth {

    private Long id;
    private Long userId;
    /**
     * 微信小程序的open_id
     */
    private String miniProgramsOpenId;
    private String unionId;
    private Date createTime;
    private Date modifyTime;
    private Boolean deleted;
}
