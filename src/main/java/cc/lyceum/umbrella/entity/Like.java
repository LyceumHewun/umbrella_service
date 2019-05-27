package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.sql.Date;

/**
 * 点赞表
 * tweetsId 和 commentId 不能同时为空
 *
 * @author Lyceum Hewun
 * @date 2019-05-13 23:30
 */
@Data
public class Like {

    private Long id;
    private Long userId;
    private Long tweetsId;
    private Long commentId;
    private Date createTime;
    private Date modifyTime;
    private Boolean deleted;
}
