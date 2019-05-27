package cc.lyceum.umbrella.vo;

import cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Data;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 20:29
 */
@Data
public class PublishTweetsVO {

    private Long id;
    private Long userId;
    private String pictures;
    private String content;
    private Integer permissionType;
    private Integer type;
    private Date createTime;

    private List<String> picturesList;

    public void setPictures(String pictures) {
        this.pictures = pictures;

        picturesList = new LinkedList<>();
        JsonArray jsonArray = new JsonParser().parse(pictures).getAsJsonArray();
        for (JsonElement element : jsonArray) {
            String url = QiniuConfig.CDN_URL + element.getAsString();
            picturesList.add(url);
        }
    }
}
