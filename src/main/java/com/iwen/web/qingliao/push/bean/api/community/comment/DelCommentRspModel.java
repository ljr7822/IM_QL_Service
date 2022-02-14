package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;

/**
 * 删除一条评论的回调model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/12/02 19:27
 */
public class DelCommentRspModel {
    // 状态
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
