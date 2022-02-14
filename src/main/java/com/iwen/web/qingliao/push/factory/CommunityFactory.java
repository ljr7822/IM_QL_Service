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
 * ������ص�factory
 *
 * @Author: iwen����
 * @DateTime: 2021/11/29 13:01
 */
public class CommunityFactory {
    /**
     * ���ݲ�ͬ���ͽ��иı�����
     */
    public static final String COUNT_LIKE = "lickCount";
    public static final String COUNT_COMMENT = "commentCount";
    public static final String COUNT_SHARE = "shareCount";

    /**
     * ����һ������
     *
     * @param model PublishFeedModel
     * @return Feeds
     */
    public static Feeds createFees(PublishFeedModel model) {
        Feeds feeds = new Feeds();
        feeds.setUserId(model.getUserId()); // �����ӵ��û�
        feeds.setCover(model.getCoverUrl()); // ����url
        feeds.setFeedsText(model.getFeedsText()); // �����ı�
        feeds.setItemType(model.getFeedsType()); // ��������
        feeds.setActivityIcon(model.getActivityIcon());// ��ǩͼ��
        feeds.setActivityText(model.getActivityText()); // ��ǩ�ı�
        feeds.setUrl(model.getFileUrl()); // ������url
        feeds.setHeight(model.getFileHeight()); // �ļ���
        feeds.setWidth(model.getFileWidth()); // �ļ���
        feeds.setPermissions(model.getPermissions()); // Ȩ��
        feeds.setAddress(model.getAddress()); // ��ַ
        // ���ݿ�洢
        return Hib.query(session -> {
            session.save(feeds);
            return feeds;
        });
    }

    /**
     * �������ӵ�id��ѯһ������
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
     * �������ӵ�type��ѯһ������
     *
     * @param feedType String ���ӵ����ͣ�0-���ı���1-ͼƬ��2-��Ƶ
     * @return Feeds
     */
    public static Feeds findFeedsByType(String feedType) {
        return Hib.query(session -> (Feeds) session
                .createQuery("from Feeds where itemType=:feedType")
                .setParameter("feedType", feedType)
                .setMaxResults(1) // ��෵��1��
                .uniqueResult());
    }

    /**
     * �������ͷ������и����͵�����
     *
     * @param feedType ���ӵ����ͣ�0-���ı���1-ͼƬ��2-��Ƶ
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
     * ������������
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
     * ��ѯָ����Ŀ������,��feedId����һ������ȡ��count��
     *
     * @param feedId ���һ�����ӵ�id
     * @param count  ����
     * @return Set<Feeds>
     */
    public static Set<Feeds> findFeedByFeedIdAndCount(int feedId, int count) {
        return Hib.query(session -> {
            List<Feeds> countFeed = session.createQuery("from Feeds where id >:feedId order by id asc ")
                    .setParameter("feedId", feedId)// ��ǰ��id
                    //.setFirstResult(feedId)// ���ﲻ��ʹ�ã���Ϊֻ�ܴ�0��ʼ
                    .setMaxResults(count) // ��ѯ����
                    .list();
            return new HashSet<>(countFeed);
        });
    }

    /**
     * �����û�id��ѯ����
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
     * ͨ������id�ҵ����Ӷ�Ӧ��ugc
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
     * ͨ�����۵�idȥ������������
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
     * ɾ��һ������
     *
     * @param feeds Feed
     */
    public static void deleteFeeds(Feeds feeds) {
        Hib.delete(feeds);
    }

    /**
     * ͨ���޸����ӵ�״̬���������ӣ��ﵽɾ��һ�����ӵ�Ŀ��
     *
     * @param feeds Feed
     */
    public static void deleteFeedsByState(Feeds feeds) {
        feeds.setState(1);
        Hib.queryOnly(session -> session.saveOrUpdate(feeds));
    }

    /**
     * ����һ������
     *
     * @param model ���ݹ��������۲���
     * @return FeedComment
     */
    public static FeedComment createComment(AddCommentModel model) {
        // ��ʼ������һ������
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
        // ���ݿ�洢
        return Hib.query(session -> {
            session.save(feedComment);
            return feedComment;
        });
    }

    /**
     * ͨ�����۵�id�ҵ���һ������
     *
     * @param itemId ���۵�id
     * @return FeedComment
     */
    public static FeedComment findFeedCommentById(String itemId) {
        return Hib.query(session -> (FeedComment) session
                .createQuery("from FeedComment where id=:itemId")
                .setParameter("itemId", itemId)
                .uniqueResult());
    }

    /**
     * ɾ��һ������
     *
     * @param feedComment FeedComment
     */
    public static void deleteComment(FeedComment feedComment) {
        Hib.deleteComment(feedComment);
    }

    /**
     * ͨ������id�������и���������
     *
     * @param itemId ���ӵ�id
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
     * ���ӻ�������ӵ�ugc����
     *
     * @param feedUgc    ugc
     * @param countType  Ҫ�ı����������
     * @param changeType Ҫ���ӻ��Ǽ���  0-���ӣ�Ĭ�ϣ�1-����
     * @return FeedUgc
     */
    public static FeedUgc increaseOrReduceCount(FeedUgc feedUgc, String countType, int changeType) {
        if (changeType == 0) {
            if (countType.equals(COUNT_LIKE)) {
                // Ҫ�ı�ϲ��
                feedUgc.setLikeCount(feedUgc.getLikeCount() + 1);
            } else if (countType.equals(COUNT_COMMENT)) {
                // Ҫ�ı�����
                feedUgc.setCommentCount(feedUgc.getCommentCount() + 1);
            } else {
                // �ı����
                feedUgc.setShareCount(feedUgc.getShareCount() + 1);
            }
            return Hib.query(session -> {
                session.saveOrUpdate(feedUgc);
                return feedUgc;
            });
        } else {
            if (countType.equals(COUNT_LIKE)) {
                // Ҫ�ı�ϲ��
                feedUgc.setLikeCount(feedUgc.getLikeCount() - 1);
            } else if (countType.equals(COUNT_COMMENT)) {
                // Ҫ�ı�����
                feedUgc.setCommentCount(feedUgc.getCommentCount() - 1);
            } else {
                // �ı����
                feedUgc.setShareCount(feedUgc.getShareCount() - 1);
            }
            return Hib.query(session -> {
                session.saveOrUpdate(feedUgc);
                return feedUgc;
            });
        }
    }

    /**
     * ����һ�����ӵ�ugc
     *
     * @param itemId       String ��Ӧ���ӵ�id
     * @param commentCount int ������������
     * @param likeCount    int ����ϲ������
     * @param shareCount   int ���ӷ�������
     * @return FeedUgc
     */
    public static FeedUgc creatFeedUgc(String itemId, int commentCount, int likeCount, int shareCount) {
        FeedUgc feedUgc = new FeedUgc();
        feedUgc.setItemId(itemId);
        feedUgc.setCommentCount(commentCount);
        feedUgc.setLikeCount(likeCount);
        feedUgc.setShareCount(shareCount);
        // ���ݿ�洢
        return Hib.query(session -> {
            session.save(feedUgc);
            return feedUgc;
        });
    }

    /**
     * ����һ�����۵�ϲ��״̬
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
     * ����û���һ�����۵�ϲ��״̬
     *
     * @param commentId ���۵�id
     * @param userId    �û���id
     * @return FeedCommentLike
     */
    public static FeedCommentLike toggleCommentLike(String commentId, String userId) {
        // ͨ��commentId��FeedCommentLike���������û��������¼�����û�У��ʹ���һ�����ּ�¼
        FeedCommentLike oldComment = findFeedCommentLikeById(commentId);
        if (oldComment == null) {
            // ������,����һ��
            FeedCommentLike newComment = creatFeedCommentLike(commentId, userId);
            if (newComment != null) {
                // �Ѿ�������
                return newComment;
            } else {
                // ����ʧ��
                return null;
            }
        } else {
            // ���ڣ��޸����ݿ��Ӧ��ֵ
            oldComment.setHasLike(!oldComment.isHasLike());
            return Hib.query(session -> {
                session.saveOrUpdate(oldComment);
                return oldComment;
            });
        }
    }
}
