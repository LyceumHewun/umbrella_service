package cc.lyceum.umbrella.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author Lyceum Hewun
 * @date 2019-05-13 23:20
 */
@Data
public class InterestingPicture {

    private Long tweetsId;
    private String picture;
    /**
     * 百度AI开放平台自建图库的索引
     */
    private String galleryIndex;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String hiddenContent;
    private Boolean hasGift;
    /**
     * 红包总数
     */
    private Integer giftQuantity;
    /**
     * 红包总金额
     */
    private BigDecimal giftTotalAmount;
    /**
     * 红包剩余金额
     */
    private BigDecimal giftBalance;
    private Date createTime;
    private Date modifyTime;
}
