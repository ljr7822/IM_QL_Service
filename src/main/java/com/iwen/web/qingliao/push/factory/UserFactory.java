package com.iwen.web.qingliao.push.factory;

import com.google.common.base.Strings;
import com.iwen.web.qingliao.push.bean.db.User;
import com.iwen.web.qingliao.push.bean.db.UserFollow;
import com.iwen.web.qingliao.push.utils.Hib;
import com.iwen.web.qingliao.push.utils.TextUtil;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @Author: iwen大大怪
 * @DateTime: 12-2 002 16:08
 */
public class UserFactory {

    /**
     * 通过Token找到user
     *
     * @param token 传入的手机号
     * @return 一个用户信息
     */
    public static User findByToken(String token) {
        return Hib.query(session -> (User) session
                .createQuery("from User where token=:inToken")
                .setParameter("inToken", token)
                .uniqueResult());
    }

    /**
     * 通过phone找到user
     *
     * @param phone 传入的手机号
     * @return 一个用户信息
     */
    public static User findByPhone(String phone) {
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:inPhone")
                .setParameter("inPhone", phone)
                .uniqueResult());
    }

    /**
     * 通过name找到user
     *
     * @param name 传入的用户名
     * @return 一个用户信息
     */
    public static User findByName(String name) {
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:inName")
                .setParameter("inName", name)
                .uniqueResult());
    }

    /**
     * 通过id找到user
     *
     * @param id 传入的id
     * @return 一个用户信息
     */
    public static User findById(String id) {
        // 通过id查询
        return Hib.query(session -> session.get(User.class, id));
    }

    /**
     * 更新用户信息到数据库
     *
     * @param user User
     * @return User
     */
    public static User update(User user) {
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 给当前账户绑定pushId
     *
     * @param user   自己的user
     * @param pushId 自己设备的pushId
     * @return User
     */
    public static User bindPushId(User user, String pushId) {
        if (Strings.isNullOrEmpty(pushId)) {
            return null;
        }
        // 1.查找是否有其他设备绑定，取消绑定，避免推送
        Hib.queryOnly(session -> {
            @SuppressWarnings("unchecked")
            List<User> userList = (List<User>) session
                    .createQuery("from User where lower(pushId)=:pushId and id!=:userId")
                    .setParameter("pushId", pushId.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();

            for (User u : userList) {
                // 跟新为null
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });
        // 2.
        if (pushId.equalsIgnoreCase(user.getPushId())) {
            // 如果当前需要绑定的设备id，之前已经绑定过了，那么不需要额外绑定
            return user;
        } else {
            // 如果我当前账户之前的设备ID，和需要绑定的不同，那么需要单点登录，让之前的设备退出账户，给之前的设备推送一条退出消息
            if (Strings.isNullOrEmpty(user.getPushId())) {
                // 推送一个退出消息
                PushFactory.pushLogout(user,user.getPushId());
            }
            // 更新新的设备id
            user.setPushId(pushId);
            return update(user);
        }
    }

    /**
     * 使用账户和密码进行登录
     * 通过账户和密码查询账户
     *
     * @param account  账户
     * @param password 密码
     * @return 一个用户
     */
    public static User login(String account, String password) {
        final String accountStr = account.trim();
        final String encodePassword = encodePassword(password);
        // 查询
        User user = Hib.query(session -> (User) session
                .createQuery("from User where phone=:phone and password=:password")
                .setParameter("phone", accountStr)
                .setParameter("password", encodePassword)
                .uniqueResult());
        if (user != null) {
            // 对User进行登录
            user = login(user);
        }
        return user;
    }

    /**
     * 用户注册
     * 注册的操作需要写入数据库，并返回数据库中的User信息
     *
     * @param account  账户
     * @param password 密码
     * @param name     用户名
     * @return User
     */
    public static User register(String account, String password, String name) {
        // 去除账户中的首尾空格
        account = account.trim();
        // 密码使用MD5加密
        password = encodePassword(password);
        User user = createUser(account, password, name);
        // 如果创建成功
        if (user != null) {
            user = login(user);
        }
        return user;
    }

    /**
     * 登录
     * 本质就是对token进行操作
     *
     * @param user 用户信息
     * @return 一个用户
     */
    private static User login(User user) {
        // 使用一个随机的uuid充当token
        String newToken = UUID.randomUUID().toString();
        // 进行一次base64格式化
        newToken = TextUtil.encodeBase64(newToken);
        user.setToken(newToken);
        return update(user);
    }

    /**
     * 创建一个用户
     *
     * @param account  手机号
     * @param password 密码
     * @param name     姓名
     * @return 用户
     */
    private static User createUser(String account, String password, String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhone(account);
        // 数据库存储
        return Hib.query(session -> {
            session.save(user);
            return user;
        });
    }

    /**
     * 密码MD5加密
     *
     * @param password 用户输入原文
     * @return 加密结果密文
     */
    private static String encodePassword(String password) {
        // 去除首尾空格
        password = password.trim();
        // 进行MD5加密
        password = TextUtil.getMD5(password);
        // 在进行一次Base64加密
        return TextUtil.encodeBase64(password);
    }

    /**
     * 数据库查询 获取联系人列表
     *
     * @param self User 用户自己
     * @return 自己的用户列表
     */
    public static List<User> contacts(User self) {
        return Hib.query(session -> {
            // 重新加载一次用户信息到self中，和当前的session绑定
            session.load(self, self.getId());
            // 获取我关注的人
            Set<UserFollow> flow = self.getFollowing();
            // 使用java8简写方法
            return flow.stream()
                    .map(UserFollow::getTarget)
                    .collect(Collectors.toList());
        });
    }

    /**
     * 关注人的操作
     *
     * @param origin 发起关注人
     * @param target 被关注人
     * @param alias  备注
     * @return 被关注人的信息
     */
    public static User follow(final User origin, final User target, final String alias) {
        UserFollow follow = getUserFollow(origin, target);
        if (follow != null) {
            // 已经关注
            return follow.getTarget();
        }

        return Hib.query(session -> {
            // 想要重新操作懒加载的数据，需要重新load一次
            session.load(origin, origin.getId());
            session.load(target, target.getId());
            // 我关注人的时候，同时他也关注我
            // 所有需要添加两条UserFollow数据
            UserFollow originFollow = new UserFollow();
            originFollow.setOrigin(origin);
            originFollow.setTarget(target);
            // 备注是我对它的备注，他对我默认没有备注
            originFollow.setAlias(alias);

            UserFollow targetFollow = new UserFollow();
            targetFollow.setOrigin(target);
            targetFollow.setTarget(origin);

            // 保存到数据库
            session.save(originFollow);
            session.save(targetFollow);

            return target;
        });
    }

    /**
     * 查询两人是否已经关注
     *
     * @param origin 发起人
     * @param target 被关注人
     * @return 中间类UserFollow
     */
    public static UserFollow getUserFollow(final User origin, final User target) {
        return Hib.query(session -> (UserFollow) session.createQuery(
                "from UserFollow where originId=:originId and targetId=:targetId")
                .setParameter("originId", origin.getId())
                .setParameter("targetId", target.getId())
                .setMaxResults(1)
                // 查询一条数据
                .uniqueResult());
    }

    /**
     * 搜索联系人的实现
     *  and portrait is not null and desc is not null
     *
     * @param name 查询的名字
     * @return 用户列表，如果name为空，则返回最近的用户
     */
    @SuppressWarnings("unchecked")
    public static List<User> search(String name) {
        if (Strings.isNullOrEmpty(name)) {
            name = "";
        }
        final String searchName = "%" + name + "%";

        return Hib.query(session -> {
            // 查询条件，name忽略大小写，使用like模糊查询，头像和描述必须完善才可以查询
            return (List<User>) session.createQuery("from User where lower(name) like :name and portrait is not null")
                    .setParameter("name", searchName)
                    .setMaxResults(20) // 最多返回20条
                    .list();
        });
    }
}
