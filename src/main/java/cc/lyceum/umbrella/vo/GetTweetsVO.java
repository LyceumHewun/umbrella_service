package cc.lyceum.umbrella.vo;

import cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Data;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 21:02
 */
@Data
public class GetTweetsVO {

    private Long id;
    private Long userId;
    private String pictures;
    private String content;
    private Integer permissionType;
    private Integer type;
    private Date createTime;

    /**
     * 是否有点赞
     */
    private Boolean isLike;

    private List<String> picturesList;

    /**
     * 发推文的用户的信息
     */
    private String username;
    private String avatar;
    private String avatarUrl;

    /**
     * 点赞数 和 评论数
     */
    private Long likeCount;
    private Long commentCount;

    /**
     * 有没有红包(趣图类型)
     */
    private Boolean hasGift;
    private Boolean isInteresting;

    public void setPictures(String pictures) {
        this.pictures = pictures;

        picturesList = new LinkedList<>();
        JsonArray jsonArray = new JsonParser().parse(pictures).getAsJsonArray();
        for (JsonElement element : jsonArray) {
            String url = QiniuConfig.CDN_URL + element.getAsString();
            picturesList.add(url);
        }
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;

        this.avatarUrl = QiniuConfig.CDN_URL + avatar;
    }
}
