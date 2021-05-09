package com.iwen.web.qingliao.push.service;

import com.google.common.base.Strings;
import com.iwen.web.qingliao.push.bean.api.base.ResponseModel;
import com.iwen.web.qingliao.push.bean.api.group.GroupCreateModel;
import com.iwen.web.qingliao.push.bean.api.group.GroupMemberAddModel;
import com.iwen.web.qingliao.push.bean.api.group.GroupMemberUpdateModel;
import com.iwen.web.qingliao.push.bean.card.ApplyCard;
import com.iwen.web.qingliao.push.bean.card.GroupCard;
import com.iwen.web.qingliao.push.bean.card.GroupMemberCard;
import com.iwen.web.qingliao.push.bean.db.Group;
import com.iwen.web.qingliao.push.bean.db.GroupMember;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.factory.GroupFactory;
import com.iwen.web.qingliao.push.factory.PushFactory;
import com.iwen.web.qingliao.push.factory.UserFactory;
import com.iwen.web.qingliao.push.provider.LocalDateTimeConverter;
import com.iwen.web.qingliao.push.utils.LogUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 群组接口的入口方法
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/03/04 10:44
 */
@Path("/group")
public class GroupService extends BaseService {
    private static final String TAG = "group";

    /**
     * 创建群
     *
     * @param model 基本参数
     * @return 群信息
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<GroupCard> create(GroupCreateModel model) {
        LogUtils.info(TAG,"开始进入 【/group】 请求");
        // 先检查创建群的model
        if (!GroupCreateModel.check(model)) {
            LogUtils.error(TAG,"【group】错误：创建群的参数错误");
            return ResponseModel.buildParameterError();
        }

        // 获取创建者
        User creator = getSelf();
        // 创建者并不在列表中
        model.getUsers().remove(creator.getId());
        if (model.getUsers().size() == 0) {
            LogUtils.error(TAG,"【group】错误：创建者为空");
            return ResponseModel.buildParameterError();
        }
        // 检查是否已有相同名字的群聊
        if (GroupFactory.findByName(model.getName()) != null) {
            LogUtils.error(TAG,"【group】错误：存在相同群聊名称");
            return ResponseModel.buildHaveNameError();
        }

        // 拿到所有用户
        List<User> userList = new ArrayList<>();
        for (String s : model.getUsers()) {
            User user = UserFactory.findById(s);
            if (user == null) {
                continue;
            }
            userList.add(user);
        }
        // 该群没有一个成员
        if (userList.size() == 0) {
            LogUtils.error(TAG,"【group】错误：该群没有一个成员");
            return ResponseModel.buildParameterError();
        }

        // 创建群
        Group group = GroupFactory.create(creator, model, userList);
        if (group == null) {
            // 服务器异常
            LogUtils.error(TAG,"【group】错误：创建群错误");
            return ResponseModel.buildServiceError();
        }

        // 拿到管理员，即自己的信息
        GroupMember creatorMember = GroupFactory.getMember(creator.getId(), group.getId());
        if (creatorMember == null) {
            // 服务器异常
            LogUtils.error(TAG,"【group】错误：没有拿到群管理员");
            return ResponseModel.buildServiceError();
        }
        // 拿到群成员，给所有的群成员发送信息：已经被添加到了XX群中
        Set<GroupMember> members = GroupFactory.getMembers(group);
        if (members == null) {
            // 服务器异常
            LogUtils.error(TAG,"【group】错误：没有拿到群成员");
            return ResponseModel.buildServiceError();
        }
        members = members.stream()
                .filter(groupMember -> !groupMember.getId().equalsIgnoreCase(creatorMember.getId()))
                .collect(Collectors.toSet());
        // 开始发起推送
        PushFactory.pushJoinGroup(members);
        LogUtils.info(TAG,"【group】成功：创建群成功");
        return ResponseModel.buildOk(new GroupCard(creatorMember));
    }

    /**
     * 查找群，不传参即代表搜索最近的所有群
     *
     * @param name 搜索的名字参数
     * @return 群信息列表
     */
    @GET
    @Path("/search/{name:(.*)?}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<GroupCard>> search(@PathParam("name") @DefaultValue("") String name) {
        LogUtils.info(TAG,"开始进入 【/group/search/{name}】 请求");
        // 拿到自己的信息
        User self = getSelf();
        // 拿到群列表
        List<Group> groups = GroupFactory.search(name);
        if (groups != null && groups.size() > 0) {
            List<GroupCard> groupCards = groups.stream()
                    .map(group -> {
                        GroupMember member = GroupFactory.getMember(self.getId(), group.getId());
                        return new GroupCard(group, member);
                    }).collect(Collectors.toList());
            LogUtils.info(TAG,"【group】成功：查找群列表成功");
            return ResponseModel.buildOk(groupCards);
        }
        LogUtils.info(TAG,"【group】成功：查找群列表成功");
        return ResponseModel.buildOk();
    }

    /**
     * 拉取自己当前的群列表
     *
     * @param dateStr 时间字段，不传则代表返回当前全部群，传入时间则返回该时间之后加入的群
     * @return 群列表
     */
    @GET
    @Path("/list/{date:(.*)?}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<GroupCard>> list(@DefaultValue("") @PathParam("date") String dateStr) {
        LogUtils.error(TAG,"开始进入 【/group/list/{date}】 请求");
        User self = getSelf();
        // 拿时间
        LocalDateTime dateTime = null;
        if (!Strings.isNullOrEmpty(dateStr)) {
            try {
                dateTime = LocalDateTime.parse(dateStr, LocalDateTimeConverter.FORMATTER);
            } catch (Exception e) {
                dateTime = null;
            }
        }
        Set<GroupMember> members = GroupFactory.getMembers(self);
        if (members == null || members.size() == 0) {
            LogUtils.info(TAG,"【group】成功：获取members成功");
            return ResponseModel.buildOk();
        }
        final LocalDateTime localDateTime = dateTime;
        List<GroupCard> groupCards = members.stream()
                .filter(groupMember -> localDateTime == null // 时间为null就不做限制
                        || groupMember.getUpdateAt().isAfter(localDateTime))// 时间不为null需要在这个时间之后
                .map(GroupCard::new).collect(Collectors.toList());
        LogUtils.info(TAG,"【group】成功：获取groupCards成功");
        return ResponseModel.buildOk(groupCards);
    }

    /**
     * 申请加入一个群，此时会创建一个加入的申请，并写入表，然后给管理员发送消息
     * 管理员同意，即调用添加成员的接口把该成员添加进去
     *
     * @param groupId 群id
     * @return 申请的信息
     */
    @POST
    @Path("/applyJoin/{groupId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<ApplyCard> join(@PathParam("groupId") String groupId) {
        return null;
    }

    /**
     * 获取一个群的信息,你必须是群的成员
     *
     * @param groupId 群的id
     * @return 群的信息card
     */
    @GET
    @Path("/{groupId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<GroupCard> getGroup(@PathParam("groupId") String groupId) {
        LogUtils.info(TAG,"进入 【/group/{groupId}】 请求");
        if (Strings.isNullOrEmpty(groupId)) {
            LogUtils.error(TAG,"【group】错误：没有群id，参数错误");
            return ResponseModel.buildParameterError();
        }
        User self = getSelf();
        GroupMember member = GroupFactory.getMember(self.getId(), groupId);
        if (member == null) {
            LogUtils.error(TAG,"【group】错误：没有加入这个群");
            return ResponseModel.buildNotFoundGroupError(null);
        }
        LogUtils.error(TAG,"【group】成功：拉取群成功");
        return ResponseModel.buildOk(new GroupCard(member));
    }

    /**
     * 拉取一个群的所有群成员，必须是群成员之一才行
     *
     * @param groupId 群id
     * @return 成员列表
     */
    @GET
    @Path("/{groupId}/members")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<GroupMemberCard>> members(@PathParam("groupId") String groupId) {
        LogUtils.info(TAG,"进入 【/group/{groupId}/members】 请求");
        User self = getSelf();

        // 没有这个群
        Group group = GroupFactory.findById(groupId);
        if (group == null) {
            LogUtils.error(TAG,"【group】错误：没有该群");
            return ResponseModel.buildNotFoundGroupError(null);
        }
        // 检查权限
        GroupMember selfMember = GroupFactory.getMember(self.getId(), groupId);
        if (selfMember == null){
            LogUtils.error(TAG,"【group】错误：检查权限参数错误");
            return ResponseModel.buildNoPermissionError();
    }
        // 所有的成员,必须是群成员之一
        Set<GroupMember> members = GroupFactory.getMembers(group);
        if (members == null) {
            LogUtils.error(TAG,"【group】错误：所有的成员,必须是群成员之一");
            return ResponseModel.buildServiceError();
        }
        // 返回
        List<GroupMemberCard> memberCards = members
                .stream()
                .map(GroupMemberCard::new)
                .collect(Collectors.toList());

        LogUtils.info(TAG,"【group】成功：拉取一个群的所有群成员");
        return ResponseModel.buildOk(memberCards);
    }

    /**
     * 给群添加成员，必须为群的管理者之一才行
     *
     * @param groupId 群id
     * @param model   添加成员的model
     * @return 群的成员列表
     */
    @POST
    @Path("/{groupId}/member")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<GroupMemberCard>> memberAdd(@PathParam("groupId") String groupId, GroupMemberAddModel model) {
        LogUtils.info(TAG,"进入 【/group/{groupId}/member】 请求");
        if (Strings.isNullOrEmpty(groupId) || !GroupMemberAddModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        // 拿到我的信息
        User self = getSelf();

        // 移除我之后再进行判断数量
        model.getUsers().remove(self.getId());
        if (model.getUsers().size() == 0) {
            LogUtils.error(TAG,"【group】错误：群成员数量错误");
            return ResponseModel.buildParameterError();
        }
        // 没有这个群
        Group group = GroupFactory.findById(groupId);
        if (group == null) {
            LogUtils.error(TAG,"【group】错误：没有这个群");
            return ResponseModel.buildNotFoundGroupError(null);
        }
        // 我必须是成员, 同时是管理员及其以上级别
        GroupMember selfMember = GroupFactory.getMember(self.getId(), groupId);
        if (selfMember == null || selfMember.getPermissionType() == GroupMember.PERMISSION_TYPE_NONE) {
            LogUtils.error(TAG,"【group】错误：必须是成员, 同时是管理员及其以上级别");
            return ResponseModel.buildNoPermissionError();
        }

        // 已有的成员
        Set<GroupMember> oldMembers = GroupFactory.getMembers(group);
        Set<String> oldMemberUserIds = oldMembers.stream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toSet());


        List<User> insertUsers = new ArrayList<>();
        for (String s : model.getUsers()) {
            // 找人
            User user = UserFactory.findById(s);
            if (user == null) {
                continue;
            }
            // 已经在群里了
            if(oldMemberUserIds.contains(user.getId())) {
                continue;
            }
            insertUsers.add(user);
        }
        // 没有一个新增的成员
        if (insertUsers.size() == 0) {
            LogUtils.error(TAG,"【group】错误：没有一个新增的成员");
            return ResponseModel.buildParameterError();
        }

        // 进行添加操作
        Set<GroupMember> insertMembers =  GroupFactory.addMembers(group, insertUsers);
        if(insertMembers==null) {
            LogUtils.error(TAG,"【group】错误：没有一个新增的成员");
            return ResponseModel.buildServiceError();
        }

        // 转换
        List<GroupMemberCard> insertCards = insertMembers.stream()
                .map(GroupMemberCard::new)
                .collect(Collectors.toList());

        // 通知，两部曲
        // 1.通知新增的成员，你被加入了XXX群
        PushFactory.pushJoinGroup(insertMembers);

        // 2.通知群中老的成员，有XXX，XXX加入群
        PushFactory.pushGroupMemberAdd(oldMembers, insertCards);
        LogUtils.info(TAG,"【group】成功：新增成员成功");
        return ResponseModel.buildOk(insertCards);
    }

    /**
     * 更改成员信息，请求的人要么是管理员，要么是成员本人
     *
     * @param memberId 成员id，可以查询对应的的群，和人
     * @param model    修改的model
     * @return 当前成员的信息
     */
    @PUT
    @Path("/member/{memberId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<GroupMemberCard> modifyMember(@PathParam("memberId") String memberId, GroupMemberUpdateModel model) {
        return null;
    }
}
