package cc.lyceum.umbrella.vo;

import cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig;
import lombok.Data;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 15:27
 */
@Data
public class GetUserInfoVO {

    private Long id;
    private String username;
    private String avatar;
    /**
     * 拼装好的url
     */
    private String avatarUrl;
    private String introduction;
    private String website;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        this.avatarUrl = QiniuConfig.CDN_URL + avatar;
    }
}
