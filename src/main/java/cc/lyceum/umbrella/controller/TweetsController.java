package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.PublishTweetsDTO;
import cc.lyceum.umbrella.entity.Tweets;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.service.LikeService;
import cc.lyceum.umbrella.service.TweetsService;
import cc.lyceum.umbrella.vo.GetTweetsVO;
import cc.lyceum.umbrella.vo.PublishTweetsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-16 17:16
 */
@Slf4j
@RestController
@RequestMapping("tweets")
public class TweetsController {

    private final TweetsService tweetsService;
    private final LikeService likeService;

    @Autowired
    public TweetsController(TweetsService tweetsService, LikeService likeService) {
        this.tweetsService = tweetsService;
        this.likeService = likeService;
    }

    /**
     * 广场推文(所有人可见)
     */
    @GetMapping("getSquareTweets")
    public ResponseMessage getSquareTweets(HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        Long userId = null;
        if (null != user) {
            userId = user.getId();
        }
        // TODO 只拿了全部 待优化
        return tweetsService.getSquareTweets(userId);
    }

    /**
     * 发布普通推文
     */
    @PostMapping("publishTweets")
    public ResponseMessage<PublishTweetsVO> publishTweets(@RequestBody PublishTweetsDTO dto, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);

        String pictures = dto.getPictures();
        Assert.hasLength(pictures, "图片不能为空");
        Integer permissionType = dto.getPermissionType();
        Assert.notNull(permissionType, "查看权限不能为空");

        // 固定值
        dto.setUserId(user.getId());
        dto.setType(Tweets.Type.NORMAL.getCode());

        return tweetsService.publishTweets(dto);
    }

    /**
     * 根据推文id获取推文
     * (公开接口)
     */
    @GetMapping("getTweets")
    public ResponseMessage<GetTweetsVO> getTweets(@RequestParam("id") Long id, HttpSession session) {

        GetTweetsVO vo = tweetsService.getTweetsById(id).getData();

        User user = (User) session.getAttribute(SessionType.USER);
        if (null != user) {
            // 查点赞
            Boolean isLike = likeService.isLikeTweets(user.getId(), id);
            vo.setIsLike(isLike);
        }

        return ResponseMessage.createBySuccess(vo);
    }

    /**
     * TODO 分页
     * 根据userId获取推文
     */
    @GetMapping("getTweetsListByUserId")
    public ResponseMessage<List<GetTweetsVO>> getTweetsListByUserId(@RequestParam("userId") Long userId, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        Long id = null;
        if (null != user) {
            id = user.getId();
        }
        return tweetsService.getTweetsByUserId(userId, id);
    }

    /**
     * 获取主页推文(关注的和自己的)
     */
    @GetMapping("getHomeTweets")
    public ResponseMessage<List<GetTweetsVO>> getHomeTweets(HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        return tweetsService.getHomeTweets(user.getId());
    }

    /**
     * 点赞过的图片(主页心形)
     */
    @GetMapping("getMyLikeTweets")
    public ResponseMessage getMyLikeTweets(HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        return tweetsService.getMyLikeTweets(user.getId());
    }
}
