package com.iwen.web.qingliao.push.bean.api.account;

import com.google.gson.annotations.Expose;

/**
 * 退出登录回调的model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/05/09 20:29
 */
public class LogoutRspModel {
    //当前登录的账号
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
