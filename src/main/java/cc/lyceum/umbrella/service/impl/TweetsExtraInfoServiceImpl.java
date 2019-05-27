package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.dao.TweetsExtraInfoMapper;
import cc.lyceum.umbrella.entity.TweetsExtraInfo;
import cc.lyceum.umbrella.service.TweetsExtraInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:33
 */
@Service
public class TweetsExtraInfoServiceImpl implements TweetsExtraInfoService {

    private final TweetsExtraInfoMapper tweetsExtraInfoMapper;

    @Autowired
    public TweetsExtraInfoServiceImpl(TweetsExtraInfoMapper tweetsExtraInfoMapper) {
        this.tweetsExtraInfoMapper = tweetsExtraInfoMapper;
    }

    @Override
    public TweetsExtraInfo getByTweetsId(Long tweetsId) {
        TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByTweetsId(tweetsId);
        if (null == tweetsExtraInfo) {
            tweetsExtraInfo = new TweetsExtraInfo();
            tweetsExtraInfo.setTweetsId(tweetsId);
            tweetsExtraInfo.setLikeCount(0L);
            tweetsExtraInfo.setCommentCount(0L);
            tweetsExtraInfoMapper.insert(tweetsExtraInfo);
        }
        return tweetsExtraInfo;
    }

    @Override
    public TweetsExtraInfo getByCommentId(Long commentId) {
        TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByCommentId(commentId);
        if (null == tweetsExtraInfo) {
            tweetsExtraInfo = new TweetsExtraInfo();
            tweetsExtraInfo.setCommentId(commentId);
            tweetsExtraInfo.setLikeCount(0L);
            tweetsExtraInfo.setCommentCount(0L);
            tweetsExtraInfoMapper.insert(tweetsExtraInfo);
        }
        return tweetsExtraInfo;
    }

    @Override
    public Boolean addCommentCountByTweetsId(Long tweetsId) {
        Boolean result = tweetsExtraInfoMapper.addCommentCountByTweetsId(tweetsId);
        if (!result) {
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByTweetsId(tweetsId);
            if (null == tweetsExtraInfo) {
                tweetsExtraInfo = new TweetsExtraInfo();
                tweetsExtraInfo.setTweetsId(tweetsId);
                tweetsExtraInfo.setCommentCount(1L);
                tweetsExtraInfo.setLikeCount(0L);
                return tweetsExtraInfoMapper.insert(tweetsExtraInfo);
            }
        }
        return result;
    }

    @Override
    public Boolean addLikeCountByTweetsId(Long tweetsId) {
        Boolean result = tweetsExtraInfoMapper.addLikeCountByTweetsId(tweetsId);
        if (!result) {
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByTweetsId(tweetsId);
            if (null == tweetsExtraInfo) {
                tweetsExtraInfo = new TweetsExtraInfo();
                tweetsExtraInfo.setTweetsId(tweetsId);
                tweetsExtraInfo.setCommentCount(0L);
                tweetsExtraInfo.setLikeCount(1L);
                return tweetsExtraInfoMapper.insert(tweetsExtraInfo);
            }
        }
        return result;
    }

    @Override
    public Boolean addCommentCountByCommentId(Long commentId) {
        Boolean result = tweetsExtraInfoMapper.addCommentCountByCommentId(commentId);
        if (!result) {
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByCommentId(commentId);
            if (null == tweetsExtraInfo) {
                tweetsExtraInfo = new TweetsExtraInfo();
                tweetsExtraInfo.setCommentId(commentId);
                tweetsExtraInfo.setCommentCount(1L);
                tweetsExtraInfo.setLikeCount(0L);
                return tweetsExtraInfoMapper.insert(tweetsExtraInfo);
            }
        }
        return result;
    }

    @Override
    public Boolean addLikeCountByCommentId(Long commentId) {
        Boolean result = tweetsExtraInfoMapper.addLikeCountByCommentId(commentId);
        if (!result) {
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByCommentId(commentId);
            if (null == tweetsExtraInfo) {
                tweetsExtraInfo = new TweetsExtraInfo();
                tweetsExtraInfo.setCommentId(commentId);
                tweetsExtraInfo.setCommentCount(0L);
                tweetsExtraInfo.setLikeCount(1L);
                return tweetsExtraInfoMapper.insert(tweetsExtraInfo);
            }
        }
        return result;
    }

    @Override
    public Boolean removeLikeCountByTweetsId(Long tweetsId) {
        return tweetsExtraInfoMapper.removeLikeCountByTweetsId(tweetsId);
    }

    @Override
    public Boolean removeLikeCountByCommentId(Long commentId) {
        return tweetsExtraInfoMapper.removeLikeCountByCommentId(commentId);
    }
}
