package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 某个帖子的某条评论点赞model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/28 19:50
 */
@Entity
@Table(name = "TB_FEED_COMMENT_LIKE")
public class FeedCommentLike {

    /**
     * 点赞的id
     */
    @Id
    @PrimaryKeyJoinColumn  // 这是一个主键
    @GeneratedValue(generator = "uuid") // 主键生成的存储类型
    @GenericGenerator(name = "uuid", strategy = "uuid2") // 把uuid的生成器定义为uuid2
    @Column(nullable = false) // 不允许更改，不允许为空
    private String id;

    /**
     * 评论的id
     */
    @Column(nullable = false)
    private String commentId;

    /**
     * 点赞用户的id
     */
    @Column( nullable = false)
    private String userId;

    /**
     * 是否关注
     */
    @Column( nullable = false)
    private boolean hasLike;

    public FeedCommentLike() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }
}
