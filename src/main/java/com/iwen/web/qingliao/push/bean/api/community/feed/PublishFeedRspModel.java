package com.iwen.web.qingliao.push.bean.api.community.feed;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.card.UserCard;
import com.iwen.web.qingliao.push.bean.db.Feeds;

/**
 * �������ӷ��ص�model
 *
 * @Author: iwen����
 * @DateTime: 2021/11/29 14:11
 */
public class PublishFeedRspModel {
    // ����url
    @Expose
    private String coverUrl;
    // �����ı�
    @Expose
    private String feedsText;
    // ��������
    @Expose
    private int feedsType;
    // ������
    @Expose
    private int fileHeight;
    // ������
    @Expose
    private int fileWidth;
    // ����url
    @Expose
    private String fileUrl;
    // ��ǩͼ��
    @Expose
    private String activityIcon;
    // ��ǩ�ı�
    @Expose
    private String activityText;
    // ��ַ
    @Expose
    private String address;
    // Ȩ��
    @Expose
    private int permissions;
    // �û���Ƭ
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
