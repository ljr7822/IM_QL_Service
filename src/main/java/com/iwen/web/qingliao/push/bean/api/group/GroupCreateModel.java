package com.iwen.web.qingliao.push.bean.api.group;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

import java.util.HashSet;
import java.util.Set;

/**
 * 群创建的Model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/03/04 10:49
 */
public class GroupCreateModel {
    @Expose
    private String name;
    @Expose
    private String desc;
    @Expose
    private String picture;
    @Expose
    private Set<String> users = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public static boolean check(GroupCreateModel model) {
        return !(Strings.isNullOrEmpty(model.name)
                || Strings.isNullOrEmpty(model.desc)
                || Strings.isNullOrEmpty(model.picture)
                || model.users == null
                || model.users.size() == 0);
    }
}
