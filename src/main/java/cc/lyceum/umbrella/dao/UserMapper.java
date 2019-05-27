package cc.lyceum.umbrella.dao;

import cc.lyceum.umbrella.dao.provider.UserProvider;
import cc.lyceum.umbrella.dto.UpdateUserInfoDTO;
import cc.lyceum.umbrella.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 12:09
 */
@Mapper
@Component
public interface UserMapper {

    @Select("SELECT * FROM `user` WHERE id=#{id}")
    User getByUserId(Long id);

    @Select("SELECT * FROM `user` WHERE phone=#{phone}")
    User getByPhone(String phone);

    @Select("SELECT * FROM `user` WHERE username=#{username}")
    User getByUsername(String username);

    @Select("SELECT COUNT(id) FROM `user` WHERE phone=#{p}")
    @ResultType(Boolean.class)
    Boolean phoneIsExist(@Param("p") String phone);

    @Select("SELECT COUNT(id) FROM `user` WHERE username=#{n}")
    @ResultType(Boolean.class)
    Boolean usernameIsExist(@Param("n") String username);

    @Insert("INSERT INTO `user` (phone, username, password) VALUES " +
            "(#{u.phone}, #{u.username}, #{u.password})")
    @ResultType(Boolean.class)
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Boolean signup(@Param("u") User user);

    @UpdateProvider(type = UserProvider.class, method = "updateUserInfo")
    @ResultType(Boolean.class)
    Boolean updateUserInfo(@Param("d") UpdateUserInfoDTO dto);

    @Select("SELECT avatar FROM `user` WHERE id=#{id}")
    String getAvatarById(@Param("id") Long userId);
}
