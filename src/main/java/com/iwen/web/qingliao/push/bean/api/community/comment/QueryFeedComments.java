package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * 查询一个帖子的所有评论需要的model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/12/02 20:11
 */
public class QueryFeedComments {
    // 对应帖子的id
    @Expose
    private String itemId;
    // 每一页的条数
    @Expose
    private int pageCount;
    // 查询用户的id
    @Expose
    private String userId;

    public QueryFeedComments(String itemId, int pageCount, String userId) {
        this.itemId = itemId;
        this.pageCount = pageCount;
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // 数据非空校验
    public static boolean check(QueryFeedComments model) {
        return model != null
                && !Strings.isNullOrEmpty(model.userId)
                && !Strings.isNullOrEmpty(model.itemId);
    }
}
