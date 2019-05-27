package cc.lyceum.umbrella.dto;

import com.google.gson.Gson;
import lombok.Data;

import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 17:33
 */
@Data
public class PublishTweetsDTO {

    private Long userId;
    private String pictures;
    private String content;
    private Integer permissionType;
    private Integer type;

    private List<String> picturesList;

    public void setPicturesList(List<String> picturesList) {
        this.picturesList = picturesList;

        // 转成JSON
        this.pictures = new Gson().toJson(picturesList);
    }
}
