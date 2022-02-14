package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedComment;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;

import javax.persistence.Entity;

/**
 * ���һ�����ۻص�model
 *
 * @Author: iwen����
 * @DateTime: 2021/12/02 14:37
 */
public class AddCommentRspModel {
    // ���۵�״̬
    @Expose
    private String commentText;

    // ����������
    @Expose
    private int commentCount;

    public AddCommentRspModel(FeedComment feedComment, FeedUgc feedUgc) {
        this.commentText = feedComment.getCommentText();
        this.commentCount = feedUgc.getCommentCount();
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
