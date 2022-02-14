package com.iwen.web.qingliao.push.bean.api.community.feed;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * 发布帖子的model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/29 11:46
 */
public class PublishFeedModel {

    // 默认地址
    private final String defAddress = "重庆西南大学";

    /**
     * 帖子状态:默认正常-1
     */
    private final int FEED_STATE_NORMAL = 1; // 正常
    private final int FEED_STATE_DELETE = 2; // 删除
    private final int FEED_STATE_HIDDEN = 3; // 不可见

    // 默认权限
    private final int defPermissions = 0;

    // 用户id
    @Expose
    private String userId;
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
    // 状态
    @Expose
    private int state;

    public PublishFeedModel(String userId, String coverUrl, String feedsText, int feedsType, int fileHeight, int fileWidth,
                            String fileUrl, String activityIcon, String activityText, String address, int permissions) {
        this.userId = userId;
        this.coverUrl = coverUrl;
        this.feedsText = feedsText;
        this.feedsType = feedsType;
        this.fileHeight = fileHeight;
        this.fileWidth = fileWidth;
        this.fileUrl = fileUrl;
        this.activityIcon = activityIcon;
        this.activityText = activityText;
        this.permissions = permissions;
        if (address.isEmpty()) {
            this.address = defAddress;
        } else {
            this.address = address;
        }
        this.state = FEED_STATE_NORMAL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    // 数据非空校验
    public static boolean check(PublishFeedModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.userId)
                && !Strings.isNullOrEmpty(model.feedsText)
                && !Strings.isNullOrEmpty(model.fileUrl)
                && !Strings.isNullOrEmpty(model.activityText);
    }
}
