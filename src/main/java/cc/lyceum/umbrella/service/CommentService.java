package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.entity.Comment;
import cc.lyceum.umbrella.vo.GetCommentsVO;

import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:16
 */
public interface CommentService {

    /**
     * @param tweetsId      推文id
     * @param visitorUserId 访问者的user_id
     * @return ResponseMessage<List<GetCommentsVO>>
     */
    ResponseMessage<List<GetCommentsVO>> getCommentsByTweetsId(Long tweetsId, Long visitorUserId);

    /**
     * 获取子评论
     *
     * @param commentId     上级评论id
     * @param visitorUserId 访问者的user_id
     * @return ResponseMessage<List<GetCommentsVO>>
     */
    ResponseMessage<List<GetCommentsVO>> getSubCommentsByCommentId(Long commentId, Long visitorUserId);

    ResponseMessage publishComment(Comment comment);
}
