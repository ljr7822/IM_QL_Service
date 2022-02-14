package com.iwen.web.qingliao.push.service;

import com.iwen.web.qingliao.push.bean.api.base.ResponseModel;
import com.iwen.web.qingliao.push.bean.card.GroupCard;
import com.iwen.web.qingliao.push.bean.card.MessageCard;
import com.iwen.web.qingliao.push.bean.db.Group;
import com.iwen.web.qingliao.push.bean.db.GroupMember;
import com.iwen.web.qingliao.push.bean.db.Message;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.factory.GroupFactory;
import com.iwen.web.qingliao.push.factory.MessageFactory;
import com.iwen.web.qingliao.push.bean.api.message.MessageCreateModel;
import com.iwen.web.qingliao.push.factory.PushFactory;
import com.iwen.web.qingliao.push.factory.UserFactory;
import com.iwen.web.qingliao.push.utils.LogUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 消息发送的入口
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/02/23 22:18
 */
@Path("/msg")
public class MessageService extends BaseService {
    private static final String TAG = "msg";
    /**
     * 发送一条消息到服务器
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<MessageCard> pushMessage(MessageCreateModel model) {
        LogUtils.info(TAG,"开始进入 【/msg】 请求");
        if (!MessageCreateModel.check(model)) {
            LogUtils.error(TAG,"【msg】错误：检查消息创建模块内容错误");
            return ResponseModel.buildParameterError();
        }

        User self = getSelf();

        // 查询是否已经在数据库中有了
        Message message = MessageFactory.findById(model.getId());
        if (message != null) {
            // 正常返回
            LogUtils.info(TAG,"【msg】成功：查询是否已经在数据库中有了");
            return ResponseModel.buildOk(new MessageCard(message));
        }

        if (model.getReceiverType() == Message.RECEIVER_TYPE_GROUP) {
            return pushToGroup(self, model);
        } else {
            return pushToUser(self, model);
        }
    }

    /**
     * 发送给人
     *
     * @param sender 发送者
     * @param model  发送的model
     * @return
     */
    private ResponseModel<MessageCard> pushToUser(User sender, MessageCreateModel model) {
        LogUtils.info(TAG,"【msg】进入：消息发送给人的方法");
        User receiver = UserFactory.findById(model.getReceiverId());
        if (receiver == null) {
            // 没有找到接收者
            LogUtils.error(TAG,"【msg】错误：没有找到接收者");
            return ResponseModel.buildNotFoundUserError("未找到给用户！");
        }

        if (receiver.getId().equalsIgnoreCase(sender.getId())) {
            // 发送者接收者是同一个人就返回创建消息失败
            LogUtils.error(TAG,"【msg】错误：发送者接收者是同一个人");
            return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }

        // 存储数据库
        Message message = MessageFactory.add(sender, receiver, model);

        LogUtils.info(TAG,"【msg】成功：发送消息成功");
        return buildAndPushResponse(sender, message);
    }

    /**
     * 发送给群
     *
     * @param sender 发送者
     * @param model  发送的model
     * @return
     */
    private ResponseModel<MessageCard> pushToGroup(User sender, MessageCreateModel model) {
        LogUtils.info(TAG,"【msg】进入：消息发送给群的方法");
        // 找群是有权限性质的找
        Group group = GroupFactory.findById(sender, model.getReceiverId());
        if (group == null) {
            // 没有找到接收者群，有可能是你不是群的成员
            LogUtils.error(TAG,"【msg】错误：没有找到接收群");
            return ResponseModel.buildNotFoundUserError("未找到该群！");
        }

        // 添加到数据库
        Message message = MessageFactory.add(sender, group, model);

        // 走通用的推送逻辑
        LogUtils.info(TAG,"【msg】成功：发送消息成功");
        return buildAndPushResponse(sender, message);
    }

    /**
     * 推送并构建一个返回信息
     *
     * @param sender 发送者
     * @param message 消息
     * @return
     */
    private ResponseModel<MessageCard> buildAndPushResponse(User sender, Message message) {
        LogUtils.info(TAG,"【msg】进入：推送并构建一个返回信息 的方法");
        if (message == null) {
            // 存储数据库失败
            LogUtils.error(TAG,"【msg】错误：存储数据库失败");
            return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }

        // 进行推送
        PushFactory.pushNewMessage(sender, message);

        // 返回
        LogUtils.error(TAG,"【msg】成功：推送并构建一个返回信息成功");
        return ResponseModel.buildOk(new MessageCard(message));
    }

    /**
     * 取出我和某个好友的历史消息列表
     *
     * @param receiverId 好友id or 我的id
     * @return List<MessageCard>
     */
    @GET
    @Path("/list/{receiverId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<MessageCard>> getMessage(@PathParam("receiverId") String receiverId){
        LogUtils.info(TAG,"开始进入 【/list/{receiverId}】 请求");
        User self = getSelf();
        Set<Message> messages= MessageFactory.findHistoryMsg(receiverId,self.getId());
        if (messages != null && messages.size() > 0) {
            LogUtils.info(TAG,"【/list/{receiverId}】成功：查到"+messages.size()+"条历史记录");
            List<MessageCard> messageCards = messages.stream().map(MessageCard::new).collect(Collectors.toList());
            return ResponseModel.buildOk(messageCards);
        }
        // 服务器异常
        LogUtils.error(TAG,"【/list/{receiverId}】错误：没有查到历史记录");
        return ResponseModel.buildServiceError();
    }
}
