package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:19
 */
@Data
public class FollowCount {

    private Long userId;
    private Long following;
    private Long follower;
    private Date createTime;
    private Date modifyTime;
}
