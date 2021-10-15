package com.iwen.web.qingliao.push.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * �޸�������Ҫ��
 *
 * @Author: iwen����
 * @DateTime: 2021/05/10 1:24
 */
public class ChangePwdModel {
    @Expose
    private String userId;

    @Expose
    private String oldPwd;

    @Expose
    private String newPwd;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    // ���ݷǿ�У��
    public static boolean check(ChangePwdModel model) {
        return model != null && !Strings.isNullOrEmpty(model.userId) && !Strings.isNullOrEmpty(model.oldPwd) && !Strings.isNullOrEmpty(model.newPwd);
    }
}
