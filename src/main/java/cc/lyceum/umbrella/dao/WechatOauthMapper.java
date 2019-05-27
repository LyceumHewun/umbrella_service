package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.entity.WechatOauth;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 12:04
 */
@Mapper
@Component
public interface WechatOauthMapper {

    @Select("SELECT * FROM `wechat_oauth` WHERE union_id=#{id}")
    WechatOauth getByUnionId(@Param("id") String unionId);

    @Insert("INSERT INTO `wechat_oauth` (user_id, mini_programs_open_id, union_id) VALUES " +
            "(#{id}, #{oid}, #{uid})")
    @ResultType(Boolean.class)
    Boolean insert(@Param("id") Long userId, @Param("oid") String miniProgramsOpenId, @Param("uid") String unionId);
}
