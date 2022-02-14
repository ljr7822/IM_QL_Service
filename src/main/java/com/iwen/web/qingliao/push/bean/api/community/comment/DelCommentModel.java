package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * 删除评论的model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/12/02 19:27
 */
public class DelCommentModel {
    // 用户id
    @Expose
    private String userId;
    // 帖子id
    @Expose
    private String itemId;
    // 评论id
    @Expose
    private String commentId;

    public DelCommentModel(String userId, String itemId, String commentId) {
        this.userId = userId;
        this.itemId = itemId;
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    // 数据非空校验
    public static boolean check(DelCommentModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.userId)
                && !Strings.isNullOrEmpty(model.itemId)
                && !Strings.isNullOrEmpty(model.commentId);
    }
}
