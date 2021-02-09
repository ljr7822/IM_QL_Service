package com.iwen.web.qingliao.push.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户关系的model
 * 用于用户直接进行的好友关系的实现
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/02/04 15:31
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
public class UserFollow {
    /**
     * 用户id
     */
    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(updatable = false, nullable = false)
    private String id;

    /**
     * 一个发起人，你关注某人，这里就是你
     * 多对一 你可以关注很多人，你的每一次关注都是一条记录
     * 你可以创建很多个关注的信息，所有都是多对一
     * 这里的多对一: 一个User 对应 多个UserFollow
     * optional 不可选 必须存储，一条关注记录一定要有一个“你”
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "originId")// 定义关联的表字段名为 originId，对应是 User.id
    private User origin;

    /**
     * 把这个列提取到model中，不允许为空，不允许更新、插入
     */
    @Column(updatable = false, nullable = false, insertable = false)
    private String originId;

    /**
     * 关注的目标，你关注某人，这里就某人
     * 多对一 你可以被很多人关注，每一次关注都是一条记录
     * 即 多个UserFollow 对应 一个User 的情况
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId") // 定义关联的表字段名为 targetId，对应是 User.id
    private User target;

    /**
     * 把这个列提取到model中，不允许为空，不允许更新、插入
     */
    @Column(updatable = false, nullable = false, insertable = false)
    private String targetId;

    /**
     * 别名，即 你对target的备注名，可以为空
     */
    @Column
    private String alias;

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

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
