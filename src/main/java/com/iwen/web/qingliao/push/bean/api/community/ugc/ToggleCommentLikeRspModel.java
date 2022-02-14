package com.iwen.web.qingliao.push.bean.api.community.ugc;

import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.db.FeedComment;
import com.iwen.web.qingliao.push.bean.db.FeedCommentLike;

import javax.persistence.Entity;

/**
 * �ı��û�������ϲ��״̬�Ļص�model
 *
 * @Author: iwen����
 * @DateTime: 2021/12/05 18:31
 */
public class ToggleCommentLikeRspModel {
    @Expose
    private boolean hasLike;

    public ToggleCommentLikeRspModel(FeedCommentLike feedCommentLike) {
        this.hasLike = feedCommentLike.isHasLike();
    }

    public boolean isHasLike() {
        return hasLike;
    }

    public void setHasLike(boolean hasLike) {
        this.hasLike = hasLike;
    }
}
