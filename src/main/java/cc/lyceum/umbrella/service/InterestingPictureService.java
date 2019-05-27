package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dto.CheckInterestingTweetsDTO;
import cc.lyceum.umbrella.dto.PublishInterestingTweetsDTO;
import cc.lyceum.umbrella.vo.CheckInterestingTweetsVO;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 18:58
 */
public interface InterestingPictureService {

    ResponseMessage publishInterestingTweets(PublishInterestingTweetsDTO dto);

    ResponseMessage<CheckInterestingTweetsVO> checkInterestingTweets(CheckInterestingTweetsDTO dto);
}
