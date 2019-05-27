package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.entity.Like;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 21:16
 */
public interface LikeService {

    /**
     * 查点赞
     */
    Boolean isLikeTweets(Long userId, Long tweetsId);

    /**
     * 查点赞
     */
    Boolean isLikeComment(Long userId, Long commentId);

    /**
     * 点赞
     */
    ResponseMessage addLike(Like like);

    /**
     * 取消点赞
     */
    ResponseMessage remove(Like like);
}
