package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:18
 */
@Data
public class Follow {

    private Long id;
    private Long userId;
    /**
     * 关注的用户的id
     */
    private Long followingUserId;
    private Date createTime;
    private Date modifyTime;
    private Boolean deleted;
}
