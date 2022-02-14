package com.iwen.web.qingliao.push.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * 修改密码需要的
 *
 * @Author: iwen大大怪
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

    // 数据非空校验
    public static boolean check(ChangePwdModel model) {
        return model != null && !Strings.isNullOrEmpty(model.userId) && !Strings.isNullOrEmpty(model.newPwd);
    }
}
