package com.iwen.web.qingliao.push.bean.db;


import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户的model，对应的数据库
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/02/04 15:14
 */
@Entity
@Table(name = "TB_USER")
public class User implements Principal {
    /**
     * 用户id
     */
    @Id
    @PrimaryKeyJoinColumn  // 这是一个主键
    @GeneratedValue(generator = "uuid") // 主键生成的存储类型
    @GenericGenerator(name = "uuid", strategy = "uuid2") // 把uuid的生成器定义为uuid2
    @Column(updatable = false, nullable = false) // 不允许更改，不允许为空
    private String id;

    /**
     * 用户名 必须唯一
     */
    @Column(nullable = false, length = 128, unique = true)
    private String name;

    /**
     * 电话 必须唯一
     */
    @Column(nullable = false, length = 62, unique = true)
    private String phone;

    /**
     * 密码 不允许为空
     */
    @Column(nullable = false)
    private String password;

    /**
     * 头像 允许为空
     */
    @Column
    private String portrait;

    /**
     * 个人描述 description
     */
    @Column
    private String description;

    /**
     * 性别 有初始值，所以不能为空
     */
    @Column(nullable = false)
    private int sex = 0;

    /**
     * 地址
     */
    @Column
    private String address;

    /**
     * 学校
     */
    @Column
    private String school;

    /**
     * 生日
     */
    @Column
    private Date birthday;

    /**
     * 职业
     */
    @Column
    private String profession;

    /**
     * 等级
     */
    @Column
    private int grade;

    /**
     * 帖子数
     */
    @Column
    private int feednum;

    /**
     * 星座
     */
    @Column
    private int constellation;

    /**
     * 访问数量
     */
    @Column
    private int visitnum;

    /**
     * token 可以拉取用户信息，所以token必须唯一
     */
    @Column(unique = true)
    private String token;

    /**
     * 用于推送的设备Id
     */
    @Column
    private String pushId;

    /**
     * 定义为创建时间戳，在创建时就已经写入
     */
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    /**
     * 定义为更新时间戳
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    /**
     * 最后一次收到消息的时间
     */
    @Column
    private LocalDateTime lastReceiveAt = LocalDateTime.now();

    /**
     * 我关注的人的列表方法
     */
    // 对应的数据库表字段为TB_USER_FOLLOW.originId
    @JoinColumn(name = "originId")
    // 定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 1对多，一个用户可以有很多关注人，每一次关注都是一个记录
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<>();

    /**
     * 关注我的人的列表
     */
    // 对应的数据库表字段为TB_USER_FOLLOW.targetId
    @JoinColumn(name = "targetId")
    // 定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 1对多，一个用户可以被很多人关注，每一次关注都是一个记录
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();

    /**
     * 我所有创建的群，对应字段为 Group.ownerId
     */
    @JoinColumn(name = "ownerId")
    // 懒加载集合，尽可能的不加载具体的数据，访问groups.size()仅仅只查询数量，不加载具体的group信息
    // 只有遍历集合时才加载具体的数据
    @LazyCollection(LazyCollectionOption.EXTRA)
    // 懒加载，加载用户信息时不加载这个集合
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Group> groups = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getLastReceiveAt() {
        return lastReceiveAt;
    }

    public void setLastReceiveAt(LocalDateTime lastReceiveAt) {
        this.lastReceiveAt = lastReceiveAt;
    }

    public Set<UserFollow> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserFollow> following) {
        this.following = following;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getFeednum() {
        return feednum;
    }

    public void setFeednum(int feednum) {
        this.feednum = feednum;
    }

    public int getConstellation() {
        return constellation;
    }

    public void setConstellation(int constellation) {
        this.constellation = constellation;
    }

    public int getVisitnum() {
        return visitnum;
    }

    public void setVisitnum(int visitnum) {
        this.visitnum = visitnum;
    }
}