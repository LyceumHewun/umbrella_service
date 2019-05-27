package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.sql.Date;

/**
 * 评论实体类
 * tweetsId 和 commentId 不能同时为空
 *
 * @author Lyceum Hewun
 * @date 2019-05-13 23:12
 */
@Data
public class Comment {

    private Long id;
    private Long userId;
    /**
     * 推文表对应id
     */
    private Long tweetsId;
    /**
     * 评论表对应id
     */
    private Long commentId;
    /**
     * 内容
     */
    private String content;
    private Date createTime;
    private Date modifyTime;
    private Boolean deleted;
}
