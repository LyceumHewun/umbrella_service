package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.entity.Wallet;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 16:29
 */
public interface PayService {

    /**
     * 获取钱包信息, 如果查询为空, 则会插入一个新的数据
     *
     * @param userId 用户表id
     * @return ResponseMessage<Wallet>
     */
    ResponseMessage<Wallet> getWallet(Long userId);

    ResponseMessage wechatUnifiedorder(HttpServletRequest request, String openId, String tootalFee);

    ResponseMessage addWallet(String money,Long userId);
}
