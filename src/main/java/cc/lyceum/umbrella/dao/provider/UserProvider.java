package cc.lyceum.umbrella.dao.provider;

import cc.lyceum.umbrella.dto.UpdateUserInfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 21:38
 */
public class UserProvider {

    public String updateUserInfo(@Param("d") UpdateUserInfoDTO dto) {
        return new SQL() {
            {
                UPDATE("`user`");

                if (StringUtils.isNotEmpty(dto.getCountryCode())) {
                    SET("country_code=#{d.countryCode}");
                }
                if (StringUtils.isNotEmpty(dto.getPhone())) {
                    SET("phone=#{d.phone}");
                }
                if (StringUtils.isNotEmpty(dto.getUsername())) {
                    SET("username=#{d.username}");
                }
                if (StringUtils.isNotEmpty(dto.getPassword())) {
                    SET("password=#{d.password}");
                }
                if (StringUtils.isNotEmpty(dto.getAvatar())) {
                    SET("avatar=#{d.avatar}");
                }
                if (StringUtils.isNotEmpty(dto.getIntroduction())) {
                    SET("introduction=#{d.introduction}");
                }
                if (StringUtils.isNotEmpty(dto.getWebsite())) {
                    SET("website=#{d.website}");
                }

                WHERE("id=#{d.id}");
            }
        }.toString();
    }
}
