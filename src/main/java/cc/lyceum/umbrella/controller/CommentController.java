package cc.lyceum.umbrella.controller;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.constants.SessionType;
import cc.lyceum.umbrella.dto.PublishCommentDTO;
import cc.lyceum.umbrella.entity.Comment;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.service.CommentService;
import cc.lyceum.umbrella.vo.GetCommentsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:13
 */
@Slf4j
@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * TODO 分页
     * 根据推文id获取评论
     */
    @GetMapping("getComments")
    public ResponseMessage<List<GetCommentsVO>> getComments(@RequestParam("tweetsId") Long tweetsId, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        return commentService.getCommentsByTweetsId(tweetsId, user.getId());
    }

    /**
     * 发表评论
     */
    @PostMapping("publishComment")
    public ResponseMessage publishComment(@RequestBody PublishCommentDTO dto, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);

        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        comment.setUserId(user.getId());

        return commentService.publishComment(comment);
    }

    /**
     * TODO 分页
     * 获取子评论
     */
    @GetMapping("getSubComments")
    public ResponseMessage<List<GetCommentsVO>> getSubComments(@RequestParam("commentId") Long commentId, HttpSession session) {
        User user = (User) session.getAttribute(SessionType.USER);
        return commentService.getSubCommentsByCommentId(commentId, user.getId());
    }
}
