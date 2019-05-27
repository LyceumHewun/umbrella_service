package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.entity.TweetsExtraInfo;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:33
 */
public interface TweetsExtraInfoService {

    TweetsExtraInfo getByTweetsId(Long tweetsId);

    TweetsExtraInfo getByCommentId(Long commentId);

    Boolean addCommentCountByTweetsId(Long tweetsId);

    Boolean addLikeCountByTweetsId(Long tweetsId);

    Boolean addCommentCountByCommentId(Long commentId);

    Boolean addLikeCountByCommentId(Long commentId);

    Boolean removeLikeCountByTweetsId(Long tweetsId);

    Boolean removeLikeCountByCommentId(Long commentId);
}
