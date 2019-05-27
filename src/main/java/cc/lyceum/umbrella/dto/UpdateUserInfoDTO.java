package cc.lyceum.umbrella.dto;

import lombok.Data;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 21:31
 */
@Data
public class UpdateUserInfoDTO {

    private Long id;
    private String countryCode;
    private String phone;
    private String username;
    private String password;
    private String avatar;
    private String introduction;
    private String website;
}
