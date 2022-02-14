package com.iwen.web.qingliao.push.bean.api.community.feed;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.card.UserCard;
import com.iwen.web.qingliao.push.bean.db.Feeds;
import com.iwen.web.qingliao.push.bean.db.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import java.time.LocalDateTime;

/**
 * 获取帖子列表回调
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/29 15:39
 */
public class TabCommunityFeedList {
    /**
     * 帖子的id
     */
    @Expose
    private int id;

    /**
     * 标签图标
     */
    @Expose
    private String activityIcon;

    /**
     * 标签内容
     */
    @Expose
    private String activityText;

    /**
     * 对应的itemId
     */
    @Expose
    private long itemId;

    /**
     * 该item的类型，不可空
     */
    @Expose
    private int itemType;

    /**
     * 定义为创建时间戳，在创建时就已经写入
     */
    @Expose
    private LocalDateTime createTime;

    /**
     * 定义为更新时间戳
     */
    @Expose
    private LocalDateTime updateTime;

    /**
     * 帖子内容文本,类型为文本TEXT
     */
    @Expose
    private String feedsText;

    /**
     * 权限
     * 0 - 全部可见 1- 好友可见
     */
    @Expose
    private int permissions;

    /**
     * 地址
     */
    @Expose
    private String address;

    /**
     * 宽度
     */
    @Expose
    private int width;

    /**
     * 高度
     */
    @Expose
    private int height;

    /**
     * 视频的话，就有一个url,图片也有url
     */
    @Expose
    private String url;

    /**
     * 封面
     */
    @Expose
    private String cover;

    /**
     * 对应的用户id
     */
    @Expose
    private String userId;

    /**
     * 这个帖子的用户
     */
    @Expose
    private UserCard author;

    /**
     * 构造方法
     * @param feeds 帖
     */
    public TabCommunityFeedList(Feeds feeds) {
        this.id = feeds.getId();
        this.activityIcon = feeds.getActivityIcon();
        this.activityText = feeds.getActivityText();
        this.itemId = feeds.getItemId();
        this.itemType = feeds.getItemType();
        this.createTime = feeds.getCreateTime();
        this.updateTime = feeds.getUpdateTime();
        this.feedsText = feeds.getFeedsText();
        this.permissions = feeds.getPermissions();
        this.address = feeds.getAddress();
        this.width = feeds.getWidth();
        this.height = feeds.getHeight();
        this.url = feeds.getUrl();
        this.cover = feeds.getCover();
        this.userId = feeds.getUserId();
    }

    /**
     * 构造方法
     * @param feeds 帖
     */
    public TabCommunityFeedList(Feeds feeds, User user) {
        this.id = feeds.getId();
        this.activityIcon = feeds.getActivityIcon();
        this.activityText = feeds.getActivityText();
        this.itemId = feeds.getItemId();
        this.itemType = feeds.getItemType();
        this.createTime = feeds.getCreateTime();
        this.updateTime = feeds.getUpdateTime();
        this.feedsText = feeds.getFeedsText();
        this.permissions = feeds.getPermissions();
        this.address = feeds.getAddress();
        this.width = feeds.getWidth();
        this.height = feeds.getHeight();
        this.url = feeds.getUrl();
        this.cover = feeds.getCover();
        this.userId = feeds.getUserId();
        this.author = new UserCard(user);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivityIcon() {
        return activityIcon;
    }

    public void setActivityIcon(String activityIcon) {
        this.activityIcon = activityIcon;
    }

    public String getActivityText() {
        return activityText;
    }

    public void setActivityText(String activityText) {
        this.activityText = activityText;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getFeedsText() {
        return feedsText;
    }

    public void setFeedsText(String feedsText) {
        this.feedsText = feedsText;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserCard getAuthor() {
        return author;
    }

    public void setAuthor(UserCard author) {
        this.author = author;
    }
}
