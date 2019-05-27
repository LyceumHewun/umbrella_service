package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.FollowCount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 16:43
 */
@Mapper
@Component
public interface FollowCountMapper {

    @Insert("INSERT INTO `follow_count` (user_id, following, follower) VALUES " +
            "(#{f.userId}, #{f.following}, #{f.follower})")
    @ResultType(Boolean.class)
    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="user_id")
    Boolean insert(@Param("f") FollowCount followCount);

    @Select("SELECT * FROM `follow_count` WHERE user_id=#{id}")
    FollowCount getByUserId(@Param("id") Long userId);

    @Update("UPDATE `follow_count` SET following = following + 1 " +
            "WHERE user_id=#{id}")
    Boolean addFollowing(@Param("id") Long userId);

    @Update("UPDATE `follow_count` SET follower = follower + 1 " +
            "WHERE user_id=#{id}")
    Boolean addFollower(@Param("id") Long userId);

    @Update("UPDATE `follow_count` SET following = following - 1 " +
            "WHERE user_id=#{id} AND following>0")
    Boolean removeFollowing(@Param("id") Long userId);

    @Update("UPDATE `follow_count` SET follower = follower - 1 " +
            "WHERE user_id=#{id} AND follower>0")
    Boolean removeFollower(@Param("id") Long userId);
}
