package com.iwen.web.qingliao.push.bean.api.community.feed;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.card.UserCard;
import com.iwen.web.qingliao.push.bean.db.Feeds;

/**
 * 发送帖子返回的model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/29 14:11
 */
public class PublishFeedRspModel {
    // 封面url
    @Expose
    private String coverUrl;
    // 帖子文本
    @Expose
    private String feedsText;
    // 帖子类型
    @Expose
    private int feedsType;
    // 附件高
    @Expose
    private int fileHeight;
    // 附件宽
    @Expose
    private int fileWidth;
    // 附件url
    @Expose
    private String fileUrl;
    // 标签图标
    @Expose
    private String activityIcon;
    // 标签文本
    @Expose
    private String activityText;
    // 地址
    @Expose
    private String address;
    // 权限
    @Expose
    private int permissions;
    // 用户卡片
    @Expose
    private UserCard user;

    public PublishFeedRspModel(UserCard userCard) {
        this.user = userCard;
    }

    public PublishFeedRspModel(UserCard user, Feeds feeds) {
        this.coverUrl = feeds.getCover();
        this.feedsText = feeds.getFeedsText();
        this.feedsType = feeds.getItemType();
        this.fileHeight = feeds.getHeight();
        this.fileWidth = feeds.getWidth();
        this.fileUrl = feeds.getUrl();
        this.activityIcon = feeds.getActivityIcon();
        this.activityText = feeds.getActivityText();
        this.address = feeds.getAddress();
        this.permissions = feeds.getPermissions();
        this.user = user;
    }

    public UserCard getUserCard() {
        return user;
    }

    public void setUserCard(UserCard userCard) {
        this.user = userCard;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getFeedsText() {
        return feedsText;
    }

    public void setFeedsText(String feedsText) {
        this.feedsText = feedsText;
    }

    public int getFeedsType() {
        return feedsType;
    }

    public void setFeedsType(int feedsType) {
        this.feedsType = feedsType;
    }

    public int getFileHeight() {
        return fileHeight;
    }

    public void setFileHeight(int fileHeight) {
        this.fileHeight = fileHeight;
    }

    public int getFileWidth() {
        return fileWidth;
    }

    public void setFileWidth(int fileWidth) {
        this.fileWidth = fileWidth;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }
}
