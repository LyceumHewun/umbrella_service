package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.ResponseMessage;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 11:18
 */
public interface FollowService {

    /**
     * @param visitorUserId 访问者id
     * @param userId        目标userid
     */
    Boolean visitorUserIsFollowingThisUser(Long visitorUserId, Long userId);

    ResponseMessage followingUser(Long userId, Long followingUserId);

    ResponseMessage unfollowingUser(Long userId, Long followingUserId);

    Boolean addFollowing(Long userId);

    Boolean addFollower(Long userId);

    Boolean removeFollowing(Long userId);

    Boolean removeFollower(Long userId);
}
