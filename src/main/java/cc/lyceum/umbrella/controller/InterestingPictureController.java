package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.ErrorCode;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.CheckInterestingTweetsDTO;
import cc.lyceum.umbrella.dto.PublishInterestingTweetsDTO;
import cc.lyceum.umbrella.entity.Tweets;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.entity.Wallet;
import cc.lyceum.umbrella.service.InterestingPictureService;
import cc.lyceum.umbrella.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 18:44
 */
@Slf4j
@RestController
@RequestMapping("interesting/tweets")
public class InterestingPictureController {

    private final InterestingPictureService interestingPictureService;
    private final PayService payService;

    @Autowired
    public InterestingPictureController(InterestingPictureService interestingPictureService, PayService payService) {
        this.interestingPictureService = interestingPictureService;
        this.payService = payService;
    }

    /**
     * 发趣图类型推文
     */
    @PostMapping("publishInterestingTweets")
    public ResponseMessage publishInterestingTweets(@RequestBody PublishInterestingTweetsDTO dto, HttpSession session) {
        // 校验
        Assert.notNull(dto.getLongitude());
        Assert.notNull(dto.getLatitude());
        Assert.notNull(dto.getPicture());
        Assert.notNull(dto.getHasGift());

        // 固定值
        dto.setPermissionType(Tweets.PermissionType.PUBLIC.getCode());
        dto.setType(Tweets.Type.INTERESTING.getCode());
        dto.setGiftBalance(dto.getGiftTotalAmount());

        User user = (User) session.getAttribute(SessionType.USER);
        dto.setUserId(user.getId());

        log.info("PublishInterestingTweetsDTO: {}", dto);

        // 如果有礼物 (hasGift)
        if (dto.getHasGift()) {
            Integer giftQuantity = dto.getGiftQuantity();
            BigDecimal giftTotalAmount = dto.getGiftTotalAmount();
            Assert.notNull(giftQuantity, "红包数量不能空");
            Assert.notNull(giftTotalAmount, "总金额不能空");
            int result = giftTotalAmount.compareTo(new BigDecimal(0.01 * giftQuantity));
            Assert.isTrue(result > -1, "金额不够分配");
            // 查钱包钱够不够
            Wallet wallet = payService.getWallet(user.getId()).getData();
            int result2 = giftTotalAmount.compareTo(wallet.getMoney());
            if (result2 == 1) {
                // 金额不足
                return ResponseMessage.createByErrorCodeData(ErrorCode.LACK_OF_FUNDS, giftTotalAmount);
            }
        }

        return interestingPictureService.publishInterestingTweets(dto);
    }

    /**
     * 校验趣图(互动)
     */
    @PostMapping("checkInterestingTweets")
    public ResponseMessage checkInterestingTweets(@RequestBody CheckInterestingTweetsDTO dto, HttpSession session) {
        // TODO 校验数据

        log.info("{}", dto);

        User user = (User) session.getAttribute(SessionType.USER);
        dto.setUserId(user.getId());

        return interestingPictureService.checkInterestingTweets(dto);
    }
}
