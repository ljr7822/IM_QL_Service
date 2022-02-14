package com.iwen.web.qingliao.push.factory;

import com.gexin.rp.sdk.base.impl.ListMessage;
import com.google.common.base.Strings;
import com.iwen.web.qingliao.push.bean.db.Group;
import com.iwen.web.qingliao.push.bean.db.GroupMember;
import com.iwen.web.qingliao.push.bean.db.Message;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.bean.api.message.MessageCreateModel;
import com.iwen.web.qingliao.push.utils.Hib;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 消息数据存储的类
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/02/23 22:19
 */
public class MessageFactory {
    // 查询某一个消息
    public static Message findById(String id) {
        return Hib.query(session -> session.get(Message.class, id));
    }

    /**
     * 查询和某个人的历史消息
     *
     * @param receiverId 好友的id
     * @return List<Message>
     */
    public static Set<Message> findHistoryMsg(String receiverId, String senderId) {
        return Hib.query(session -> {
            @SuppressWarnings("unchecked")
            List<Message> messages = session.createQuery(
                    "from Message where (receiverId=:receiverId and senderId=:senderId) or (receiverId=:senderId and senderId=:receiverId)")
                    .setParameter("receiverId", receiverId)
                    .setParameter("senderId", senderId)
                    .list();
            return new HashSet<>(messages);
        });
    }

    // 添加一条普通消息
    public static Message add(User sender, User receiver, MessageCreateModel model) {
        Message message = new Message(sender, receiver, model);
        return save(message);
    }

    // 添加一条群消息
    public static Message add(User sender, Group group, MessageCreateModel model) {
        Message message = new Message(sender, group, model);
        return save(message);
    }

    private static Message save(Message message) {
        return Hib.query(session -> {
            session.save(message);

            // 写入到数据库
            session.flush();

            // 紧接着从数据库中查询出来
            session.refresh(message);
            return message;
        });
    }
}
