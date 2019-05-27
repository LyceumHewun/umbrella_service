package cc.lyceum.umbrella.dto;

import lombok.Data;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 12:53
 */
@Data
public class GetWechatPhoneDTO {

    private String encryptedData;
    private String iv;
}
