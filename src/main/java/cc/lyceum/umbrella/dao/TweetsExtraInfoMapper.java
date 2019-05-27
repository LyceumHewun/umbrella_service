package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.TweetsExtraInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 21:31
 */
@Mapper
@Component
public interface TweetsExtraInfoMapper {

    @Select("SELECT * FROM `tweets_extra_info` WHERE tweets_id=#{tid}")
    TweetsExtraInfo getByTweetsId(@Param("tid") Long tweetsId);

    @Select("SELECT * FROM `tweets_extra_info` WHERE comment_id=#{cid}")
    TweetsExtraInfo getByCommentId(@Param("cid") Long commentId);

    @Insert("INSERT INTO `tweets_extra_info` (tweets_id, comment_id, comment_count, like_count) VALUES " +
            "(#{t.tweetsId}, #{t.commentId}, #{t.commentCount}, #{t.likeCount})")
    Boolean insert(@Param("t") TweetsExtraInfo tweetsExtraInfo);

    @Update("UPDATE `tweets_extra_info` SET comment_count = comment_count + 1 " +
            "WHERE tweets_id=${id}")
    Boolean addCommentCountByTweetsId(@Param("id") Long tweetsId);

    @Update("UPDATE `tweets_extra_info` SET like_count = like_count + 1 " +
            "WHERE tweets_id=${id}")
    Boolean addLikeCountByTweetsId(@Param("id") Long tweetsId);

    @Update("UPDATE `tweets_extra_info` SET comment_count = comment_count + 1 " +
            "WHERE comment_id=${id}")
    Boolean addCommentCountByCommentId(@Param("id") Long commentId);

    @Update("UPDATE `tweets_extra_info` SET like_count = like_count + 1 " +
            "WHERE comment_id=${id}")
    Boolean addLikeCountByCommentId(@Param("id") Long commentId);

    @Update("UPDATE `tweets_extra_info` SET like_count = like_count - 1 " +
            "WHERE tweets_id=${id} AND like_count>0")
    Boolean removeLikeCountByTweetsId(@Param("id") Long tweetsId);

    @Update("UPDATE `tweets_extra_info` SET like_count = like_count - 1 " +
            "WHERE comment_id=${id} AND like_count>0")
    Boolean removeLikeCountByCommentId(@Param("id") Long commentId);
}
