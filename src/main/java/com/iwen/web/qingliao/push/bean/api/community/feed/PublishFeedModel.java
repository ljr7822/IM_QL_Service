package com.iwen.web.qingliao.push.bean.api.community.feed;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * �������ӵ�model
 *
 * @Author: iwen����
 * @DateTime: 2021/11/29 11:46
 */
public class PublishFeedModel {

    // Ĭ�ϵ�ַ
    private final String defAddress = "�������ϴ�ѧ";

    /**
     * ����״̬:Ĭ������-1
     */
    private final int FEED_STATE_NORMAL = 1; // ����
    private final int FEED_STATE_DELETE = 2; // ɾ��
    private final int FEED_STATE_HIDDEN = 3; // ���ɼ�

    // Ĭ��Ȩ��
    private final int defPermissions = 0;

    // �û�id
    @Expose
    private String userId;
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
    // ״̬
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

    // ���ݷǿ�У��
    public static boolean check(PublishFeedModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.userId)
                && !Strings.isNullOrEmpty(model.feedsText)
                && !Strings.isNullOrEmpty(model.fileUrl)
                && !Strings.isNullOrEmpty(model.activityText);
    }
}
