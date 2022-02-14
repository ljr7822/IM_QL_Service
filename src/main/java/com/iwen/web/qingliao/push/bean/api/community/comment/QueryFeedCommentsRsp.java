package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.card.UserCard;
import com.iwen.web.qingliao.push.bean.db.FeedComment;
import com.iwen.web.qingliao.push.bean.db.User;

/**
 * ��ѯ���ӵ����ۻص�ģ��
 *
 * @Author: iwen����
 * @DateTime: 2021/12/02 20:16
 */
public class QueryFeedCommentsRsp {
    // �������۵�id
    @Expose
    private String commentId;
    // ��������
    @Expose
    private String commentText;
    // ��������
    @Expose
    private int commentType;
    // ��
    @Expose
    private int height;
    // ��
    @Expose
    private int width;
    // ����
    @Expose
    private int commentLevel;
    // ͼƬurl
    @Expose
    private String imageUrl;
    // ��Ƶurl
    @Expose
    private String videoUrl;;
    // ���۵�����
    @Expose
    private int lickCount;

    // ÿһ�����Ӷ�Ӧ���û���Ϣ
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
