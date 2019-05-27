package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.Follow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 11:21
 */
@Mapper
@Component
public interface FollowMapper {

    @Select("SELECT COUNT(1) FROM `follow` WHERE user_id=#{vid} AND following_user_id=#{uid} AND deleted=0")
    Boolean visitorUserIsFollowingThisUser(@Param("vid") Long visitorUserId, @Param("uid") Long userId);

    @Insert("REPLACE INTO `follow` (user_id, following_user_id) VALUES " +
            "(#{f.userId}, #{f.followingUserId})")
    Boolean insert(@Param("f") Follow follow);

    @Update("UPDATE `follow` SET deleted=1 " +
            "WHERE user_id=#{uid} AND following_user_id=#{fid}")
    Boolean delete(@Param("uid") Long userId, @Param("fid") Long followingUserId);

    @Select("SELECT * FROM `follow` WHERE user_id=#{uid}")
    List<Follow> getFollowingList(@Param("uid") Long userId);
}
