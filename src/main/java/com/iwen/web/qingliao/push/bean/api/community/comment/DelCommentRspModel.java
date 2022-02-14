package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;

/**
 * ɾ��һ�����۵Ļص�model
 *
 * @Author: iwen����
 * @DateTime: 2021/12/02 19:27
 */
public class DelCommentRspModel {
    // ״̬
    @Expose
    private int commentCount;

    public DelCommentRspModel(FeedUgc feedUgc){
        this.commentCount = feedUgc.getCommentCount();
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
