package cc.lyceum.umbrella.vo;

import lombok.Data;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 16:20
 */
@Data
public class GetUserOtherInfoVO {

    private Integer tweetsCount;
    private Long following;
    private Long follower;
}
