package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dao.UserMapper;
import cc.lyceum.umbrella.dao.WalletMapper;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.entity.Wallet;
import cc.lyceum.umbrella.service.PayService;
import cc.lyceum.umbrella.tripartite.wechat.WechatPayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 16:29
 */
@Service
public class PayServiceImpl implements PayService {

    private WechatPayApi wechatPayApi = new WechatPayApi();

    private final UserMapper userMapper;
    private final WalletMapper walletMapper;

    @Autowired
    public PayServiceImpl(WalletMapper walletMapper, UserMapper userMapper) {
        this.walletMapper = walletMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseMessage<Wallet> getWallet(Long userId) {
        User user = userMapper.getByUserId(userId);
        Assert.notNull(user, "用户不存在");
        Wallet wallet = walletMapper.getByUserId(userId);
        if (null == wallet) {
            wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setMoney(BigDecimal.valueOf(0));
            Boolean result = walletMapper.insert(wallet);
            Assert.isTrue(result, "获取失败");
        }
        return ResponseMessage.createBySuccess(wallet);
    }

    @Override
    public ResponseMessage wechatUnifiedorder(HttpServletRequest request, String openId, String tootalFee) {
        Map<String, String> unifiedorder = wechatPayApi.unifiedorder(request, openId, tootalFee);
        return ResponseMessage.createBySuccess(unifiedorder);
    }

    @Override
    public ResponseMessage addWallet(String money, Long userId) {
        walletMapper.addMoney(userId, new BigDecimal(money));
        return ResponseMessage.createBySuccess();
    }
}
