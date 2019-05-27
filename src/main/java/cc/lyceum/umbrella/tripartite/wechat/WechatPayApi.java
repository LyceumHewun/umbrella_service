package cc.lyceum.umbrella.tripartite.wechat;

import com.jpay.ext.kit.IpKit;
import com.jpay.ext.kit.PaymentKit;
import com.jpay.ext.kit.StrKit;
import com.jpay.weixin.api.WxPayApi;
import com.jpay.weixin.api.WxPayApiConfig;
import com.jpay.weixin.api.WxPayApiConfigKit;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static cc.lyceum.umbrella.tripartite.wechat.WechatConfig.*;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 23:54
 */
@Slf4j
public class WechatPayApi {

    WxPayApiConfig wxPayApiConfig;

    public WechatPayApi() {
        wxPayApiConfig = WxPayApiConfig.New()
                .setAppId(APP_ID)
                .setMchId(MCH_ID)
                .setPaternerKey(MCH_KEY)
                .setPayModel(WxPayApiConfig.PayModel.BUSINESSMODEL);

    }

    /**
     * 统一下单
     */
    public Map<String, String> unifiedorder(HttpServletRequest request, String openId, String tootalFee) {

        String ip = IpKit.getRealIp(request);
        if (StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }

        WxPayApiConfigKit.putApiConfig(wxPayApiConfig);

        Map<String, String> params = WxPayApiConfigKit
                .getWxPayApiConfig()
                .setOpenId(openId)
                .setAttach("测试")
                .setBody("小程序支付测试")
                .setSpbillCreateIp(ip)
                // 单位分
                .setTotalFee(tootalFee)
                .setTradeType(WxPayApi.TradeType.JSAPI)
                .setNotifyUrl("https://www.lyceum.cc/")
                .setOutTradeNo(String.valueOf(System.currentTimeMillis()))
                .build();

        String xmlResult = WxPayApi.pushOrder(false, params);


        log.info(xmlResult);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);

        String return_code = result.get("return_code");
        String return_msg = result.get("return_msg");
        if (!PaymentKit.codeIsOK(return_code)) {
            throw new RuntimeException(return_msg);
        }
        String result_code = result.get("result_code");
        if (!PaymentKit.codeIsOK(result_code)) {
            throw new RuntimeException(return_msg);
        }

        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepay_id = result.get("prepay_id");
        //封装调起微信支付的参数https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=7_7&index=5
        Map<String, String> packageParams = new HashMap<>();
        packageParams.put("appId", WxPayApiConfigKit.getWxPayApiConfig().getAppId());
        packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
        packageParams.put("nonceStr", System.currentTimeMillis() + "");
        packageParams.put("package", "prepay_id=" + prepay_id);
        packageParams.put("signType", "MD5");
        String packageSign = PaymentKit.createSign(packageParams, WxPayApiConfigKit.getWxPayApiConfig().getPaternerKey());
        packageParams.put("paySign", packageSign);

        log.info("{}", packageParams);
        return packageParams;
    }
}
