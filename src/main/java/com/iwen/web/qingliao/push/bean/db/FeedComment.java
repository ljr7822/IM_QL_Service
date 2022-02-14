package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 帖子的评论model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/28 19:20
 */
@Entity
@Table(name = "TB_FEED_COMMENT")
public class FeedComment {

    /**
     * 评论的id
     */
    @Id
    @PrimaryKeyJoinColumn  // 这是一个主键
    @GeneratedValue(generator = "uuid") // 主键生成的存储类型
    @GenericGenerator(name = "uuid", strategy = "uuid2") // 把uuid的生成器定义为uuid2
    @Column(updatable = false, nullable = false) // 不允许更改，不允许为空
    private String id;

    /**
     * 该条评论的用户id,
     */
    @Column(nullable = false) // 不允许更改，不允许为空
    private String userId;

    /**
     * 该条评论的id,
     */
    private String commentId;

    /**
     * 评论内容
     */
    @Column
    private String commentText;

    /**
     * 评论类型
     */
    @Column(nullable = false)
    private int commentType;

    /**
     * 该条评论的等级
     */
    @Column(nullable = false)
    private int commentLevel;

    /**
     * 宽
     */
    @Column
    private int width;

    /**
     * 高
     */
    @Column
    private int height;

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
     * 图片评论的图片url
     */
    @Column
    private String imageUrl;

    /**
     * 视频评论的url
     */
    @Column
    private String videoUrl;

    /**
     * 哪条帖子的评论
     */
    @Column(nullable = false)
    private String itemId;

    /**
     * 评论点赞数
     */
    @Column
    private int lickCount;

    public FeedComment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public int getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(int commentLevel) {
        this.commentLevel = commentLevel;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getLickCount() {
        return lickCount;
    }

    public void setLickCount(int lickCount) {
        this.lickCount = lickCount;
    }
}
