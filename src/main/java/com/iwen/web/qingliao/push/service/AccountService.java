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
        LogUtils.error(TAG, "进入 /account/login 请求");
        if (!LoginModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG, "Parameters Error(参数异常)!");
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
            LogUtils.error(TAG, "登录成功");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 登录异常
            LogUtils.error(TAG, "Account or password error(用户名或密码异常)!");
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
        LogUtils.error(TAG, "进入 /account/register 请求");
        if (!RegisterModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG, "Parameters Error(参数异常)!");
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.findByPhone(model.getAccount().trim());
        if (user != null) {
            // 已经存在账户
            LogUtils.error(TAG, "Already have this account(已经存在该账户)!");
            return ResponseModel.buildHaveAccountError();
        }
        user = UserFactory.findByName(model.getName().trim());
        if (user != null) {
            // 已经存在用户名
            LogUtils.error(TAG, "已经存在该用户名");
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
            LogUtils.info(TAG, "注册成功");
            return ResponseModel.buildOk(accountRspModel);
        } else {
            // 注册异常
            LogUtils.error(TAG, "注册异常");
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
        LogUtils.error(TAG, "进入 /account/logout 请求");
        if (!LogoutModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG, "Parameters Error(参数异常)!");
            return ResponseModel.buildParameterError();
        }
        // 开始退出登录
        User user = UserFactory.logout(model.getAccount());
        if (user != null) {
            if (user.getToken() == null) {
                LogoutRspModel rspModel = new LogoutRspModel("退出登录成功");
                LogUtils.error(TAG, "退出登录成功");
                return ResponseModel.buildOk(rspModel);
            } else {
                // 退出登录异常
                LogUtils.error(TAG, "退出登录异常");
                return ResponseModel.buildLoginError();
            }
        }
        // 退出登录异常
        LogUtils.error(TAG, "退出登录异常");
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
        LogUtils.error(TAG, "进入 /account/changepwd 请求");
        if (!ChangePwdModel.check(model)) {
            // 返回参数异常
            LogUtils.error(TAG, "Parameters Error(参数异常)!");
            return ResponseModel.buildParameterError();
        }
        // 开始修改密码
        User user = UserFactory.changePwd(model);
        if (user != null) {
            // 返回当前账户
            AccountRspModel rspModel = new AccountRspModel(user);
            LogUtils.error(TAG, "修改密码成功");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 修改密码异常
            LogUtils.error(TAG, "修改密码异常!");
            return ResponseModel.buildLoginError();
        }
    }

    /**
     * 修改用户名接口
     *
     * @param userId 用户id
     * @param mName  新用户名
     * @return AccountRspModel
     */
    @PUT
    @Path("/modifyusername")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> modifyUserName(@QueryParam("userId") String userId, @QueryParam("mName") String mName) {
        LogUtils.info(TAG, "in to /account/modifyUserName request>>>>");
        // 先查找这个用户名是否存在，存在就不能修改
        User user = UserFactory.findByName(mName);
        if (user != null) {
            // 存在该用户、不能修改
            AccountRspModel rspModel = new AccountRspModel(user);
            LogUtils.info(TAG, "已经存在该用户名");
            return ResponseModel.buildHaveNameError();
        } else {
            // 不存在，可以修改,通过id进行修改
            // 开始修改密码
            User modify = UserFactory.modifyUserName(userId, mName);
            if (modify != null) {
                // 返回当前账户
                AccountRspModel rspModel = new AccountRspModel(modify);
                LogUtils.info(TAG, "修改用户名成功");
                return ResponseModel.buildOk(rspModel);
            } else {
                // 修改密码异常
                LogUtils.info(TAG, "修改用户名异常!");
                return ResponseModel.buildLoginError();
            }
        }
    }

    /**
     * 修改个性签名接口
     *
     * @param userId 用户id
     * @param mDesc  新个性签名
     * @return AccountRspModel
     */
    @PUT
    @Path("/modifyuserdesc")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> modifyUserDesc(@QueryParam("userId") String userId, @QueryParam("mDesc") String mDesc) {
        LogUtils.info(TAG, "in to /account/modifyUserDesc request>>>>");
        // 开始修改密码
        User modify = UserFactory.modifyUserDesc(userId, mDesc);
        if (modify != null) {
            // 返回当前账户
            AccountRspModel rspModel = new AccountRspModel(modify);
            LogUtils.info(TAG, "Modify user desc successful>>>");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 修改密码异常
            LogUtils.info(TAG, "Modify user desc error>>>");
            return ResponseModel.buildAccountError();
        }
    }

    /**
     * 修改学校接口
     *
     * @param userId 用户id
     * @param mSchool  我的学校
     * @return AccountRspModel
     */
    @PUT
    @Path("/modifyuserschool")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> modifyUserSchool(@QueryParam("userId") String userId, @QueryParam("mSchool") String mSchool) {
        LogUtils.info(TAG, "in to /account/modifyUserSchool request>>>>");
        // 开始修改密码
        User modify = UserFactory.modifyUserSchool(userId, mSchool);
        if (modify != null) {
            // 返回当前账户
            AccountRspModel rspModel = new AccountRspModel(modify);
            LogUtils.info(TAG, "Modify user school successful>>>");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 修改密码异常
            LogUtils.info(TAG, "Modify user school error>>>");
            return ResponseModel.buildAccountError();
        }
    }

    /**
     * 修改性别接口
     *
     * @param userId 用户id
     * @param mSex  我的学校
     * @return AccountRspModel
     */
    @PUT
    @Path("/modifyusersex")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> modifyUserSex(@QueryParam("userId") String userId, @QueryParam("mSex") int mSex) {
        LogUtils.info(TAG, "in to /account/modifyUserSex request>>>>");
        // 开始修改
        User modify = UserFactory.modifyUserSex(userId, mSex);
        if (modify != null) {
            // 返回当前账户
            AccountRspModel rspModel = new AccountRspModel(modify);
            LogUtils.info(TAG, "Modify user sex successful>>>");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 修改异常
            LogUtils.info(TAG, "Modify user sex error>>>");
            return ResponseModel.buildAccountError();
        }
    }

    /**
     * 修改地址接口
     *
     * @param userId 用户id
     * @param mAddress  我的地址
     * @return AccountRspModel
     */
    @PUT
    @Path("/modifyuseraddress")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> modifyUserAddress(@QueryParam("userId") String userId, @QueryParam("mAddress") String mAddress) {
        LogUtils.info(TAG, "in to /account/modifyUserAddress request>>>>");
        // 开始修改
        User modify = UserFactory.modifyUserAddress(userId, mAddress);
        if (modify != null) {
            // 返回当前账户
            AccountRspModel rspModel = new AccountRspModel(modify);
            LogUtils.info(TAG, "Modify user address successful>>>");
            return ResponseModel.buildOk(rspModel);
        } else {
            // 修改异常
            LogUtils.info(TAG, "Modify user address error>>>");
            return ResponseModel.buildAccountError();
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
        LogUtils.info(TAG, "start request------>> /account/bind/{pushId} ");
        if (Strings.isNullOrEmpty(pushId)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }
        // 通过token拿到个人信息
        // User user = UserFactory.findByToken(token);
        User self = getSelf();
        // 进行绑定
        LogUtils.info(TAG, "start bind phone ----->> ");
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
            LogUtils.error(TAG, "--->>> bind error");
            return ResponseModel.buildServiceError();
        }
        // 绑定成功，返回当前账户
        AccountRspModel rspModel = new AccountRspModel(user, true);
        LogUtils.info(TAG, "--->>> bind successful");
        return ResponseModel.buildOk(rspModel);
    }
}
