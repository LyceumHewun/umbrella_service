package cc.lyceum.umbrella.vo;

import cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 16:21
 */
@Data
public class GetMyUserInfoVO {

    private Long id;
    private String username;
    private String avatar;
    /**
     * 拼装好的url
     */
    private String avatarUrl;
    private String introduction;
    private String website;
    /**
     * 钱包余额
     */
    private BigDecimal walletMoney;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        this.avatarUrl = QiniuConfig.CDN_URL + avatar;
    }
}
