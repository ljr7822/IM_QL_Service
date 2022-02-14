package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ���ӵ�model���������Ӷ�Ӧ�ı�
 *
 * @Author: iwen����
 * @DateTime: 2021/11/28 10:56
 */
@Entity
@Table(name = "TB_FEEDS")
public class Feeds {

    /**
     * ���ӵ�id
     */
    @Id
    @PrimaryKeyJoinColumn  // ����һ������
//    @GeneratedValue(generator = "uuid") // �������ɵĴ洢����
//    @GenericGenerator(name = "uuid", strategy = "uuid2") // ��uuid������������Ϊuuid2
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false) // ��������ģ�������Ϊ��
    private int id;

    /**
     * ��ǩͼ��
     */
    @Column
    private String activityIcon;

    /**
     * ��ǩ����
     */
    @Column
    private String activityText;

    /**
     * ��Ӧ���û�id
     */
    @Column(nullable = false)
    private String userId;

    /**
     * ��Ӧ��itemId
     */
    @Column(nullable = false)
    private long itemId;

    /**
     * ��item�����ͣ����ɿ�
     */
    @Column(nullable = false)
    private int itemType;

    /**
     * ����Ϊ����ʱ������ڴ���ʱ���Ѿ�д��
     */
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * ����Ϊ����ʱ���
     */
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updateTime = LocalDateTime.now();

    /**
     * ���������ı�,����Ϊ�ı�TEXT
     */
    @Column(columnDefinition = "TEXT")
    private String feedsText;

    /**
     * Ȩ��
     * 0 - ȫ���ɼ� 1- ���ѿɼ�
     */
    @Column
    private int permissions;

    /**
     * ��ַ
     */
    @Column
    private String address;

    /**
     * ״̬,����-1��ɾ��-2������-3
     */
    @Column
    private int state;

    /**
     * ��̨��ı�ǩ��Ϊ���ӵ��Ƽ���׼��
     */
    @Column
    private int labels;

    /**
     * ���
     */
    @Column
    private int width;

    /**
     * �߶�
     */
    @Column
    private int height;

    /**
     * ��Ƶ�Ļ�������һ��url,ͼƬҲ��url
     */
    @Column
    private String url;

    /**
     * ����
     */
    @Column
    private String cover;

    public Feeds() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
