package com.iwen.web.qingliao.push.bean.api.community.ugc;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;

/**
 * ����һ�����ӵ�ϲ�������ص�model
 *
 * @Author: iwen����
 * @DateTime: 2021/12/04 16:45
 */
public class AddFeedLikeCountRspModel {
    // ����id
    @Expose
    private String itemId;

    // ϲ��������
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
