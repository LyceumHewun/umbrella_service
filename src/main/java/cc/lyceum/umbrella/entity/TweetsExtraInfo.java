package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:41
 */
@Data
public class TweetsExtraInfo {

    private Long id;
    private Long tweetsId;
    private Long commentId;
    private Long commentCount;
    private Long likeCount;
    private Date createTime;
    private Date modifyTime;
}
