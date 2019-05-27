package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dao.FollowCountMapper;
import cc.lyceum.umbrella.dao.FollowMapper;
import cc.lyceum.umbrella.entity.Follow;
import cc.lyceum.umbrella.entity.FollowCount;
import cc.lyceum.umbrella.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 11:18
 */
@Service
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;
    private final FollowCountMapper followCountMapper;

    @Autowired
    public FollowServiceImpl(FollowMapper followMapper, FollowCountMapper followCountMapper) {
        this.followMapper = followMapper;
        this.followCountMapper = followCountMapper;
    }

    @Override
    public Boolean visitorUserIsFollowingThisUser(Long visitorUserId, Long userId) {
        Assert.notNull(visitorUserId);
        Assert.notNull(userId);
        return followMapper.visitorUserIsFollowingThisUser(visitorUserId, userId);
    }

    @Override
    public ResponseMessage followingUser(Long userId, Long followingUserId) {
        Assert.notNull(userId);
        Assert.notNull(followingUserId);
        Follow follow = new Follow();
        follow.setUserId(userId);
        follow.setFollowingUserId(followingUserId);
        Boolean result = followMapper.insert(follow);
        if (result) {
            this.addFollowing(userId);
            this.addFollower(followingUserId);
        }
        return ResponseMessage.createBySuccess();
    }

    @Override
    public ResponseMessage unfollowingUser(Long userId, Long followingUserId) {
        Assert.notNull(userId);
        Assert.notNull(followingUserId);
        Boolean delete = followMapper.delete(userId, followingUserId);
        if (delete) {
            this.removeFollowing(userId);
            this.removeFollower(followingUserId);
            return ResponseMessage.createBySuccess();
        }
        return ResponseMessage.createByErrorMessage("取消关注失败");
    }

    @Override
    public Boolean addFollowing(Long userId) {
        Boolean result = followCountMapper.addFollowing(userId);
        if (!result) {
            FollowCount followCount = followCountMapper.getByUserId(userId);
            if (null == followCount) {
                followCount = new FollowCount();
                followCount.setFollowing(1L);
                followCount.setFollower(0L);
                return followCountMapper.insert(followCount);
            }
        }
        return result;
    }

    @Override
    public Boolean addFollower(Long userId) {
        Boolean result = followCountMapper.addFollower(userId);
        if (!result) {
            FollowCount followCount = followCountMapper.getByUserId(userId);
            if (null == followCount) {
                followCount = new FollowCount();
                followCount.setFollowing(0L);
                followCount.setFollower(1L);
                return followCountMapper.insert(followCount);
            }
        }
        return result;
    }

    @Override
    public Boolean removeFollowing(Long userId) {
        return followCountMapper.removeFollowing(userId);
    }

    @Override
    public Boolean removeFollower(Long userId) {
        return followCountMapper.removeFollower(userId);
    }
}
