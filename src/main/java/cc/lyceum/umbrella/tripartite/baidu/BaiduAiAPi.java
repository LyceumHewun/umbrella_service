package cc.lyceum.umbrella.tripartite.baidu;

import cc.lyceum.umbrella.ResponseMessage;
import com.baidu.aip.imagesearch.AipImageSearch;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;

import static cc.lyceum.umbrella.tripartite.baidu.BaiduAiConfig.*;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 21:38
 */
@Slf4j
public class BaiduAiAPi {

    private AipImageSearch client;

    public BaiduAiAPi() {
        client = new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);
    }

    /**
     * 相似图入库
     */
    public Boolean addSimilarPicture(String pictureUrl, String tweetsId, String galleryIndex) {
        HashMap<String, String> options = new HashMap<>(2);
        options.put("brief", tweetsId);
        options.put("tags", galleryIndex);
        JSONObject jsonObject = client.similarAddUrl(pictureUrl, options);
        log.info("addSimilarPicture: {}", jsonObject);
        if (jsonObject.has("cont_sign")) {
            return true;
        }
        throw new RuntimeException(jsonObject.getString("error_msg"));
    }

    public ResponseMessage<JSONObject> searchSimilarPicture(String pictureUrl, String tweetsId, String galleryIndex) {
        HashMap<String, String> options = new HashMap<>(1);
        options.put("tags", galleryIndex);
        JSONObject jsonObject = client.similarSearchUrl(pictureUrl, options);
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            if (object.getString("brief").equals(tweetsId)) {
                BigDecimal score = object.getBigDecimal("score");
                log.info("searchSimilarPicture: {}", object);

                return ResponseMessage.createBySuccess(object);
            }
        }
//        throw new RuntimeException(jsonObject.getString("error_msg"));
        throw new RuntimeException();
    }

    /**
     * user_id 转 索引
     */
    public String userIdToGalleryIndex(Long userId) {
        BigDecimal b1 = new BigDecimal(userId);
        BigDecimal b2 = new BigDecimal(65534);
        BigDecimal[] result = b1.divideAndRemainder(b2);
        String a = result[0].add(new BigDecimal(1)).toPlainString();
        String b = result[1].add(new BigDecimal(1)).toPlainString();
        return a + "," + b;
    }
}
