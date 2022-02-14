package com.iwen.web.qingliao.push.factory;

import com.iwen.web.qingliao.push.bean.api.community.comment.AddCommentModel;
import com.iwen.web.qingliao.push.bean.api.community.feed.PublishFeedModel;
import com.iwen.web.qingliao.push.bean.db.FeedComment;
import com.iwen.web.qingliao.push.bean.db.FeedCommentLike;
import com.iwen.web.qingliao.push.bean.db.FeedUgc;
import com.iwen.web.qingliao.push.bean.db.Feeds;
import com.iwen.web.qingliao.push.utils.Hib;

import javax.ws.rs.PathParam;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 社区相关的factory
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/29 13:01
 */
public class CommunityFactory {
    /**
     * 根据不同类型进行改变数量
     */
    public static final String COUNT_LIKE = "lickCount";
    public static final String COUNT_COMMENT = "commentCount";
    public static final String COUNT_SHARE = "shareCount";

    /**
     * 创建一个帖子
     *
     * @param model PublishFeedModel
     * @return Feeds
     */
    public static Feeds createFees(PublishFeedModel model) {
        Feeds feeds = new Feeds();
        feeds.setUserId(model.getUserId()); // 发帖子的用户
        feeds.setCover(model.getCoverUrl()); // 封面url
        feeds.setFeedsText(model.getFeedsText()); // 帖子文本
        feeds.setItemType(model.getFeedsType()); // 帖子类型
        feeds.setActivityIcon(model.getActivityIcon());// 标签图标
        feeds.setActivityText(model.getActivityText()); // 标签文本
        feeds.setUrl(model.getFileUrl()); // 附件的url
        feeds.setHeight(model.getFileHeight()); // 文件高
        feeds.setWidth(model.getFileWidth()); // 文件宽
        feeds.setPermissions(model.getPermissions()); // 权限
        feeds.setAddress(model.getAddress()); // 地址
        // 数据库存储
        return Hib.query(session -> {
            session.save(feeds);
            return feeds;
        });
    }

    /**
     * 根据帖子的id查询一个帖子
     *
     * @param feedId String
     * @return Feeds
     */
    public static Feeds findFeedsById(String feedId) {
        return Hib.query(session -> (Feeds) session
                .createQuery("from Feeds where id=:feedId")
                .setParameter("feedId", feedId)
                .uniqueResult());
    }

    /**
     * 根据帖子的type查询一个帖子
     *
     * @param feedType String 帖子的类型；0-纯文本，1-图片，2-视频
     * @return Feeds
     */
    public static Feeds findFeedsByType(String feedType) {
        return Hib.query(session -> (Feeds) session
                .createQuery("from Feeds where itemType=:feedType")
                .setParameter("feedType", feedType)
                .setMaxResults(1) // 最多返回1条
                .uniqueResult());
    }

    /**
     * 根据类型返回所有该类型的帖子
     *
     * @param feedType 帖子的类型；0-纯文本，1-图片，2-视频
     * @return Set<Feeds>
     */
    public static Set<Feeds> findFeedsSetByType(int feedType) {
        return Hib.query(session -> {
            @SuppressWarnings("unchecked")
            List<Feeds> feeds = session.createQuery(
                            "from Feeds where itemType=:feedType")
                    .setParameter("feedType", feedType)
                    .list();
            return new HashSet<>(feeds);
        });
    }

    /**
     * 查找所有帖子
     *
     * @return Set<Feeds>
     */
    public static Set<Feeds> findAllFeeds() {
        return Hib.query(session -> {
            List<Feeds> feeds = session.createQuery(
                            "from Feeds")
                    .list();
            return new HashSet<>(feeds);
        });
    }

    /**
     * 查询指定条目的帖子,从feedId的下一个算起，取出count条
     *
     * @param feedId 最后一条帖子的id
     * @param count  数量
     * @return Set<Feeds>
     */
    public static Set<Feeds> findFeedByFeedIdAndCount(int feedId, int count) {
        return Hib.query(session -> {
            List<Feeds> countFeed = session.createQuery("from Feeds where id >:feedId order by id asc ")
                    .setParameter("feedId", feedId)// 当前的id
                    //.setFirstResult(feedId)// 这里不能使用，因为只能从0开始
                    .setMaxResults(count) // 查询数量
                    .list();
            return new HashSet<>(countFeed);
        });
    }

    /**
     * 根据用户id查询帖子
     *
     * @param userId String
     * @return Set<Feeds>
     */
    public static Set<Feeds> findFeedsSetByUserId(String userId) {
        return Hib.query(session -> {
            @SuppressWarnings("unchecked")
            List<Feeds> feeds = session.createQuery(
                            "from Feeds where userId=:userId")
                    .setParameter("userId", userId)
                    .list();
            return new HashSet<>(feeds);
        });
    }

    /**
     * 通过帖子id找到帖子对应的ugc
     *
     * @param itemId String
     * @return FeedUgc
     */
    public static FeedUgc findFeedUgcById(String itemId) {
        return Hib.query(session -> (FeedUgc) session
                .createQuery("from FeedUgc where itemId=:itemId")
                .setParameter("itemId", itemId)
                .uniqueResult());
    }

    /**
     * 通过评论的id去查找这条评论
     *
     * @param commentId String
     * @return FeedCommentLike
     */
    public static FeedCommentLike findFeedCommentLikeById(String commentId) {
        return Hib.query(session -> (FeedCommentLike) session
                .createQuery("from FeedCommentLike where commentId=:commentId")
                .setParameter("commentId", commentId)
                .uniqueResult());
    }

    /**
     * 删除一个帖子
     *
     * @param feeds Feed
     */
    public static void deleteFeeds(Feeds feeds) {
        Hib.delete(feeds);
    }

    /**
     * 通过修改帖子的状态来隐藏帖子，达到删除一个帖子的目的
     *
     * @param feeds Feed
     */
    public static void deleteFeedsByState(Feeds feeds) {
        feeds.setState(1);
        Hib.queryOnly(session -> session.saveOrUpdate(feeds));
    }

    /**
     * 创建一条评论
     *
     * @param model 传递过来的评论参数
     * @return FeedComment
     */
    public static FeedComment createComment(AddCommentModel model) {
        // 开始构造这一条评论
        FeedComment feedComment = new FeedComment();
        feedComment.setUserId(model.getUserId());
        feedComment.setItemId(model.getItemId());
        feedComment.setCommentText(model.getCommentText());
        feedComment.setCommentType(model.getCommentType());
        feedComment.setImageUrl(model.getImageUrl());
        feedComment.setVideoUrl(model.getVideoUrl());
        feedComment.setCommentLevel(model.getLevel());
        feedComment.setHeight(model.getHeight());
        feedComment.setWidth(model.getWidth());
        // 数据库存储
        return Hib.query(session -> {
            session.save(feedComment);
            return feedComment;
        });
    }

    /**
     * 通过评论的id找到这一条评论
     *
     * @param itemId 评论的id
     * @return FeedComment
     */
    public static FeedComment findFeedCommentById(String itemId) {
        return Hib.query(session -> (FeedComment) session
                .createQuery("from FeedComment where id=:itemId")
                .setParameter("itemId", itemId)
                .uniqueResult());
    }

    /**
     * 删除一个评论
     *
     * @param feedComment FeedComment
     */
    public static void deleteComment(FeedComment feedComment) {
        Hib.deleteComment(feedComment);
    }

    /**
     * 通过帖子id返回所有该帖子评论
     *
     * @param itemId 帖子的id
     * @return Set<FeedComment>
     */
    public static Set<FeedComment> findCommentSetByFeedId(String itemId) {
        return Hib.query(session -> {
            @SuppressWarnings("unchecked")
            List<FeedComment> commentList = session.createQuery(
                            "from FeedComment where itemId=:itemId")
                    .setParameter("itemId", itemId)
                    .list();
            return new HashSet<>(commentList);
        });
    }

    /**
     * 增加或减少帖子的ugc数量
     *
     * @param feedUgc    ugc
     * @param countType  要改变的数量类型
     * @param changeType 要增加还是减少  0-增加（默认）1-减少
     * @return FeedUgc
     */
    public static FeedUgc increaseOrReduceCount(FeedUgc feedUgc, String countType, int changeType) {
        if (changeType == 0) {
            if (countType.equals(COUNT_LIKE)) {
                // 要改变喜欢
                feedUgc.setLikeCount(feedUgc.getLikeCount() + 1);
            } else if (countType.equals(COUNT_COMMENT)) {
                // 要改变评论
                feedUgc.setCommentCount(feedUgc.getCommentCount() + 1);
            } else {
                // 改变分享
                feedUgc.setShareCount(feedUgc.getShareCount() + 1);
            }
            return Hib.query(session -> {
                session.saveOrUpdate(feedUgc);
                return feedUgc;
            });
        } else {
            if (countType.equals(COUNT_LIKE)) {
                // 要改变喜欢
                feedUgc.setLikeCount(feedUgc.getLikeCount() - 1);
            } else if (countType.equals(COUNT_COMMENT)) {
                // 要改变评论
                feedUgc.setCommentCount(feedUgc.getCommentCount() - 1);
            } else {
                // 改变分享
                feedUgc.setShareCount(feedUgc.getShareCount() - 1);
            }
            return Hib.query(session -> {
                session.saveOrUpdate(feedUgc);
                return feedUgc;
            });
        }
    }

    /**
     * 创建一个帖子的ugc
     *
     * @param itemId       String 对应帖子的id
     * @param commentCount int 帖子评论总数
     * @param likeCount    int 帖子喜欢总数
     * @param shareCount   int 帖子分享数量
     * @return FeedUgc
     */
    public static FeedUgc creatFeedUgc(String itemId, int commentCount, int likeCount, int shareCount) {
        FeedUgc feedUgc = new FeedUgc();
        feedUgc.setItemId(itemId);
        feedUgc.setCommentCount(commentCount);
        feedUgc.setLikeCount(likeCount);
        feedUgc.setShareCount(shareCount);
        // 数据库存储
        return Hib.query(session -> {
            session.save(feedUgc);
            return feedUgc;
        });
    }

    /**
     * 创建一条评论的喜欢状态
     *
     * @param commentId String
     * @param userId    String
     * @return FeedCommentLike
     */
    public static FeedCommentLike creatFeedCommentLike(String commentId, String userId) {
        FeedCommentLike creatCommentLike = new FeedCommentLike();
        creatCommentLike.setCommentId(commentId);
        creatCommentLike.setUserId(userId);
        creatCommentLike.setHasLike(true);
        return Hib.query(session -> {
            session.save(creatCommentLike);
            return creatCommentLike;
        });
    }

    /**
     * 变更用户对一条评论的喜欢状态
     *
     * @param commentId 评论的id
     * @param userId    用户的id
     * @return FeedCommentLike
     */
    public static FeedCommentLike toggleCommentLike(String commentId, String userId) {
        // 通过commentId在FeedCommentLike里面查找有没有这条记录，如果没有，就创建一条这种记录
        FeedCommentLike oldComment = findFeedCommentLikeById(commentId);
        if (oldComment == null) {
            // 不存在,创建一条
            FeedCommentLike newComment = creatFeedCommentLike(commentId, userId);
            if (newComment != null) {
                // 已经创建好
                return newComment;
            } else {
                // 创建失败
                return null;
            }
        } else {
            // 存在，修改数据库对应的值
            oldComment.setHasLike(!oldComment.isHasLike());
            return Hib.query(session -> {
                session.saveOrUpdate(oldComment);
                return oldComment;
            });
        }
    }
}
