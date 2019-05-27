package cc.lyceum.umbrella.vo;

import cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig;
import lombok.Data;

import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:22
 */
@Data
public class GetCommentsVO {

    private Long id;
    private Long userId;
    private String content;
    private Date createTime;

    private Boolean isLike;

    /**
     * userId对应的用户名
     */
    private String username;
    /**
     * userId对应的用户的头像
     */
    private String avatar;
    private String avatarUrl;

    /**
     * 点赞数 评论数
     */
    private Long likeCount;
    private Long commentCount;

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        this.avatarUrl = QiniuConfig.CDN_URL + avatar;
    }
}
