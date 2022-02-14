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
 * 社区相关接口入口
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/11/24 11:55
 */
@Path("/community")
public class CommunityService extends BaseService {
    private static final String TAG = "CommunityService";

    /**
     * 查询社区几个tab页面的帖子
     *
     * @param tabType   tab类型
     * @param feedId    帖子的id
     * @param pageCount 每页拉取多少条
     * @return <List<GetCommunityFeeds>
     */
    @GET
    @Path("/{tabType}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<TabCommunityFeedList>> getCommunityFeeds(@PathParam("tabType") int tabType, @QueryParam("feedId") int feedId, @QueryParam("pageCount") int pageCount) {
        LogUtils.info(TAG, ">>>>>>Start request ---/{tabType}: PathParam: tabType = " + tabType + ", feedId = " + feedId + ", pageCount = " + pageCount);
        // 根据不同类型的tab进行帖子查找,返回查找的条数
        Set<Feeds> feedsSet = CommunityFactory.findFeedByFeedIdAndCount(feedId,pageCount);
        if (feedsSet != null && feedsSet.size() > 0) {
            LogUtils.info(TAG, ">>>>>>>/{tabType}/{feedId}/{pageCount} >>>>>> request successful: size=" + feedsSet.size());
            // List<GetCommunityFeeds> feedsList = feedsSet.stream().map(GetCommunityFeeds::new).collect(Collectors.toList());
            List<TabCommunityFeedList> fl = new ArrayList<>();
            // 将set转化成list
            for (Feeds feed : feedsSet) {
                // 找到每篇帖子的用户
                User user = UserFactory.findById(feed.getUserId());
                if (user == null) {
                    // 异常处理：没有找到这个用户，一般情况不出现，由于测试删除了数据库，到这导致这个问题出现
                    User creatUser = new User();
                    creatUser.setName("iwen");
                    creatUser.setId("808080");
                    // 构造一个帖子
                    TabCommunityFeedList getCommunityFeeds = new TabCommunityFeedList(feed, creatUser);
                    fl.add(getCommunityFeeds);
                } else {
                    // 构造一个帖子
                    TabCommunityFeedList getCommunityFeeds = new TabCommunityFeedList(feed, user);
                    fl.add(getCommunityFeeds);
                }
            }
            return ResponseModel.buildOk(fl);
        }else if (feedsSet.size() == 0){
            // 没有数据了
            return ResponseModel.buildNotHaveMoreFeed();
        }
        // 服务器异常
        LogUtils.error(TAG, ">>>>>>>/{tabType}error: Not find feeds.");
        return ResponseModel.buildServiceError();
    }

    /**
     * 查询一个用户所有的帖子
     *
     * @param userId 用户id
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
        // 服务器异常
        LogUtils.error(TAG, ">>>>>>>/{userId}error: Not find feeds.");
        return ResponseModel.buildServiceError();
    }

    /**
     * 发布一条帖子
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
            // 参数异常
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        // 创建一个帖子
        Feeds feeds = CommunityFactory.createFees(model);
        if (feeds != null) {
            // 创建成功,查询发送该帖子的用户
            User user = UserFactory.findById(feeds.getUserId());
            // 创建一个用户卡片
            UserCard userCard = new UserCard(user);
            // 封装返回体
            PublishFeedRspModel publishFeedRspModel = new PublishFeedRspModel(userCard, feeds);
            return ResponseModel.buildOk(publishFeedRspModel);
        } else {
            // 创建失败
            LogUtils.error(TAG, "创建帖子失败");
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * 删除一个帖子
     *
     * @param id 帖子的id
     * @return DeleteFeedRspModel
     */
    @GET
    @Path("/deletefeed/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<DeleteFeedRspModel> deleteFeeds(@PathParam("id") String id) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/deletefeed/{id}");
        // 先找到这个帖子
        Feeds feeds = CommunityFactory.findFeedsById(id);
        if (feeds != null) {
            // 找到了这个帖子，开始进行删除操作
            CommunityFactory.deleteFeedsByState(feeds);
            DeleteFeedRspModel deleteFeedRspModel = new DeleteFeedRspModel(Constant.DELETE_FEED_SUCCESSFUL);
            return ResponseModel.buildOk(deleteFeedRspModel);
        } else {
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * 发布一条评论
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
            // 参数异常
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        // 开始构建评论
        FeedComment feedComment = CommunityFactory.createComment(model);
        if (feedComment != null) {
            // 修改评论数量
            FeedUgc feedUgc = increaseOrReduceCommentCount(0, model.getItemId());
            // 构建返回
            AddCommentRspModel addCommentRspModel = new AddCommentRspModel(feedComment, feedUgc);
            return ResponseModel.buildOk(addCommentRspModel);
        } else {
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * 删除一条评论
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
            // 参数异常
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        // 先找到这个帖子
        FeedComment feedComment = CommunityFactory.findFeedCommentById(model.getCommentId());
        if (feedComment != null) {
            // 开始删除评论
            CommunityFactory.deleteComment(feedComment);
            FeedUgc feedUgc = increaseOrReduceCommentCount(1, model.getItemId());
            DelCommentRspModel delCommentRspModel = new DelCommentRspModel(feedUgc);
            return ResponseModel.buildOk(delCommentRspModel);
        } else {
            return ResponseModel.buildServiceError();
        }
    }

    /**
     * 查询某个帖子对应的评论列表
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
            // 参数异常
            LogUtils.error(TAG, "Parameters Error!");
            return ResponseModel.buildParameterError();
        }
        Set<FeedComment> comments = CommunityFactory.findCommentSetByFeedId(model.getItemId());
        if (comments != null && comments.size() > 0) {
            LogUtils.info(TAG, ">>>>>>>/{queryFeedComments} request successful: size=" + comments.size());
            // List<GetCommunityFeeds> feedsList = feedsSet.stream().map(GetCommunityFeeds::new).collect(Collectors.toList());
            List<QueryFeedCommentsRsp> feedCommentsRspList = new ArrayList<>();
            // 将set转化成list
            for (FeedComment feedComment : comments) {
                // 找到每篇帖子的用户
                User user = UserFactory.findById(feedComment.getUserId());
                // 构造一个帖子
                QueryFeedCommentsRsp queryFeedCommentsRsp = new QueryFeedCommentsRsp(feedComment, user);
                feedCommentsRspList.add(queryFeedCommentsRsp);
            }
            return ResponseModel.buildOk(feedCommentsRspList);
        }
        // 服务器异常
        LogUtils.error(TAG, ">>>>>>>/{queryFeedComments}error: Not find feeds.");
        return ResponseModel.buildServiceError();
    }

    /**
     * 增加或减少一条帖子喜欢的数量
     *
     * @param itemId     帖子的id
     * @param changeType 0-增加（默认）1-减少
     * @return AddFeedLikeCountRspModel
     */
    @GET
    @Path("/ugc/increaseOrReduceLikeCount/{changeType}/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AddFeedLikeCountRspModel> increaseOrReduceLikeCount(@PathParam("changeType") int changeType, @PathParam("itemId") String itemId) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/ugc/increaseOrReduceLikeCount/{changeType}/{itemId}");
        // 先找到这个帖子ugc
        FeedUgc oldUgc = CommunityFactory.findFeedUgcById(itemId);
        if (oldUgc != null) {
            // 这个帖子的ugc存在，开始修改数据库
            FeedUgc newUgc = CommunityFactory.increaseOrReduceCount(oldUgc, CommunityFactory.COUNT_LIKE, changeType);
            if (newUgc != null) {
                AddFeedLikeCountRspModel addFeedLikeCountRspModel = new AddFeedLikeCountRspModel(newUgc);
                return ResponseModel.buildOk(addFeedLikeCountRspModel);
            }
        } else {
            // 这个帖子的ugc不存在，这是第一次添加ugc，应该创建一条ugc
            FeedUgc creatUgc = CommunityFactory.creatFeedUgc(itemId, 0, 1, 0);
            if (creatUgc != null) {
                AddFeedLikeCountRspModel addFeedLikeCountRspModel = new AddFeedLikeCountRspModel(creatUgc);
                return ResponseModel.buildOk(addFeedLikeCountRspModel);
            }
        }
        return ResponseModel.buildServiceError();
    }

    /**
     * 增加或减少一条帖子分享的数量
     *
     * @param changeType 0-增加（默认）1-减少
     * @param itemId     帖子的id
     * @return AddFeedShareCountRspModel
     */
    @GET
    @Path("/ugc/increaseOrReduceShareCount/{changeType}/{itemId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AddFeedShareCountRspModel> increaseOrReduceShareCount(@PathParam("changeType") int changeType, @PathParam("itemId") String itemId) {
        LogUtils.info(TAG, ">>>>>>>Start request ---/ugc/increaseOrReduceShareCount/{changeType}/{itemId}");
        // 先找到这个帖子ugc
        FeedUgc oldUgc = CommunityFactory.findFeedUgcById(itemId);
        if (oldUgc != null) {
            // 这个帖子的ugc存在，开始修改数据库
            FeedUgc newUgc = CommunityFactory.increaseOrReduceCount(oldUgc, CommunityFactory.COUNT_SHARE, changeType);
            if (newUgc != null) {
                AddFeedShareCountRspModel addFeedShareCountRspModel = new AddFeedShareCountRspModel(newUgc);
                return ResponseModel.buildOk(addFeedShareCountRspModel);
            }
        } else {
            // 这个帖子的ugc不存在，这是第一次添加ugc，应该创建一条ugc
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
     * 增加评论数量，不暴露接口给外部，添加一条评论时自动增加
     *
     * @param changeType 操作类型 0-增加（默认）1-减少
     * @param itemId     帖子的id
     * @return FeedUgc
     */
    public FeedUgc increaseOrReduceCommentCount(int changeType, String itemId) {
//        LogUtils.info(TAG, ">>>>>>>Start request ---/ugc/increaseOrReduceCommentCount/{changeType}/{itemId}");
        // 先找到这个帖子ugc
        FeedUgc oldUgc = CommunityFactory.findFeedUgcById(itemId);
        if (oldUgc != null) {
            // 这个帖子的ugc存在，开始修改数据库
            FeedUgc newUgc = CommunityFactory.increaseOrReduceCount(oldUgc, CommunityFactory.COUNT_COMMENT, changeType);
            if (newUgc != null) {
                return newUgc;
            }
        } else {
            // 这个帖子的ugc不存在，这是第一次添加ugc，应该创建一条ugc
            FeedUgc creatUgc = CommunityFactory.creatFeedUgc(itemId, 1, 0, 0);
            if (creatUgc != null) {
                return creatUgc;
            }
        }
        return null;
    }

    /**
     * 变更用户对一条评论的喜欢状态
     *
     * @param commentId 评论的id
     * @param userId    用户的id
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
