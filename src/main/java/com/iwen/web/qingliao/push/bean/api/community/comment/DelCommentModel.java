package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * ɾ�����۵�model
 *
 * @Author: iwen����
 * @DateTime: 2021/12/02 19:27
 */
public class DelCommentModel {
    // �û�id
    @Expose
    private String userId;
    // ����id
    @Expose
    private String itemId;
    // ����id
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

    // ���ݷǿ�У��
    public static boolean check(DelCommentModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.userId)
                && !Strings.isNullOrEmpty(model.itemId)
                && !Strings.isNullOrEmpty(model.commentId);
    }
}
