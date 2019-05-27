package cc.lyceum.umbrella.dto;

import com.google.gson.Gson;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-18 2:40
 */
@Data
public class CheckInterestingTweetsDTO {

    private Long userId;
    private Long tweetsId;

    // 校验数据
    private String picture;
    private BigDecimal longitude;
    private BigDecimal latitude;

    private List<String> picturesList;

    public void setPicturesList(List<String> picturesList) {
        this.picturesList = picturesList;

        // 转成JSON
        this.picture = new Gson().toJson(picturesList);
    }

}
