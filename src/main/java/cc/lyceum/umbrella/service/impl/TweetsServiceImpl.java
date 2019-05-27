package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dao.*;
import cc.lyceum.umbrella.dto.PublishTweetsDTO;
import cc.lyceum.umbrella.entity.Tweets;
import cc.lyceum.umbrella.entity.TweetsExtraInfo;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.service.TweetsService;
import cc.lyceum.umbrella.vo.GetTweetsVO;
import cc.lyceum.umbrella.vo.PublishTweetsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 17:19
 */
@Service
public class TweetsServiceImpl implements TweetsService {

    private final UserMapper userMapper;
    private final TweetsMapper tweetsMapper;
    private final TweetsExtraInfoMapper tweetsExtraInfoMapper;
    private final LikeMapper likeMapper;
    private final FollowMapper followMapper;
    private final InterestingPictureMapper interestingPictureMapper;

    @Autowired
    public TweetsServiceImpl(TweetsMapper tweetsMapper, UserMapper userMapper, TweetsExtraInfoMapper tweetsExtraInfoMapper, LikeMapper likeMapper, FollowMapper followMapper, InterestingPictureMapper interestingPictureMapper) {
        this.tweetsMapper = tweetsMapper;
        this.userMapper = userMapper;
        this.tweetsExtraInfoMapper = tweetsExtraInfoMapper;
        this.likeMapper = likeMapper;
        this.followMapper = followMapper;
        this.interestingPictureMapper = interestingPictureMapper;
    }

    @Override
    public ResponseMessage getSquareTweets(Long visitorUserId) {
        List<Tweets> tweetsList = tweetsMapper.getAllPublicTweets();
        List<GetTweetsVO> list = new LinkedList<>();
        for (Tweets tweets : tweetsList) {
            GetTweetsVO vo = new GetTweetsVO();
            BeanUtils.copyProperties(tweets, vo);
            // 发推文的用户的信息
            User user = userMapper.getByUserId(tweets.getUserId());
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
            // 查点赞数和评论数
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByTweetsId(tweets.getId());
            if (null == tweetsExtraInfo) {
                tweetsExtraInfo = new TweetsExtraInfo();
                tweetsExtraInfo.setTweetsId(tweets.getId());
                tweetsExtraInfo.setLikeCount(0L);
                tweetsExtraInfo.setCommentCount(0L);
                tweetsExtraInfoMapper.insert(tweetsExtraInfo);
            }
            vo.setLikeCount(tweetsExtraInfo.getLikeCount());
            vo.setCommentCount(tweetsExtraInfo.getCommentCount());
            // 查询趣图类型的信息
            if (tweets.getType() == Tweets.Type.INTERESTING.getCode()) {
                Boolean hasGift = interestingPictureMapper.get(tweets.getId()).getHasGift();
                vo.setHasGift(hasGift);
                vo.setIsInteresting(true);
            }
            if (null != visitorUserId) {
                // 查点赞
                Boolean isLike = likeMapper.isLikeTweets(visitorUserId, tweets.getId());
                vo.setIsLike(isLike);
            }
            list.add(vo);
        }
        return ResponseMessage.createBySuccess(list);
    }

    @Override
    public ResponseMessage<PublishTweetsVO> publishTweets(PublishTweetsDTO dto) {
        Tweets tweets = new Tweets();
        BeanUtils.copyProperties(dto, tweets);
        Boolean result = tweetsMapper.insert(tweets);
        Assert.isTrue(result, "插入失败");
        PublishTweetsVO vo = new PublishTweetsVO();
        BeanUtils.copyProperties(tweets, vo);
        return ResponseMessage.createBySuccess(vo);
    }

    @Override
    public ResponseMessage<GetTweetsVO> getTweetsById(Long id) {
        // 查推文信息
        Tweets tweets = tweetsMapper.getById(id);
        Assert.notNull(tweets, "id不存在");
        GetTweetsVO vo = new GetTweetsVO();
        BeanUtils.copyProperties(tweets, vo);
        // 查发推文的用户的信息
        User user = userMapper.getByUserId(tweets.getUserId());
        Assert.notNull(user, "获取用户信息失败");
        vo.setUsername(user.getUsername());
        vo.setAvatar(user.getAvatar());
        // 查点赞数和评论数
        TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByTweetsId(tweets.getId());
        if (null == tweetsExtraInfo) {
            tweetsExtraInfo = new TweetsExtraInfo();
            tweetsExtraInfo.setTweetsId(tweets.getId());
            tweetsExtraInfo.setLikeCount(0L);
            tweetsExtraInfo.setCommentCount(0L);
            tweetsExtraInfoMapper.insert(tweetsExtraInfo);
        }
        vo.setLikeCount(tweetsExtraInfo.getLikeCount());
        vo.setCommentCount(tweetsExtraInfo.getCommentCount());
        // 查询趣图类型的信息
        if (tweets.getType() == Tweets.Type.INTERESTING.getCode()) {
            Boolean hasGift = interestingPictureMapper.get(tweets.getId()).getHasGift();
            vo.setHasGift(hasGift);
            vo.setIsInteresting(true);
        }
        return ResponseMessage.createBySuccess(vo);
    }

    @Override
    public ResponseMessage<List<GetTweetsVO>> getTweetsByUserId(Long userId, Long visitorUserId) {
        User user = userMapper.getByUserId(userId);
        Assert.notNull(user, "userId不存在");
        // 查推文信息
        List<Tweets> tweetsList = tweetsMapper.getByUserId(userId);
        List<GetTweetsVO> list = new LinkedList<>();
        for (Tweets tweets : tweetsList) {
            GetTweetsVO vo = new GetTweetsVO();
            BeanUtils.copyProperties(tweets, vo);
            // 发推文的用户的信息
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
            // 查点赞数和评论数
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByTweetsId(tweets.getId());
            if (null == tweetsExtraInfo) {
                tweetsExtraInfo = new TweetsExtraInfo();
                tweetsExtraInfo.setTweetsId(tweets.getId());
                tweetsExtraInfo.setLikeCount(0L);
                tweetsExtraInfo.setCommentCount(0L);
                tweetsExtraInfoMapper.insert(tweetsExtraInfo);
            }
            vo.setLikeCount(tweetsExtraInfo.getLikeCount());
            vo.setCommentCount(tweetsExtraInfo.getCommentCount());
            // 查询趣图类型的信息
            if (tweets.getType() == Tweets.Type.INTERESTING.getCode()) {
                Boolean hasGift = interestingPictureMapper.get(tweets.getId()).getHasGift();
                vo.setHasGift(hasGift);
                vo.setIsInteresting(true);
            }
            if (null != visitorUserId) {
                // 查点赞
                Boolean isLike = likeMapper.isLikeTweets(visitorUserId, tweets.getId());
                vo.setIsLike(isLike);
            }
            list.add(vo);
        }
        return ResponseMessage.createBySuccess(list);
    }

    @Override
    public ResponseMessage<List<GetTweetsVO>> getHomeTweets(Long userId) {
        List<Tweets> tweetsList = tweetsMapper.getHomeTweets(userId);
        List<GetTweetsVO> list = new LinkedList<>();
        for (Tweets tweets : tweetsList) {
            GetTweetsVO vo = new GetTweetsVO();
            BeanUtils.copyProperties(tweets, vo);
            // 发推文的用户的信息
            User user = userMapper.getByUserId(tweets.getUserId());
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
            // 查点赞数和评论数
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoMapper.getByTweetsId(tweets.getId());
            if (null == tweetsExtraInfo) {
                tweetsExtraInfo = new TweetsExtraInfo();
                tweetsExtraInfo.setTweetsId(tweets.getId());
                tweetsExtraInfo.setLikeCount(0L);
                tweetsExtraInfo.setCommentCount(0L);
                tweetsExtraInfoMapper.insert(tweetsExtraInfo);
            }
            vo.setLikeCount(tweetsExtraInfo.getLikeCount());
            vo.setCommentCount(tweetsExtraInfo.getCommentCount());
            // 查询趣图类型的信息
            if (tweets.getType() == Tweets.Type.INTERESTING.getCode()) {
                Boolean hasGift = interestingPictureMapper.get(tweets.getId()).getHasGift();
                vo.setHasGift(hasGift);
                vo.setIsInteresting(true);
            }
            // 查点赞
            Boolean isLike = likeMapper.isLikeTweets(userId, tweets.getId());
            vo.setIsLike(isLike);
            list.add(vo);
        }
        return ResponseMessage.createBySuccess(list);
    }

    @Override
    public ResponseMessage getMyLikeTweets(Long userId) {
        List<Tweets> tweetsList = tweetsMapper.getMyLikeTweets(userId);
        List<PublishTweetsVO> list = new LinkedList<>();
        for (Tweets tweets : tweetsList) {
            PublishTweetsVO vo = new PublishTweetsVO();
            BeanUtils.copyProperties(tweets, vo);
            list.add(vo);
        }
        return ResponseMessage.createBySuccess(list);
    }
}
