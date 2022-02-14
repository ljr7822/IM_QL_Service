package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ĳ�����ӵ�ĳ�����۵���model
 *
 * @Author: iwen����
 * @DateTime: 2021/11/28 19:50
 */
@Entity
@Table(name = "TB_FEED_COMMENT_LIKE")
public class FeedCommentLike {

    /**
     * ���޵�id
     */
    @Id
    @PrimaryKeyJoinColumn  // ����һ������
    @GeneratedValue(generator = "uuid") // �������ɵĴ洢����
    @GenericGenerator(name = "uuid", strategy = "uuid2") // ��uuid������������Ϊuuid2
    @Column(nullable = false) // ��������ģ�������Ϊ��
    private String id;

    /**
     * ���۵�id
     */
    @Column(nullable = false)
    private String commentId;

    /**
     * �����û���id
     */
    @Column( nullable = false)
    private String userId;

    /**
     * �Ƿ��ע
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
