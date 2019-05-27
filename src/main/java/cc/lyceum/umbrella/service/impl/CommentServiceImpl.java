package cc.lyceum.umbrella.service.impl;

import cc.lyceum.umbrella.ResponseMessage;
import cc.lyceum.umbrella.dao.CommentMapper;
import cc.lyceum.umbrella.dao.LikeMapper;
import cc.lyceum.umbrella.dao.UserMapper;
import cc.lyceum.umbrella.entity.Comment;
import cc.lyceum.umbrella.entity.TweetsExtraInfo;
import cc.lyceum.umbrella.entity.User;
import cc.lyceum.umbrella.service.CommentService;
import cc.lyceum.umbrella.service.TweetsExtraInfoService;
import cc.lyceum.umbrella.vo.GetCommentsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Lyceum Hewun
 * @date 2019-05-17 1:16
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final UserMapper userMapper;
    private final TweetsExtraInfoService tweetsExtraInfoService;
    private final LikeMapper likeMapper;

    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper, UserMapper userMapper, TweetsExtraInfoService tweetsExtraInfoService, LikeMapper likeMapper) {
        this.commentMapper = commentMapper;
        this.userMapper = userMapper;
        this.tweetsExtraInfoService = tweetsExtraInfoService;
        this.likeMapper = likeMapper;
    }

    @Override
    public ResponseMessage<List<GetCommentsVO>> getCommentsByTweetsId(Long tweetsId, Long visitorUserId) {
        List<Comment> commentList = commentMapper.getCommentByTweetsId(tweetsId);
        List<GetCommentsVO> list = new LinkedList<>();
        for (Comment comment : commentList) {
            GetCommentsVO vo = new GetCommentsVO();
            BeanUtils.copyProperties(comment, vo);
            // 获取用户名和用户头像
            User user = userMapper.getByUserId(comment.getUserId());
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
            // 获取点赞数和评论数
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoService.getByCommentId(comment.getId());
            vo.setLikeCount(tweetsExtraInfo.getLikeCount());
            vo.setCommentCount(tweetsExtraInfo.getCommentCount());
            // 是否点赞
            if (null != visitorUserId) {
                Boolean isLike = likeMapper.isLikeComment(visitorUserId, comment.getId());
                vo.setIsLike(isLike);
            }
            list.add(vo);
        }
        return ResponseMessage.createBySuccess(list);
    }

    @Override
    public ResponseMessage<List<GetCommentsVO>> getSubCommentsByCommentId(Long commentId, Long visitorUserId) {
        List<Comment> commentList = commentMapper.getCommentByCommentId(commentId);
        List<GetCommentsVO> list = new LinkedList<>();
        for (Comment comment : commentList) {
            GetCommentsVO vo = new GetCommentsVO();
            BeanUtils.copyProperties(comment, vo);
            // 获取用户名和用户头像
            User user = userMapper.getByUserId(comment.getUserId());
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
            // 获取点赞数和评论数
            TweetsExtraInfo tweetsExtraInfo = tweetsExtraInfoService.getByCommentId(comment.getId());
            vo.setLikeCount(tweetsExtraInfo.getLikeCount());
            vo.setCommentCount(tweetsExtraInfo.getCommentCount());
            // 是否点赞
            if (null != visitorUserId) {
                Boolean isLike = likeMapper.isLikeComment(visitorUserId, comment.getId());
                vo.setIsLike(isLike);
            }
            list.add(vo);
        }
        return ResponseMessage.createBySuccess(list);
    }

    @Override
    public ResponseMessage publishComment(Comment comment) {
        Boolean result = commentMapper.insert(comment);
        Assert.isTrue(result, "插入失败");
        if (null != comment.getCommentId()) {
            tweetsExtraInfoService.addCommentCountByCommentId(comment.getCommentId());
        } else {
            // 必定有 tweets_id
            tweetsExtraInfoService.addCommentCountByTweetsId(comment.getTweetsId());
        }
        return ResponseMessage.createBySuccess();
    }
}
