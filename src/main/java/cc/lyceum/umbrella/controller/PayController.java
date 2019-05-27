package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.WechatUnifiedorderDTO;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.entity.Wallet;
import cc.lyceum.umbrella.service.PayService;
import cc.lyceum.umbrella.tripartite.wechat.WechatApi;
import cc.lyceum.umbrella.tripartite.wechat.WechatUtils;
import cc.lyceum.umbrella.tripartite.wechat.pojo.AuthorizationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 19:56
 */
@Slf4j
@RestController
@RequestMapping("pay")
public class PayController {

    private WechatApi wechatApi = new WechatApi();

    private final PayService payService;

    @Autowired
    public PayController(PayService payService) {
        this.payService = payService;
    }

    /**
     * 获取钱包金额
     */
    @GetMapping("getWalletMoney")
    public ResponseMessage getWalletMoney(HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        Wallet wallet = payService.getWallet(user.getId()).getData();

        return ResponseMessage.createBySuccess(wallet.getMoney());
    }

    @PostMapping("wechatUnifiedorder")
    public ResponseMessage wechatUnifiedorder(@RequestBody WechatUnifiedorderDTO dto, HttpServletRequest request, HttpSession session) {
        AuthorizationInfo authorizationInfo = wechatApi.code2Session(dto.getCode());

        String fee = WechatUtils.changeY2F(dto.getMoney());

        return payService.wechatUnifiedorder(request, authorizationInfo.getOpenid(), fee);
    }

    /**
     * TODO 演示用 之后要删除
     * 高危
     */
    @GetMapping("add")
    public ResponseMessage add(@RequestParam("money") String money, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);

        return payService.addWallet(money, user.getId());
    }
}
