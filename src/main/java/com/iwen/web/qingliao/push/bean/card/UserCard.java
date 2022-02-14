package com.iwen.web.qingliao.push.bean.card;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.utils.Hib;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户信息卡片，用于替代传递User类，防止敏感信息风险
 *
 * @Author: iwen大大怪
 * @DateTime: 11-25 025 18:00
 */
public class UserCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex = 0;
    /**
     * 地址
     */
    @Expose
    private String address;

    /**
     * 学校
     */
    @Expose
    private String school;

    /**
     * 生日
     */
    @Expose
    private Date birthday;

    /**
     * 职业
     */
    @Expose
    private String profession;

    /**
     * 等级
     */
    @Expose
    private int grade;

    /**
     * 帖子数
     */
    @Expose
    private int feednum;

    /**
     * 星座
     */
    @Expose
    private int constellation;

    /**
     * 访问数量
     */
    @Expose
    private int visitnum;
    // 用户信息最后的更新时间
    @Expose
    private LocalDateTime modifyAt;
    // 用户关注人的数量
    @Expose
    private int follows;
    // 用户粉丝的数量
    @Expose
    private int following;
    // 我与这个User的关系状态，我是否关注了这个人
    @Expose
    private boolean isFollow;

    public UserCard(final User user) {
        this(user, false);
    }

    public UserCard(final User user, boolean isFollow) {
        this.id = user.getId();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.portrait = user.getPortrait();
        this.desc = user.getDescription();
        this.sex = user.getSex();
        this.address = user.getAddress();
        this.school = user.getSchool();
        this.birthday = user.getBirthday();
        this.profession = user.getProfession();
        this.grade = user.getGrade();
        this.feednum = user.getFeednum();
        this.constellation = user.getConstellation();
        this.visitnum = user.getVisitnum();
        this.modifyAt = user.getUpdateAt();
        this.isFollow = isFollow;

        // 得到关注人和粉丝的数量
        // user.getFollowers().size()
        // 懒加载会报错，因为没有Session
        Hib.queryOnly(session -> {
            // 重新加载一次用户信息
            session.load(user, user.getId());
            // 这个时候仅仅只是进行了数量查询，并没有查询整个集合
            // 要查询集合，必须在session存在情况下进行遍历
            // 或者使用Hibernate.initialize(user.getFollowers());
            follows = user.getFollowers().size();
            following = user.getFollowing().size();
        });
    }

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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    public int getFollows() {
        return follows;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
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