package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:25
 */
@Data
public class InterestingPictureTryRecord {

    private Long id;
    private Long userId;
    private Long tweetsId;
    private Boolean isSuccess;
    private String result;
    private String subMessage;
    /**
     * 是否获得红包
     */
    private Boolean gotGift;
    /**
     * 获得的红包金额
     */
    private BigDecimal giftAmount;
    private Date createTime;
    private Date modifyTime;
}
