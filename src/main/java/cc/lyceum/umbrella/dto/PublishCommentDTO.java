package cc.lyceum.umbrella.dto;

import lombok.Data;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:49
 */
@Data
public class PublishCommentDTO {

    private Long tweetsId;
    /**
     * 回复的评论的id
     * 可以空
     */
    private Long commentId;
    private String content;

}
