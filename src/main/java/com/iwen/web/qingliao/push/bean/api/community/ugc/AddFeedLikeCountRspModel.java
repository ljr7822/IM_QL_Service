package com.iwen.web.qingliao.push.bean.api.community.ugc;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;

/**
 * 增加一条帖子的喜欢数量回调model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/12/04 16:45
 */
public class AddFeedLikeCountRspModel {
    // 帖子id
    @Expose
    private String itemId;

    // 喜欢的数量
    @Expose
    private int lickCount;

    public AddFeedLikeCountRspModel(FeedUgc feedUgc) {
        this.itemId = feedUgc.getItemId();
        this.lickCount = feedUgc.getLikeCount();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getLickCount() {
        return lickCount;
    }

    public void setLickCount(int lickCount) {
        this.lickCount = lickCount;
    }
}
