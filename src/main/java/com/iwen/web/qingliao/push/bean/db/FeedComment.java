package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * ���ӵ�����model
 *
 * @Author: iwen����
 * @DateTime: 2021/11/28 19:20
 */
@Entity
@Table(name = "TB_FEED_COMMENT")
public class FeedComment {

    /**
     * ���۵�id
     */
    @Id
    @PrimaryKeyJoinColumn  // ����һ������
    @GeneratedValue(generator = "uuid") // �������ɵĴ洢����
    @GenericGenerator(name = "uuid", strategy = "uuid2") // ��uuid������������Ϊuuid2
    @Column(updatable = false, nullable = false) // ��������ģ�������Ϊ��
    private String id;

    /**
     * �������۵��û�id,
     */
    @Column(nullable = false) // ��������ģ�������Ϊ��
    private String userId;

    /**
     * �������۵�id,
     */
    private String commentId;

    /**
     * ��������
     */
    @Column
    private String commentText;

    /**
     * ��������
     */
    @Column(nullable = false)
    private int commentType;

    /**
     * �������۵ĵȼ�
     */
    @Column(nullable = false)
    private int commentLevel;

    /**
     * ��
     */
    @Column
    private int width;

    /**
     * ��
     */
    @Column
    private int height;

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
     * ͼƬ���۵�ͼƬurl
     */
    @Column
    private String imageUrl;

    /**
     * ��Ƶ���۵�url
     */
    @Column
    private String videoUrl;

    /**
     * �������ӵ�����
     */
    @Column(nullable = false)
    private String itemId;

    /**
     * ���۵�����
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
