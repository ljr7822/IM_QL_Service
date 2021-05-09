package com.iwen.web.qingliao.push.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * �˳���¼������model
 *
 * @Author: iwen����
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

    // ���ݷǿ�У��
    public static boolean check(LogoutModel model) {
        return model != null && !Strings.isNullOrEmpty(model.account);
    }
}
