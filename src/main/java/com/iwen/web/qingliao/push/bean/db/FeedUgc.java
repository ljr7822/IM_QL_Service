package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ���ӵĵ��ޡ����ۡ�����ģ��
 *
 * @Author: iwen����
 * @DateTime: 2021/11/28 19:56
 */
@Entity
@Table(name = "TB_FEED_UGC")
public class FeedUgc {
    /**
     * ���޵�id
     */
    @Id
    @PrimaryKeyJoinColumn  // ����һ������
    @GeneratedValue(generator = "uuid") // �������ɵĴ洢����
    @GenericGenerator(name = "uuid", strategy = "uuid2") // ��uuid������������Ϊuuid2
    @Column(updatable = false, nullable = false) // ��������ģ�������Ϊ��
    private String id;

    /**
     * ��Ӧ���ӵ�id
     */
    @Column
    private String itemId;

    /**
     * ���޵�����
     */
    @Column
    private int likeCount;

    /**
     * ���������
     */
    @Column
    private int shareCount;

    /**
     * ��������
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
