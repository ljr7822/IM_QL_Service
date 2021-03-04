package com.iwen.web.qingliao.push.bean.api.group;

import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Set;

/**
 * 群聊添加成员的model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/03/04 11:01
 */
public class GroupMemberAddModel {
    @Expose
    private Set<String> users = new HashSet<>();

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public static boolean check(GroupMemberAddModel model) {
        return !(model.users == null
                || model.users.size() == 0);
    }
}
