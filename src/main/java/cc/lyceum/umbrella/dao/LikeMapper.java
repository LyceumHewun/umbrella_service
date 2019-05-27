package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.Like;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 21:09
 */
@Mapper
@Component
public interface LikeMapper {

    /**
     * 根据tweets_id和user_id查是否点赞
     */
    @Select("SELECT COUNT(1) FROM `like` WHERE user_id=#{uid} AND tweets_id=#{tid} AND deleted=0")
    Boolean isLikeTweets(@Param("uid") Long userId, @Param("tid") Long tweetsId);

    @Select("SELECT COUNT(1) FROM `like` WHERE user_id=#{uid} AND comment_id=#{tid} AND deleted=0")
    Boolean isLikeComment(@Param("uid") Long userId, @Param("tid") Long commentId);

    @Insert("REPLACE INTO `like` (user_id, tweets_id, comment_id) VALUES " +
            "(#{l.userId}, #{l.tweetsId}, #{l.commentId})")
    @ResultType(Boolean.class)
    Boolean insert(@Param("l") Like like);

    @Update("UPDATE `like` SET deleted=1 WHERE id=#{id}")
    Boolean remove(@Param("id") Long id);

    @Update("UPDATE `like` SET deleted=1 WHERE user_id=#{l.userId} AND tweets_id=#{l.tweetsId}")
    Boolean removeByTweetsId(@Param("l") Like like);

    @Update("UPDATE `like` SET deleted=1 WHERE user_id=#{l.userId} AND comment_id=#{l.commentId}")
    Boolean removeByCommentsId(@Param("l") Like like);

    @Select("SELECT * FROM `like` WHERE user_id=#{uid} AND tweets_id=#{tid}")
    Like getByTweetsId(@Param("uid") Long userId, @Param("tid") Long tweetsId);

    @Select("SELECT * FROM `like` WHERE user_id=#{uid} AND comment_id=#{tid}")
    Like getByCommentId(@Param("uid") Long userId, @Param("tid") Long commentId);
}
