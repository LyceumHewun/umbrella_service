package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dao.InterestingPictureMapper;
import cc.lyceum.umbrella.dao.TweetsMapper;
import cc.lyceum.umbrella.dao.WalletMapper;
import cc.lyceum.umbrella.dto.CheckInterestingTweetsDTO;
import cc.lyceum.umbrella.dto.PublishInterestingTweetsDTO;
import cc.lyceum.umbrella.entity.InterestingPicture;
import cc.lyceum.umbrella.entity.Tweets;
import cc.lyceum.umbrella.service.InterestingPictureService;
import cc.lyceum.umbrella.tripartite.baidu.BaiduAiAPi;
import cc.lyceum.umbrella.tripartite.qiniu.QiniuConfig;
import cc.lyceum.umbrella.utils.DistanceUtil;
import cc.lyceum.umbrella.vo.CheckInterestingTweetsVO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 18:59
 */
@Slf4j
@Service
public class InterestingPictureServiceImpl implements InterestingPictureService {

    private BaiduAiAPi baiduAiAPi = new BaiduAiAPi();

    private final TweetsMapper tweetsMapper;
    private final InterestingPictureMapper interestingPictureMapper;
    // TODO 扣钱 加钱 都应该用 WalletService
    private final WalletMapper walletMapper;

    @Autowired
    public InterestingPictureServiceImpl(InterestingPictureMapper interestingPictureMapper, TweetsMapper tweetsMapper, WalletMapper walletMapper) {
        this.interestingPictureMapper = interestingPictureMapper;
        this.tweetsMapper = tweetsMapper;
        this.walletMapper = walletMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage publishInterestingTweets(PublishInterestingTweetsDTO dto) {
        Tweets tweets = new Tweets();
        BeanUtils.copyProperties(dto, tweets);
        tweets.setPictures(dto.getPicture());

        Boolean result = tweetsMapper.insert(tweets);
        if (result) {
            // 获取索引
            String galleryIndex = baiduAiAPi.userIdToGalleryIndex(dto.getUserId());
            dto.setGalleryIndex(galleryIndex);

            InterestingPicture interestingPicture = new InterestingPicture();
            BeanUtils.copyProperties(dto, interestingPicture);
            interestingPicture.setTweetsId(tweets.getId());
            interestingPictureMapper.insert(interestingPicture);

            // 百度ai入库
            String pictureUrl = QiniuConfig.CDN_URL + dto.getPicturesList().get(0);
            baiduAiAPi.addSimilarPicture(pictureUrl, String.valueOf(tweets.getId()), galleryIndex);

            if (dto.getHasGift()) {
                // 扣钱
                walletMapper.deductingMoney(dto.getUserId(), dto.getGiftTotalAmount());
            }

            return ResponseMessage.createBySuccess(tweets);
        }

        return ResponseMessage.createByError("发布失败");
    }

    @Override
    public ResponseMessage<CheckInterestingTweetsVO> checkInterestingTweets(CheckInterestingTweetsDTO dto) {
        InterestingPicture interestingPicture = interestingPictureMapper.get(dto.getTweetsId());
        // 校验经纬度
        // 500m米范围
        double distance = DistanceUtil.getDistance(dto.getLongitude(), dto.getLatitude(), interestingPicture.getLongitude(), interestingPicture.getLatitude());
        Assert.isTrue(distance < 0.5, "超出范围500米");

        // baiduAI接口校验
        String pictureUrl = QiniuConfig.CDN_URL + dto.getPicturesList().get(0);
        ResponseMessage<JSONObject> responseMessage = baiduAiAPi.searchSimilarPicture(pictureUrl, String.valueOf(interestingPicture.getTweetsId()), interestingPicture.getGalleryIndex());
        JSONObject jsonObject = responseMessage.getData();
        log.info("结果: {}", jsonObject);
        BigDecimal score = jsonObject.getBigDecimal("score");

        // TODO 记录入库

        int result = score.compareTo(new BigDecimal(0.90));
        DecimalFormat df = new DecimalFormat("0.00%");
        Assert.isTrue(result > -1, df.format(score) + "相似度");

        CheckInterestingTweetsVO vo = new CheckInterestingTweetsVO();
        vo.setHiddenConent(interestingPicture.getHiddenContent());

        BigDecimal giftBalance = interestingPicture.getGiftBalance();
        if (interestingPicture.getHasGift() && giftBalance.compareTo(BigDecimal.valueOf(0)) == 1) {
            // TODO 获得红包
            // TODO 临时写
            Integer giftQuantity = interestingPicture.getGiftQuantity();
            BigDecimal b = giftBalance.divide(BigDecimal.valueOf(giftQuantity));
            vo.setGotMoney(b.toPlainString());
        }

        return ResponseMessage.createBySuccess(vo);
    }
}
