package com.iwen.web.qingliao.push.bean.api.community.ugc;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;

/**
 * 增加一条帖子的评论数量回调model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/12/04 16:45
 */
public class AddFeedCommentCountRspModel {
    // 帖子id
    @Expose
    private String itemId;

    // 评论的数量
    @Expose
    private int commentCount;

    public AddFeedCommentCountRspModel(FeedUgc feedUgc) {
        this.itemId = feedUgc.getItemId();
        this.commentCount = feedUgc.getCommentCount();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
