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
 * ��ȡ�����б�ص�
 *
 * @Author: iwen����
 * @DateTime: 2021/11/29 15:39
 */
public class TabCommunityFeedList {
    /**
     * ���ӵ�id
     */
    @Expose
    private int id;

    /**
     * ��ǩͼ��
     */
    @Expose
    private String activityIcon;

    /**
     * ��ǩ����
     */
    @Expose
    private String activityText;

    /**
     * ��Ӧ��itemId
     */
    @Expose
    private long itemId;

    /**
     * ��item�����ͣ����ɿ�
     */
    @Expose
    private int itemType;

    /**
     * ����Ϊ����ʱ������ڴ���ʱ���Ѿ�д��
     */
    @Expose
    private LocalDateTime createTime;

    /**
     * ����Ϊ����ʱ���
     */
    @Expose
    private LocalDateTime updateTime;

    /**
     * ���������ı�,����Ϊ�ı�TEXT
     */
    @Expose
    private String feedsText;

    /**
     * Ȩ��
     * 0 - ȫ���ɼ� 1- ���ѿɼ�
     */
    @Expose
    private int permissions;

    /**
     * ��ַ
     */
    @Expose
    private String address;

    /**
     * ���
     */
    @Expose
    private int width;

    /**
     * �߶�
     */
    @Expose
    private int height;

    /**
     * ��Ƶ�Ļ�������һ��url,ͼƬҲ��url
     */
    @Expose
    private String url;

    /**
     * ����
     */
    @Expose
    private String cover;

    /**
     * ��Ӧ���û�id
     */
    @Expose
    private String userId;

    /**
     * ������ӵ��û�
     */
    @Expose
    private UserCard author;

    /**
     * ���췽��
     * @param feeds ��
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
     * ���췽��
     * @param feeds ��
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
