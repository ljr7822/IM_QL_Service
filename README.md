# IM轻聊App服务器

## 提交日志
- 2021.02.04 第一次提交
    - 初始化 工程
    - 完成 项目基本配置：数据库、基本库依赖
    - 添加 第一个Restful请求接口

- 2021.02.04 第二次提交
    - 完成 用户信息模型表设计、编写
    - 完成 用户关系Model表的设计、编写
    - 完成 消息Model表的设计、编写
    - 完成 消息表和用户表之间的关系
    - 完成 群组表的设计、编写
    - 完成 用户、群组、消息之间的关系设计、编写
    - 完成 消息历史、申请的表设计、编写

- 2021.02.04 第三次提交
    - 导入GsonProvider.java、LocalDateTimeConverter.java文件
    - 添加 工具类Hib.java、TextUtil.java
    - 初步 实现请求接口
    - 封装UserCard 保存用户私密信息

- 2021.02.05 第四次提交
    - 添加UserFactory类 实现注册方法
    - 修改了 Hibernate自动创建表的bug：
    - 将<property name="dialect">org.hibernate.dialect.MySQLDialect</property>修改为<property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>
    - 实现 注册接口逻辑

- 2021.02.05 第五次提交
    - 封装 基础返回Model：ResponseModel.java，统一整个接口Api规范
    - 登录、注册部分接口的返回信息封装
    - 实现 账户登录逻辑
    - 实现 Token的生效机制

- 2021.02.06 第六次提交
    - 完善用户信息更新接口

- 2021.02.06 第七次提交
    - 规定 请求类型：
        - 1.GET 获取信息时使用
        - 2.POST 新建或修改
        - 3.PUT 主要用于数据修改
        - 4.DELETE 删除时使用

- 2021.02.06 第八次提交
    - 实现 对所有的请求进行拦截处理
    - 完善 将拦截器使用到service中

- 2021.02.07 第九次提交
    - 实现 拉取联系人列表的接口
    - 实现 关注某个人的接口

- 2021.02.07 第十次提交
    - 实现 拉取某个人的信息接口

- 2021.02.07 第十一次提交
    - 实现 搜索联系人的接口

- 2021.02.14 第十二次提交
    - 添加 一个推送的具体Model PushModel
    - 添加 申请请求的Card ApplyCard
    - 添加 群信息Model GroupCard
    - 添加 群成员Model GroupMemberCard
    - 添加 消息的卡片Model MessageCard
  
