package com.iwen.web.qingliao.push.bean.api.account;

import com.google.gson.annotations.Expose;

/**
 * �˳���¼�ص���model
 *
 * @Author: iwen����
 * @DateTime: 2021/05/09 20:29
 */
public class LogoutRspModel {
    //��ǰ��¼���˺�
    @Expose
    private String state;

    public LogoutRspModel(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
