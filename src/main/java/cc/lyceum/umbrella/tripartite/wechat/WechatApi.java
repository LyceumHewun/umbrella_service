package cc.lyceum.umbrella.tripartite.wechat;

import cc.lyceum.umbrella.tripartite.wechat.pojo.AuthorizationInfo;
import cc.lyceum.umbrella.utils.okhttp.Client;
import cc.lyceum.umbrella.utils.okhttp.NotSafeHttpsClient;
import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

import static cc.lyceum.umbrella.tripartite.wechat.WechatConfig.APP_ID;
import static cc.lyceum.umbrella.tripartite.wechat.WechatConfig.APP_SECRET;


/**
 * 微信api
 */
public class WechatApi {

    private Client client;
    private Gson gson;


    public WechatApi() {
        this.client = new NotSafeHttpsClient();
        this.gson = new Gson();
    }

    /**
     * 登录凭证校验。通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。
     *
     * @param jsCode 通过 wx.login() 接口获得临时登录凭证
     * @return 微信code2Session接口返回实体类
     * @see <a href="https://developers.weixin.qq.com/miniprogram/dev/api/code2Session.html">https://developers.weixin.qq.com/miniprogram/dev/api/code2Session.html</a>
     */
    public AuthorizationInfo code2Session(String jsCode) {
        String json = client.getToString("https://api.weixin.qq.com/sns/jscode2session" +
                "?appid=" + APP_ID +
                "&secret=" + APP_SECRET +
                "&js_code=" + jsCode +
                "&grant_type=authorization_code");
        return gson.fromJson(json, AuthorizationInfo.class);
    }

    /**
     * 小程序解码
     */
    public String decrypt(String sessionKey, String iv, String encryptedData) {
        try {
            byte[] key2 = Base64.decode(sessionKey);
            byte[] iv2 = Base64.decode(iv);
            byte[] encData = Base64.decode(encryptedData);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv2);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key2, "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            //解析解密后的字符串
            return new String(cipher.doFinal(encData), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "解码失败";
    }


}
