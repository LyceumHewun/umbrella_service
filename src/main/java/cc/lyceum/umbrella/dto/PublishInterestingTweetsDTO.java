package cc.lyceum.umbrella.dto;

import com.google.gson.Gson;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 18:47
 */
@Data
public class PublishInterestingTweetsDTO {

    private Long userId;

    private String picture;
    private String content;
    private Integer permissionType;
    private Integer type;

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


    private List<String> picturesList;

    public void setPicturesList(List<String> picturesList) {
        this.picturesList = picturesList;

        // 转成JSON
        this.picture = new Gson().toJson(picturesList);
    }
}
