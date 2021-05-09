package com.iwen.web.qingliao.push.service;

import com.google.common.base.Strings;
import com.iwen.web.qingliao.push.bean.api.base.PushModel;
import com.iwen.web.qingliao.push.bean.api.base.ResponseModel;
import com.iwen.web.qingliao.push.bean.api.user.UpdateInfoModel;
import com.iwen.web.qingliao.push.bean.card.UserCard;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.factory.PushFactory;
import com.iwen.web.qingliao.push.factory.UserFactory;
import com.iwen.web.qingliao.push.utils.LogUtils;
import com.iwen.web.qingliao.push.utils.PushDispatcher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: iwen大大怪
 * @DateTime: 12-4 004 17:50
 */
@Path("/user")
public class UserService extends BaseService {
    private static final String TAG = "user";
    // 用户信息修改接口,返回自己的个人信息
    // 不需要写@Path，就是当前目录
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        LogUtils.info(TAG,"开始进入 【/user】 请求");
        if (!UpdateInfoModel.check(model)) {
            LogUtils.error(TAG,"【user】错误：检查用户修改模块内容错误");
            return ResponseModel.buildParameterError();
        }
        //拿到自己的个人信息
        User self = getSelf();
        // 更新用户信息
        self = model.updateToUser(self);
        UserFactory.update(self);
        // 构建自己的用户信息
        UserCard userCard = new UserCard(self, true);
        // 返回
        LogUtils.info(TAG,"【user】成功：用户信息修改成功");
        return ResponseModel.buildOk(userCard);
    }

    // 拉取联系人的接口
    @GET
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> contact() {
        LogUtils.info(TAG,"开始进入 【/user/contact】 请求");
        User self = getSelf();

        /*
        // 测试推送消息
        PushModel model = new PushModel();
        model.add(new PushModel.Entity(0,"Hello!!!!"));
        PushDispatcher dispatcher = new PushDispatcher();
        dispatcher.add(self,model);
        dispatcher.submit();
         */

        // 拿到我的联系人
        List<User> users = UserFactory.contacts(self);
        // 转换为UserCard
        List<UserCard> userCards = users.stream()
                // map操作，相当于转置操作，User->UserCard
                .map(user -> new UserCard(user, true))
                .collect(Collectors.toList());
        // 返回
        LogUtils.info(TAG,"【user】成功：拉取联系人成功");
        return ResponseModel.buildOk(userCards);
    }

    // 关注某个人的接口
    @PUT // 修改使用put
    @Path("/follow/{followId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> follow(@PathParam("followId") String followId) {
        LogUtils.info(TAG,"开始进入 【/user/follow/{followId}】 请求");
        User self = getSelf();
        // 不能关注自己
        if (self.getId().equalsIgnoreCase(followId) || Strings.isNullOrEmpty(followId)) {
            // 返回参数异常
            LogUtils.error(TAG,"【user】错误：不能关注自己");
            return ResponseModel.buildParameterError();
        }
        // 找到我要关注的人
        User followUser = UserFactory.findById(followId);
        if (followUser == null) {
            // 未找到人
            LogUtils.error(TAG,"【user】错误：未找到关注人");
            return ResponseModel.buildNotFoundUserError(null);
        }
        // 开始关注操作,备注默认没有·
        followUser = UserFactory.follow(self, followUser, null);
        if (followUser == null) {
            // 关注失败，返回服务器异常
            LogUtils.error(TAG,"【user】错误：关注失败");
            return ResponseModel.buildServiceError();
        }
        // 通知我关注的人我关注了他
        // 给他发送一个我的信息过去
        PushFactory.pushFollow(followUser, new UserCard(self));

        // 返回关注的人的信息
        LogUtils.info(TAG,"【user】成功：关注成功");
        return ResponseModel.buildOk(new UserCard(followUser, true));
    }

    // 拉取某个人的信息接口
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> getUser(@PathParam("id") String id) {
        if (Strings.isNullOrEmpty(id)) {
            // 参数异常
            return ResponseModel.buildParameterError();
        }
        User self = getSelf();
        if (self.getId().equalsIgnoreCase(id)) {
            // 返回自己
            return ResponseModel.buildOk(new UserCard(self, true));
        }

        User user = UserFactory.findById(id);
        if (user == null) {
            // 没找到该联系人，返回没找到该用户异常
            return ResponseModel.buildNotFoundUserError(null);
        }
        // 如果我们之间有关注的记录，则我已关注需要查询信息的用户
        boolean isFollow = UserFactory.getUserFollow(self, user) != null;
        return ResponseModel.buildOk(new UserCard(user, isFollow));
    }

    // 搜索人的接口
    // 为了简化分页，只返回20条数据
    @GET // 不涉及数据更改
    @Path("/search/{name:(.*)?}") // name为任意字符，允许为空
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> search(@DefaultValue("") @PathParam("name") String name) {
        User self = getSelf();
        // 查询用户
        List<User> searchUsers = UserFactory.search(name);
        // 查询的人封装为userCard,判断这些人是否有我已经关注的人,如果有就应该设置好状态
        // 拿出我的联系人
        List<User> contacts = UserFactory.contacts(self);
        // 把user装换成userCard
        List<UserCard> userCards = searchUsers.stream()
                .map(user -> {
                    // 判断这个人是否是我自己或者是否是在我的联系人中
                    boolean isFollow = user.getId().equalsIgnoreCase(self.getId())
                            // 进行联系人的任意匹配，匹配其中的Id字段
                            || contacts.stream().anyMatch(contactUser -> contactUser.getId().equalsIgnoreCase(user.getId()));
                    return new UserCard(user, isFollow);
                }).collect(Collectors.toList());
        // 返回
        return ResponseModel.buildOk(userCards);
    }
}
