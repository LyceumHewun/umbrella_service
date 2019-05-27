package cc.lyceum.umbrella.dto;

import lombok.Data;

/**
 * @author Lyceum Hewun
 * @date 2019-05-15 11:19
 */
@Data
public class WechatLoginDTO {

    private String code;

    private String encryptedData;
    private String iv;
    private String rawData;
    private String signature;
}
