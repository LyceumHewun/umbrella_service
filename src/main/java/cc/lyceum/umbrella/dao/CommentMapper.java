package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:18
 */
@Mapper
@Component
public interface CommentMapper {

    @Select("SELECT * FROM `comment` WHERE tweets_id=#{id} AND deleted=0 AND comment_id IS NULL " +
            "ORDER BY id DESC")
    List<Comment> getCommentByTweetsId(@Param("id") Long tweetsid);

    @Select("SELECT * FROM `comment` WHERE comment_id=#{id} AND deleted=0 " +
            "ORDER BY id DESC")
    List<Comment> getCommentByCommentId(@Param("id") Long commentId);

    @Insert("INSERT INTO `comment` (user_id, tweets_id, comment_id, content) VALUES " +
            "(#{c.userId}, #{c.tweetsId}, #{c.commentId}, #{c.content})")
    Boolean insert(@Param("c") Comment comment);
}
