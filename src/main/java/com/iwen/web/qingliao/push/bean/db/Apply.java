package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 申请记录表
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/02/04 21:57
 */
@Entity
@Table(name = "TB_APPLY")
public class Apply {
    //加好友
    public static final int TYPE_ADD_USER = 1;
    //加好友
    public static final int TYPE_ADD_GROUP = 2;

    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    /**
     * 描述,申请信息 eg：约吗？
     */
    @Column(nullable = false)
    private String description;

    /**
     * 附件,可为空，可以附带图片地址，或者其他
     */
    @Column(columnDefinition = "TEXT")
    private String attach;

    /**
     * 当前申请的类型
     */
    @Column(nullable = false)
    private int type;

    /**
     * 目标id,不进行强关联，不建立主外键
     * type->TYPE_ADD_USER: User.Id
     * type->TYPE_ADD_GROUP: Group.Id
     */
    @Column(nullable = false)
    private String targetId;

    /**
     * 申请人，可为空（系统信息），一个人可以有很多个申请
     */
    @ManyToOne
    @JoinColumn(name = "applicantId")
    private User applicant;

    @Column(updatable = false, insertable = false)
    private String applicantId;

    /**
     * 定义为创建时间戳，在创建时就已经写入
     */
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    /**
     * 定义为更新时间戳
     */
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}