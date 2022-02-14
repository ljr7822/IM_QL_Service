package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 帖子的点赞、评论、分享模型
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/28 19:56
 */
@Entity
@Table(name = "TB_FEED_UGC")
public class FeedUgc {
    /**
     * 点赞的id
     */
    @Id
    @PrimaryKeyJoinColumn  // 这是一个主键
    @GeneratedValue(generator = "uuid") // 主键生成的存储类型
    @GenericGenerator(name = "uuid", strategy = "uuid2") // 把uuid的生成器定义为uuid2
    @Column(updatable = false, nullable = false) // 不允许更改，不允许为空
    private String id;

    /**
     * 对应帖子的id
     */
    @Column
    private String itemId;

    /**
     * 点赞的数量
     */
    @Column
    private int likeCount;

    /**
     * 分享的数量
     */
    @Column
    private int shareCount;

    /**
     * 评论数量
     */
    @Column
    private int commentCount;

    public FeedUgc() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
