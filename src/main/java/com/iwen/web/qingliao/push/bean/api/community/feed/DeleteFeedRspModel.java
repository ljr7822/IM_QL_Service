package com.iwen.web.qingliao.push.bean.api.community.feed;

import com.google.gson.annotations.Expose;

/**
 * ɾ��һ�����ӵĻص�model
 *
 * @Author: iwen����
 * @DateTime: 2021/11/29 14:56
 */
public class DeleteFeedRspModel {
    // ɾ��״̬
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
