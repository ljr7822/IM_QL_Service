package com.iwen.web.qingliao.push.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * 退出登录的请求model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/05/09 20:08
 */
public class LogoutModel {
    @Expose
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    // 数据非空校验
    public static boolean check(LogoutModel model) {
        return model != null && !Strings.isNullOrEmpty(model.account);
    }
}
