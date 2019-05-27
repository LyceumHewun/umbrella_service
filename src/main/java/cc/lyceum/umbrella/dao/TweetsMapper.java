package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.Like;
import cc.lyceum.umbrella.entity.Tweets;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 16:50
 */
@Mapper
@Component
public interface TweetsMapper {

    @Select("SELECT COUNT(id) FROM `tweets` WHERE user_id=#{id} AND deleted=0")
    Integer getTweetsCountByUserId(@Param("id") Long userId);

    @Select("SELECT * FROM `tweets` WHERE permission_type=1 AND deleted=0 " +
            "ORDER BY id DESC LIMIT 1000")
    List<Tweets> getAllPublicTweets();

    @Insert("INSERT INTO `tweets` (user_id, pictures, content, permission_type, type) VALUES " +
            "(#{t.userId}, #{t.pictures}, #{t.content}, #{t.permissionType}, #{t.type})")
    @ResultType(Boolean.class)
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean insert(@Param("t") Tweets tweets);

    @Select("SELECT * FROM `tweets` WHERE id=#{id}")
    Tweets getById(@Param("id") Long id);

    @Select("SELECT * FROM `tweets` WHERE user_id=#{id} " +
            "ORDER BY id DESC LIMIT 1000")
    List<Tweets> getByUserId(@Param("id") Long userId);

    /**
     * TODO 分页
     * 获取主页推文(关注的和自己的)
     */
    @Select("SELECT * FROM `tweets` " +
            "WHERE deleted=0 AND permission_type=1 AND " +
            "(user_id IN (SELECT following_user_id FROM `follow` WHERE user_id=#{id} AND deleted=0) OR user_id=#{id}) " +
            "ORDER BY id DESC")
    List<Tweets> getHomeTweets(@Param("id") Long userId);

    @Select("SELECT * FROM `tweets` WHERE deleted=0 AND permission_type=1 AND " +
            "id IN (SELECT tweets_id FROM `like` WHERE user_id=#{id} AND deleted=0 AND tweets_id IS NOT NULL GROUP BY tweets_id ORDER BY id DESC)")
    List<Tweets> getMyLikeTweets(@Param("id") Long userId);
}
