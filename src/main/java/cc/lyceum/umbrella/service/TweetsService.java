package cc.lyceum.umbrella.service;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dto.PublishTweetsDTO;
import cc.lyceum.umbrella.vo.GetTweetsVO;
import cc.lyceum.umbrella.vo.PublishTweetsVO;

import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 17:19
 */
public interface TweetsService {

    ResponseMessage getSquareTweets(Long visitorUserId);

    ResponseMessage<PublishTweetsVO> publishTweets(PublishTweetsDTO dto);

    ResponseMessage<GetTweetsVO> getTweetsById(Long id);

    /**
     * TODO 分页
     * 获取用户页面的推文
     *
     * @param userId        userId
     * @param visitorUserId 浏览的用户的userId
     * @return ResponseMessage<List<GetTweetsVO>>
     */
    ResponseMessage<List<GetTweetsVO>> getTweetsByUserId(Long userId, Long visitorUserId);

    /**
     * 获取主页推文(关注的和自己的)
     */
    ResponseMessage<List<GetTweetsVO>> getHomeTweets(Long userId);

    ResponseMessage getMyLikeTweets(Long userId);
}
