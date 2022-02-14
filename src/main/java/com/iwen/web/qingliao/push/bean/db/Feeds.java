package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 帖子的model，就是帖子对应的表
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/28 10:56
 */
@Entity
@Table(name = "TB_FEEDS")
public class Feeds {

    /**
     * 帖子的id
     */
    @Id
    @PrimaryKeyJoinColumn  // 这是一个主键
//    @GeneratedValue(generator = "uuid") // 主键生成的存储类型
//    @GenericGenerator(name = "uuid", strategy = "uuid2") // 把uuid的生成器定义为uuid2
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false) // 不允许更改，不允许为空
    private int id;

    /**
     * 标签图标
     */
    @Column
    private String activityIcon;

    /**
     * 标签内容
     */
    @Column
    private String activityText;

    /**
     * 对应的用户id
     */
    @Column(nullable = false)
    private String userId;

    /**
     * 对应的itemId
     */
    @Column(nullable = false)
    private long itemId;

    /**
     * 该item的类型，不可空
     */
    @Column(nullable = false)
    private int itemType;

    /**
     * 定义为创建时间戳，在创建时就已经写入
     */
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    /**
     * 定义为更新时间戳
     */
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updateTime = LocalDateTime.now();

    /**
     * 帖子内容文本,类型为文本TEXT
     */
    @Column(columnDefinition = "TEXT")
    private String feedsText;

    /**
     * 权限
     * 0 - 全部可见 1- 好友可见
     */
    @Column
    private int permissions;

    /**
     * 地址
     */
    @Column
    private String address;

    /**
     * 状态,正常-1；删除-2；隐藏-3
     */
    @Column
    private int state;

    /**
     * 后台打的标签：为帖子的推荐做准备
     */
    @Column
    private int labels;

    /**
     * 宽度
     */
    @Column
    private int width;

    /**
     * 高度
     */
    @Column
    private int height;

    /**
     * 视频的话，就有一个url,图片也有url
     */
    @Column
    private String url;

    /**
     * 封面
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
