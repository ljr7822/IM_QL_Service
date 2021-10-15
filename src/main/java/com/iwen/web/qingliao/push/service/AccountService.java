package com.iwen.web.qingliao.push.service;

import com.google.common.base.Strings;
import com.iwen.web.qingliao.push.bean.api.account.*;
import com.iwen.web.qingliao.push.bean.api.base.ResponseModel;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.factory.UserFactory;
import com.iwen.web.qingliao.push.utils.LogUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 账户相关服务入口
 * 实际路径：127.0.0.1/api/account/login
 *
 * @Author: iwen大大怪
 * @DateTime: 2020/11/11 12:30
 */
@Path("/account")
public class AccountService extends BaseService {
    private static final String TAG = "account";
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
        LogUtils.error(TAG,"进入 /account/login 请求");
        if (!LoginModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG,"Parameters Error(参数异常)!");
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
            LogUtils.error(TAG,"登录成功");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 登录异常
            LogUtils.error(TAG,"Account or password error(用户名或密码异常)!");
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
        LogUtils.error(TAG,"进入 /account/register 请求");
        if (!RegisterModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG,"Parameters Error(参数异常)!");
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            // 已经存在账户
            LogUtils.error(TAG,"Already have this account(已经存在该账户)!");
            return ResponseModel.buildHaveAccountError();
        }
        user = UserFactory.findByName(model.getName().trim());
        if (user != null) {
            // 已经存在用户名
            LogUtils.error(TAG,"已经存在该用户名");
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
            LogUtils.error(TAG,"注册成功");
            return ResponseModel.buildOk(accountRspModel);
        } else {
            // 注册异常
            LogUtils.error(TAG,"注册异常");
            return ResponseModel.buildRegisterError();
        }
    }

    /**
     * 退出登录接口
     *
     * @param model 退出登录所需要的
     * @return LogoutRspModel
     */
    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<LogoutRspModel> logout(LogoutModel model) {
        LogUtils.error(TAG,"进入 /account/logout 请求");
        if (!LogoutModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG,"Parameters Error(参数异常)!");
            return ResponseModel.buildParameterError();
        }
        // 开始退出登录
        User user = UserFactory.logout(model.getAccount());
        if (user!=null){
            if (user.getToken()==null){
                LogoutRspModel rspModel = new LogoutRspModel("退出登录成功");
                LogUtils.error(TAG,"退出登录成功");
                return ResponseModel.buildOk(rspModel);
            }else {
                // 退出登录异常
                LogUtils.error(TAG,"退出登录异常");
                return ResponseModel.buildLoginError();
            }
        }
        // 退出登录异常
        LogUtils.error(TAG,"退出登录异常");
        return ResponseModel.buildLoginError();
    }

    /**
     * 修改密码接口
     *
     * @param model 退出登录所需要的
     * @return AccountRspModel
     */
    @POST
    @Path("/changepwd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> changePwd(ChangePwdModel model) {
        LogUtils.error(TAG,"进入 /account/changepwd 请求");
        if (!ChangePwdModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG,"Parameters Error(参数异常)!");
            return ResponseModel.buildParameterError();
        }
        // 开始修改密码
        User user = UserFactory.changePwd(model);
        if (user != null) {
            // 返回当前账户
            AccountRspModel rspModel = new AccountRspModel(user);
            LogUtils.error(TAG,"修改密码成功");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 修改密码异常
            LogUtils.error(TAG,"修改密码异常!");
            return ResponseModel.buildLoginError();
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
        LogUtils.error(TAG,"进入 /account/bind/{pushId} 请求");
        if (Strings.isNullOrEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }
        // 通过token拿到个人信息
        // User user = UserFactory.findByToken(token);
        User self = getSelf();
        // 进行绑定
        LogUtils.error(TAG,"开始进行设备绑定");
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
            LogUtils.error(TAG,"绑定失败");
            return ResponseModel.buildServiceError();
        }
        // 绑定成功，返回当前账户
        AccountRspModel rspModel = new AccountRspModel(user, true);
        LogUtils.error(TAG,"绑定成功");
        return ResponseModel.buildOk(rspModel);
    }
}
