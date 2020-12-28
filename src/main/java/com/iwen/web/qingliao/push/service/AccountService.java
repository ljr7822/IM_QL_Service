package com.iwen.web.qingliao.push.service;

import com.google.common.base.Strings;
import com.iwen.web.qingliao.push.bean.api.account.AccountRspModel;
import com.iwen.web.qingliao.push.bean.api.account.LoginModel;
import com.iwen.web.qingliao.push.bean.api.account.RegisterModel;
import com.iwen.web.qingliao.push.bean.api.base.ResponseModel;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.stream.StreamSupport;

/**
 * 账户相关服务入口
 * 实际路径：127.0.0.1/api/account/login
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/11/11 12:30
 */
@Path("/account")
public class AccountService extends BaseService {

    /**
     * 登录接口
     *
     * @param model 登录所需要的
     * @return AccountRspModel
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        if (!LoginModel.check(model)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {
            // 如果有携带pushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                // 绑定
                return bind(user, model.getPushId());
            }
            // 返回当前账户
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            // 登录异常
            return ResponseModel.buildLoginError();
        }
    }

    /**
     * 注册入口
     *
     * @param model 注册需要的
     * @return 一个用户
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model) {
        if (!RegisterModel.check(model)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            // 已经存在账户
            return ResponseModel.buildHaveAccountError();
        }
        user = UserFactory.findByName(model.getName().trim());
        if (user != null) {
            // 已经存在用户名
            return ResponseModel.buildHaveAccountError();
        }
        // 开始注册逻辑
        user = UserFactory.register(model.getAccount().trim(), model.getPassword(), model.getName().trim());
        if (user != null) {
            // 如果有携带pushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                // 绑定
                return bind(user, model.getPushId());
            }
            // 注册成功
            AccountRspModel accountRspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(accountRspModel);
        } else {
            // 注册异常
            return ResponseModel.buildRegisterError();
        }
    }

    /**
     * 绑定设备id
     *
     * @param pushId pushid
     * @return AccountRspModel
     */
    @POST
    @Path("/bind/{pushId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 从请求头中获取token字段
    // pushId从url中获取
    public ResponseModel<AccountRspModel> bind(@PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }
        // 通过token拿到个人信息
        // User user = UserFactory.findByToken(token);
        User self = getSelf();
        // 进行绑定
        return bind(self, pushId);
    }

    /**
     * 绑定操作
     *
     * @param self   自己
     * @param pushId pushId
     * @return user
     */
    private ResponseModel<AccountRspModel> bind(User self, String pushId) {
        // 进行设备绑定
        User user = UserFactory.bindPushId(self, pushId);
        if (user == null) {
            // 绑定失败
            return ResponseModel.buildServiceError();
        }
        // 绑定成功，返回当前账户
        AccountRspModel rspModel = new AccountRspModel(user, true);
        return ResponseModel.buildOk(rspModel);
    }
}
