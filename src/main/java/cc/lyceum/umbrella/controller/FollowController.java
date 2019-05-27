package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.FollowingUserDTO;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 11:29
 */
@RestController
@RequestMapping("follow")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    /**
     * 关注
     */
    @PostMapping("followingUser")
    public ResponseMessage followingUser(@RequestBody FollowingUserDTO dto, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        return followService.followingUser(user.getId(), dto.getFollowingUserId());
    }

    /**
     * 取消关注
     */
    @PostMapping("unFollowingUser")
    public ResponseMessage unFollowingUser(@RequestBody FollowingUserDTO dto, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        return followService.unfollowingUser(user.getId(), dto.getFollowingUserId());
    }
}
