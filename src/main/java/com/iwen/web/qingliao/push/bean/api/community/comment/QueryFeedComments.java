package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * ��ѯһ�����ӵ�����������Ҫ��model
 *
 * @Author: iwen����
 * @DateTime: 2021/12/02 20:11
 */
public class QueryFeedComments {
    // ��Ӧ���ӵ�id
    @Expose
    private String itemId;
    // ÿһҳ������
    @Expose
    private int pageCount;
    // ��ѯ�û���id
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

    // ���ݷǿ�У��
    public static boolean check(QueryFeedComments model) {
        return model != null
                && !Strings.isNullOrEmpty(model.userId)
                && !Strings.isNullOrEmpty(model.itemId);
    }
}
