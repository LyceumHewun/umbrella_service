package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dao.LikeMapper;
import cc.lyceum.umbrella.entity.Like;
import cc.lyceum.umbrella.service.LikeService;
import cc.lyceum.umbrella.service.TweetsExtraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 21:16
 */
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;
    private final TweetsExtraInfoService tweetsExtraInfoService;

    @Autowired
    public LikeServiceImpl(LikeMapper likeMapper, TweetsExtraInfoService tweetsExtraInfoService) {
        this.likeMapper = likeMapper;
        this.tweetsExtraInfoService = tweetsExtraInfoService;
    }

    @Override
    public Boolean isLikeTweets(Long userId, Long tweetsId) {
        return likeMapper.isLikeTweets(userId, tweetsId);
    }

    @Override
    public Boolean isLikeComment(Long userId, Long commentId) {
        return likeMapper.isLikeComment(userId, commentId);
    }

    @Override
    public ResponseMessage addLike(Like like) {
        Boolean result = likeMapper.insert(like);
        if (result) {
            if (null != like.getTweetsId()) {
                tweetsExtraInfoService.addLikeCountByTweetsId(like.getTweetsId());
            } else if (null != like.getCommentId()) {
                tweetsExtraInfoService.addLikeCountByCommentId(like.getCommentId());
            }
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByErrorMessage("点赞失败");
    }

    @Override
    public ResponseMessage remove(Like like) {
        if (null != like.getTweetsId()) {
            likeMapper.removeByTweetsId(like);
            tweetsExtraInfoService.removeLikeCountByTweetsId(like.getTweetsId());
        } else if (null != like.getCommentId()) {
            likeMapper.removeByCommentsId(like);
            tweetsExtraInfoService.removeLikeCountByCommentId(like.getCommentId());
        }
        return ResponseMessage.createBySuccess();
    }
}
