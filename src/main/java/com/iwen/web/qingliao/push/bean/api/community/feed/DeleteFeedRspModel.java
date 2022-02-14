package com.iwen.web.qingliao.push.bean.api.community.feed;

import com.google.gson.annotations.Expose;

/**
 * 删除一条帖子的回调model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/29 14:56
 */
public class DeleteFeedRspModel {
    // 删除状态
    @Expose
    private String state;

    public DeleteFeedRspModel(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
