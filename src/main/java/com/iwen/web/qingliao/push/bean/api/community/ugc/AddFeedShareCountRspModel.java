package com.iwen.web.qingliao.push.bean.api.community.ugc;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;

/**
 * ����һ�����ӵķ��������ص�model
 *
 * @Author: iwen����
 * @DateTime: 2021/12/04 16:45
 */
public class AddFeedShareCountRspModel {
    // ����id
    @Expose
    private String itemId;

    // ���������
    @Expose
    private int shareCount;

    public AddFeedShareCountRspModel(FeedUgc feedUgc) {
        this.itemId = feedUgc.getItemId();
        this.shareCount = feedUgc.getShareCount();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }
}
