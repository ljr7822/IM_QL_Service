package com.iwen.web.qingliao.push.bean.api.community.comment;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import com.iwen.web.qingliao.push.bean.api.community.feed.PublishFeedModel;


/**
 * 添加一条评论的model
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/12/02 14:37
 */
public class AddCommentModel {

    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;
    private static final int TYPE_VIDEO = 2;

    // 评论用户的id
    @Expose
    private String userId;
    // 帖子的id
    @Expose
    private String itemId;
    // 评论文本
    @Expose
    private String commentText;
    // 评论类型
    @Expose
    private int commentType;
    // 高
    @Expose
    private int height;
    // 宽
    @Expose
    private int width;
    // video_url
    @Expose
    private String videoUrl;
    // image_url
    @Expose
    private String imageUrl;
    // 等级，0-父评论；1-子评论
    @Expose
    private int commentLevel;

    public AddCommentModel(String userId, String itemId, String commentText, int commentType, int height, int width, String videoUrl, String imageUrl, int commentLevel) {
        this.userId = userId;
        this.itemId = itemId;
        this.commentText = commentText;
        this.commentType = commentType;
        this.height = height;
        this.width = width;
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.commentLevel = commentLevel;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getLevel() {
        return commentLevel;
    }

    public void setLevel(int commentLevel) {
        this.commentLevel = commentLevel;
    }

    // 数据非空校验:就检查评论用户和评论的帖子
    public static boolean check(AddCommentModel model) {
        return model != null
                && !Strings.isNullOrEmpty(model.userId)
                && !Strings.isNullOrEmpty(model.itemId);
    }
}
