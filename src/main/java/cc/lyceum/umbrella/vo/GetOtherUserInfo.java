package cc.lyceum.umbrella.vo;

import cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 10:52
 */
@Data
public class GetOtherUserInfo {

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
     * 关注有没有
     */
    private Boolean isFollowing;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        this.avatarUrl = QiniuConfig.CDN_URL + avatar;
    }
}
