package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.card.UserCard;
import com.iwen.web.qingliao.push.bean.db.FeedComment;
import com.iwen.web.qingliao.push.bean.db.User;

/**
 * 查询帖子的评论回调模型
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/12/02 20:16
 */
public class QueryFeedCommentsRsp {
    // 这条评论的id
    @Expose
    private String commentId;
    // 评论内容
    @Expose
    private String commentText;
    // 评论类型
    @Expose
    private int commentType;
    // 高
    @Expose
    private int height;
    // 宽
    @Expose
    private int width;
    // 级别
    @Expose
    private int commentLevel;
    // 图片url
    @Expose
    private String imageUrl;
    // 视频url
    @Expose
    private String videoUrl;;
    // 评论点赞数
    @Expose
    private int lickCount;

    // 每一个帖子对应的用户信息
    @Expose
    private UserCard user;

    public QueryFeedCommentsRsp(FeedComment feedComment, User user) {
        this.commentId = feedComment.getId();
        this.commentText = feedComment.getCommentText();
        this.commentLevel = feedComment.getCommentLevel();
        this.commentType = feedComment.getCommentType();
        this.height = feedComment.getHeight();
        this.width = feedComment.getWidth();
        this.imageUrl = feedComment.getImageUrl();
        this.videoUrl = feedComment.getVideoUrl();
        this.lickCount = feedComment.getLickCount();
        this.user = new UserCard(user);
    }

    public UserCard getUser() {
        return user;
    }

    public void setUser(UserCard user) {
        this.user = user;
    }
}
