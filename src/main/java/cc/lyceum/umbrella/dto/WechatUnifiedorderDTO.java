package cc.lyceum.umbrella.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lyceum Hewun
 * @date 2019-05-18 1:03
 */
@Data
public class WechatUnifiedorderDTO {

    /**
     * wx.login() 获取的code
     * 用来获取open_id
     */
    private String code;
    private String money;
}
