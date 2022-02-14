package com.iwen.web.qingliao.push.service;

import com.iwen.web.qingliao.push.Constant;
import com.iwen.web.qingliao.push.bean.api.base.ResponseModel;
import com.iwen.web.qingliao.push.bean.api.community.comment.*;
import com.iwen.web.qingliao.push.bean.api.community.feed.*;
import com.iwen.web.qingliao.push.bean.api.community.ugc.AddFeedCommentCountRspModel;
import com.iwen.web.qingliao.push.bean.api.community.ugc.AddFeedLikeCountRspModel;
import com.iwen.web.qingliao.push.bean.api.community.ugc.AddFeedShareCountRspModel;
import com.iwen.web.qingliao.push.bean.api.community.ugc.ToggleCommentLikeRspModel;
import com.iwen.web.qingliao.push.bean.card.UserCard;
import com.iwen.web.qingliao.push.bean.db.*;
import com.iwen.web.qingliao.push.factory.CommunityFactory;
import com.iwen.web.qingliao.push.factory.UserFactory;
import com.iwen.web.qingliao.push.utils.LogUtils;
import org.hibernate.dialect.PostgreSQLDialect;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ������ؽӿ����
 *
 * @Author: iwen����
 * @DateTime: 2021/11/24 11:55
 */
@Path("/community")
public class CommunityService extends BaseService {
    private static final String TAG = "CommunityService";

    /**
     * ��ѯ��������tabҳ�������
     *
     * @param tabType   tab����
     * @param feedId    ���ӵ�id
     * @param pageCount ÿҳ��ȡ������
     * @return <List<GetCommunityFeeds>
     */
    @GET
    @Path("/{tabType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<TabCommunityFeedList>> getCommunityFeeds(@PathParam("tabType") int tabType, @QueryParam("feedId") int feedId, @QueryParam("pageCount") int pageCount) {
        LogUtils.info(TAG, ">>>>>>Start request ---/{tabType}: PathParam: tabType = " + tabType + ", feedId = " + feedId + ", pageCount = " + pageCount);
        // ���ݲ�ͬ���͵�tab�������Ӳ���,���ز��ҵ�����
        Set<Feeds> feedsSet = CommunityFactory.findFeedByFeedIdAndCount(feedId,pageCount);
        if (feedsSet != null && feedsSet.size() > 0) {
            LogUtils.info(TAG, ">>>>>>>/{tabType}/{feedId}/{pageCount} >>>>>> request successful: size=" + feedsSet.size());
            // List<GetCommunityFeeds> feedsList = feedsSet.stream().map(GetCommunityFeeds::new).collect(Collectors.toList());
            List<TabCommunityFeedList> fl = new ArrayList<>();
            // ��setת����list
            for (Feeds feed : feedsSet) {
                // �ҵ�ÿƪ���ӵ��û�
                User user = UserFactory.findById(feed.getUserId());
                if (user == null) {
                    // �쳣����û���ҵ�����û���һ����������֣����ڲ���ɾ�������ݿ⣬���⵼������������
                    User creatUser = new User();
                    creatUser.setName("iwen");
                    creatUser.setId("808080");
                    // ����һ������
                    TabCommunityFeedList getCommunityFeeds = new TabCommunityFeedList(feed, creatUser);
                    fl.add(getCommunityFeeds);
                } else {
                    // ����һ������
                    TabCommunityFeedList getCommunityFeeds = new TabCommunityFeedList(feed, user);
                    fl.add(getCommunityFeeds);
                }
            }
            return ResponseModel.buildOk(fl);
        }else if (feedsSet.size() == 0){
            // û��������
            return ResponseModel.buildNotHaveMoreFeed();
        }
        // �������쳣
        LogUtils.error(TAG, ">>>>>>>/{tabType}error: Not find feeds.");
        return ResponseModel.buildServiceError();
    }

    /**
     * ��ѯһ���û����е�����
     *
     * @param userId �û�id
     * @return <List<UserCommunityFeedList>
     */
    @GET
    @Path("/user/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCommunityFeedList>> getFeedsByUserId(@PathParam("userId") String userId) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/{userId}");
        Set<Feeds> feedsSet = CommunityFactory.findFeedsSetByUserId(userId);
        if (feedsSet != null && feedsSet.size() > 0) {
            LogUtils.info(TAG, ">>>>>>>/{userId} request successful: size=" + feedsSet.size());
            List<UserCommunityFeedList> feedsList = feedsSet.stream().map(UserCommunityFeedList::new).collect(Collectors.toList());
            return ResponseModel.buildOk(feedsList);
        }
        // �������쳣
        LogUtils.error(TAG, ">>>>>>>/{userId}error: Not find feeds.");
        return ResponseModel.buildServiceError();
    }

    /**
     * ����һ������
     *
     * @param model PublishFeedModel
     * @return Feeds
     */
    @POST
    @Path("/publish")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<PublishFeedRspModel> publishFeeds(PublishFeedModel model) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/publish");
        if (!PublishFeedModel.check(model)) {
            // �����쳣
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        // ����һ������
        Feeds feeds = CommunityFactory.createFees(model);
        if (feeds != null) {
            // �����ɹ�,��ѯ���͸����ӵ��û�
            User user = UserFactory.findById(feeds.getUserId());
            // ����һ���û���Ƭ
            UserCard userCard = new UserCard(user);
            // ��װ������
            PublishFeedRspModel publishFeedRspModel = new PublishFeedRspModel(userCard, feeds);
            return ResponseModel.buildOk(publishFeedRspModel);
        } else {
            // ����ʧ��
            LogUtils.error(TAG, "��������ʧ��");
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * ɾ��һ������
     *
     * @param id ���ӵ�id
     * @return DeleteFeedRspModel
     */
    @GET
    @Path("/deletefeed/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<DeleteFeedRspModel> deleteFeeds(@PathParam("id") String id) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/deletefeed/{id}");
        // ���ҵ��������
        Feeds feeds = CommunityFactory.findFeedsById(id);
        if (feeds != null) {
            // �ҵ���������ӣ���ʼ����ɾ������
            CommunityFactory.deleteFeedsByState(feeds);
            DeleteFeedRspModel deleteFeedRspModel = new DeleteFeedRspModel(Constant.DELETE_FEED_SUCCESSFUL);
            return ResponseModel.buildOk(deleteFeedRspModel);
        } else {
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * ����һ������
     *
     * @param model AddCommentModel
     * @return AddCommentRspModel
     */
    @POST
    @Path("/addComment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AddCommentRspModel> addComment(AddCommentModel model) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/addComment");
        if (!AddCommentModel.check(model)) {
            // �����쳣
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        // ��ʼ��������
        FeedComment feedComment = CommunityFactory.createComment(model);
        if (feedComment != null) {
            // �޸���������
            FeedUgc feedUgc = increaseOrReduceCommentCount(0, model.getItemId());
            // ��������
            AddCommentRspModel addCommentRspModel = new AddCommentRspModel(feedComment, feedUgc);
            return ResponseModel.buildOk(addCommentRspModel);
        } else {
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * ɾ��һ������
     *
     * @param model DelCommentModel
     * @return DelCommentRspModel
     */
    @POST
    @Path("/deleteComment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<DelCommentRspModel> deleteComment(DelCommentModel model) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/deleteComment");
        if (!DelCommentModel.check(model)) {
            // �����쳣
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        // ���ҵ��������
        FeedComment feedComment = CommunityFactory.findFeedCommentById(model.getCommentId());
        if (feedComment != null) {
            // ��ʼɾ������
            CommunityFactory.deleteComment(feedComment);
            FeedUgc feedUgc = increaseOrReduceCommentCount(1, model.getItemId());
            DelCommentRspModel delCommentRspModel = new DelCommentRspModel(feedUgc);
            return ResponseModel.buildOk(delCommentRspModel);
        } else {
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * ��ѯĳ�����Ӷ�Ӧ�������б�
     *
     * @param model QueryFeedComments
     * @return List<QueryFeedCommentsRsp>
     */
    @POST
    @Path("/queryFeedComments")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<QueryFeedCommentsRsp>> queryFeedCommentList(QueryFeedComments model) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/queryFeedComments");
        if (!QueryFeedComments.check(model)) {
            // �����쳣
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        Set<FeedComment> comments = CommunityFactory.findCommentSetByFeedId(model.getItemId());
        if (comments != null && comments.size() > 0) {
            LogUtils.info(TAG, ">>>>>>>/{queryFeedComments} request successful: size=" + comments.size());
            // List<GetCommunityFeeds> feedsList = feedsSet.stream().map(GetCommunityFeeds::new).collect(Collectors.toList());
            List<QueryFeedCommentsRsp> feedCommentsRspList = new ArrayList<>();
            // ��setת����list
            for (FeedComment feedComment : comments) {
                // �ҵ�ÿƪ���ӵ��û�
                User user = UserFactory.findById(feedComment.getUserId());
                // ����һ������
                QueryFeedCommentsRsp queryFeedCommentsRsp = new QueryFeedCommentsRsp(feedComment, user);
                feedCommentsRspList.add(queryFeedCommentsRsp);
            }
            return ResponseModel.buildOk(feedCommentsRspList);
        }
        // �������쳣
        LogUtils.error(TAG, ">>>>>>>/{queryFeedComments}error: Not find feeds.");
        return ResponseModel.buildServiceError();
    }

    /**
     * ���ӻ����һ������ϲ��������
     *
     * @param itemId     ���ӵ�id
     * @param changeType 0-���ӣ�Ĭ�ϣ�1-����
     * @return AddFeedLikeCountRspModel
     */
    @GET
    @Path("/ugc/increaseOrReduceLikeCount/{changeType}/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AddFeedLikeCountRspModel> increaseOrReduceLikeCount(@PathParam("changeType") int changeType, @PathParam("itemId") String itemId) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/ugc/increaseOrReduceLikeCount/{changeType}/{itemId}");
        // ���ҵ��������ugc
        FeedUgc oldUgc = CommunityFactory.findFeedUgcById(itemId);
        if (oldUgc != null) {
            // ������ӵ�ugc���ڣ���ʼ�޸����ݿ�
            FeedUgc newUgc = CommunityFactory.increaseOrReduceCount(oldUgc, CommunityFactory.COUNT_LIKE, changeType);
            if (newUgc != null) {
                AddFeedLikeCountRspModel addFeedLikeCountRspModel = new AddFeedLikeCountRspModel(newUgc);
                return ResponseModel.buildOk(addFeedLikeCountRspModel);
            }
        } else {
            // ������ӵ�ugc�����ڣ����ǵ�һ�����ugc��Ӧ�ô���һ��ugc
            FeedUgc creatUgc = CommunityFactory.creatFeedUgc(itemId, 0, 1, 0);
            if (creatUgc != null) {
                AddFeedLikeCountRspModel addFeedLikeCountRspModel = new AddFeedLikeCountRspModel(creatUgc);
                return ResponseModel.buildOk(addFeedLikeCountRspModel);
            }
        }
        return ResponseModel.buildServiceError();
    }

    /**
     * ���ӻ����һ�����ӷ��������
     *
     * @param changeType 0-���ӣ�Ĭ�ϣ�1-����
     * @param itemId     ���ӵ�id
     * @return AddFeedShareCountRspModel
     */
    @GET
    @Path("/ugc/increaseOrReduceShareCount/{changeType}/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AddFeedShareCountRspModel> increaseOrReduceShareCount(@PathParam("changeType") int changeType, @PathParam("itemId") String itemId) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/ugc/increaseOrReduceShareCount/{changeType}/{itemId}");
        // ���ҵ��������ugc
        FeedUgc oldUgc = CommunityFactory.findFeedUgcById(itemId);
        if (oldUgc != null) {
            // ������ӵ�ugc���ڣ���ʼ�޸����ݿ�
            FeedUgc newUgc = CommunityFactory.increaseOrReduceCount(oldUgc, CommunityFactory.COUNT_SHARE, changeType);
            if (newUgc != null) {
                AddFeedShareCountRspModel addFeedShareCountRspModel = new AddFeedShareCountRspModel(newUgc);
                return ResponseModel.buildOk(addFeedShareCountRspModel);
            }
        } else {
            // ������ӵ�ugc�����ڣ����ǵ�һ�����ugc��Ӧ�ô���һ��ugc
            FeedUgc creatUgc = CommunityFactory.creatFeedUgc(itemId, 0, 0, 1);
            if (creatUgc != null) {
                AddFeedShareCountRspModel addFeedShareCountRspModel = new AddFeedShareCountRspModel(creatUgc);
                return ResponseModel.buildOk(addFeedShareCountRspModel);
            }
        }
        return ResponseModel.buildServiceError();
    }

//    @GET
//    @Path("/ugc/increaseOrReduceCommentCount/{changeType}/{itemId}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)

    /**
     * ������������������¶�ӿڸ��ⲿ�����һ������ʱ�Զ�����
     *
     * @param changeType �������� 0-���ӣ�Ĭ�ϣ�1-����
     * @param itemId     ���ӵ�id
     * @return FeedUgc
     */
    public FeedUgc increaseOrReduceCommentCount(int changeType, String itemId) {
//        LogUtils.info(TAG, ">>>>>>>Start request ---/ugc/increaseOrReduceCommentCount/{changeType}/{itemId}");
        // ���ҵ��������ugc
        FeedUgc oldUgc = CommunityFactory.findFeedUgcById(itemId);
        if (oldUgc != null) {
            // ������ӵ�ugc���ڣ���ʼ�޸����ݿ�
            FeedUgc newUgc = CommunityFactory.increaseOrReduceCount(oldUgc, CommunityFactory.COUNT_COMMENT, changeType);
            if (newUgc != null) {
                return newUgc;
            }
        } else {
            // ������ӵ�ugc�����ڣ����ǵ�һ�����ugc��Ӧ�ô���һ��ugc
            FeedUgc creatUgc = CommunityFactory.creatFeedUgc(itemId, 1, 0, 0);
            if (creatUgc != null) {
                return creatUgc;
            }
        }
        return null;
    }

    /**
     * ����û���һ�����۵�ϲ��״̬
     *
     * @param commentId ���۵�id
     * @param userId    �û���id
     * @return ToggleCommentLikeRspModel
     */
    @GET
    @Path("/ugc/toggleCommentLike/{commentId}/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<ToggleCommentLikeRspModel> toggleCommentLike(@PathParam("commentId") String commentId, @PathParam("userId") String userId) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/ugc/toggleCommentLike/{commentId}/{userId}");
        FeedCommentLike feedCommentLike = CommunityFactory.toggleCommentLike(commentId, userId);
        if (feedCommentLike != null) {
            ToggleCommentLikeRspModel rspModel = new ToggleCommentLikeRspModel(feedCommentLike);
            return ResponseModel.buildOk(rspModel);
        }
        LogUtils.error(TAG, ">>>>>>>request error---/ugc/toggleCommentLike/{commentId}/{userId} feedCommentLike is null.");
        return ResponseModel.buildStateChangeError();
    }
}
