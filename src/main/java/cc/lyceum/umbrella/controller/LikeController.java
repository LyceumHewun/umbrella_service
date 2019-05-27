package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.LikeDTO;
import cc.lyceum.umbrella.entity.Like;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 13:24
 */
@Slf4j
@RestController
@RequestMapping("like")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    /**
     * 点赞
     */
    @PostMapping("addLike")
    public ResponseMessage addLike(@RequestBody LikeDTO dto, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        Like like = new Like();
        like.setUserId(user.getId());
        like.setTweetsId(dto.getTweetsId());
        like.setCommentId(dto.getCommentId());
        return likeService.addLike(like);
    }

    /**
     * 取消点赞
     */
    @PostMapping("removeLike")
    public ResponseMessage removeLike(@RequestBody LikeDTO dto, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        Like like = new Like();
        like.setUserId(user.getId());
        like.setTweetsId(dto.getTweetsId());
        like.setCommentId(dto.getCommentId());
        return likeService.remove(like);
    }
}
