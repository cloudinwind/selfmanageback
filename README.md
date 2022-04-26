## 1. 论坛模块重新部署

### 1. `entity` 包

该文件夹下是数据库表对应的实体类，其中用户信息相关的
在该文件夹的根目录, 论坛相关的在forum目录

### 2. `dto` 包

扩展的实体对象, 便于获取服务器的返回值并对其进行重新
封装

### 2.1. `exception` 包

自定义抛出的异常(会被dto中的一些类使用)

### 3. `mapper` 包

该文件夹下是持久层接口, 直接对数据库进行操作


### 4. `service` 包

业务层, 进行业务逻辑处理

### 4.1 `util` 包

工具类包, 该文件夹下是一些工具类
- 会被service中的一些类使用
- 同时一些工具类会使用 `dto` 包下的某些类

### 4.2 `enums` 包

该文件夹下是一些枚举类, 这些枚举类会和 某个实体类相对应,
通过枚举的方法定义实体类的属性值(会被service中的一些类用到)

### 4.3 `vo` 包

该文件夹下的类是一些实体类的扩展类, 便于更好处理
请求的返回值


### 5. `controller` 包

表现层, 处理服务器请求

- ForumIndexController: 进行论坛首页定位

- SSOController: 邮箱, 手机号登录注册, 获取验证码

- AuthorizeController: 处理第三方平台登录注册的回调请求

- MailController:  处理邮件发送、获取验证码相关请求

论坛发帖相关(操作question表):

- ForumPublishController: 处理发布论坛帖子相关请求(比如 跳转到发布帖子页面, 完成帖子发布, 完成帖子重新编辑)

- ForumQuestionController: 处理帖子相关请求(比如 查看帖子详细内容, 删除帖子)

论坛说说相关(操作talk表):

- ForumTalkController: 跳转到说说页面(首页), 跳转到某个说说详情页

论坛看看(新闻相关):

- ForumNewsController: 跳转到看看首页, 查询所有新闻, 跳转到某个新闻详情页

用户个人信息管理相关:

- ForumProfileController: 

    - 消息管理(查询通知该用户的所有消息(对应notification表), 跳转到消息管理页面 user/message.html), 
      比如其他用户对该用户发的贴子或评论点赞/收藏, 该用户会收到消息
    > 通知的消息不仅仅只有点赞, 还有评论
      
    - 帖子管理, user/p/{action}: 
      user/p/myPosts: 查询用户发布的所有帖子, 跳转到帖子管理页面 user/p.html
      user/p/likes: 查询用户收藏的所有帖子, 跳转到帖子管理页面 user/p.html 
    
    - 相关设置, user/set/{action}
       user/set/account: 跳转到账户中心页面 user/account, 换绑邮箱、手机号、第三方账户, 更换密码
       user/set/info: 跳转到基本设置页面 user/set, 填写个人资料、更换头像, 查看积分方式、所属用户组
       
    
用户管理:
- UserController:

    - 用于处理和用户相关的请求
    - user/{userId} : 跳转到用户首页
    
    - user/set/{action}
      user/set/avatar : 更换头像请求 post
      user/set/info : 更改个人资料请求 post
      
      
- ForumNotificationController:

用于处理消息请求, 包括一键已读, 一键删除, 单个删除

- FileController:  处理上传文件的请求, 比如上传图片(会被发帖, 修改用户头像使用到)  

- CustomizeErrorController: 自定义处理异常请求处理



### 5.1 `cache` 包

该包下的类都是一些缓存类, 用来存储一些缓存信息(比如登录信息等),
会被controller包下的一些类使用

### 5.2 `provider` 包

该包下的类对调用第三方平台的api返回的结果进行封装

### 5.3 `api` 包

该包下的类定义了一些操作实体类和用户请求的接口

- SsoApi: 用户手机号、邮箱登录, 注册, 获取验证码

- TalkApi:  新增说说, 查询说说, 删除说说, 重新设置说说

### 5.3.1 `annotion` 包

该包下自定义了一些注解, 这些注解会被 api 包下的一些类用到

### 6. `constant` 包

改包下的类用于定义分页的属性值

### 7. `interceptor` 包

过滤器, 每个请求都会先经过过滤器，然后才会调用相对应的方法

### 8. `schedule` 包

该包中的文件设置定时器, 对一些内容进行定时更新


### 前端页面

**common** : 
- 该文件夹下是一些在各个页面出现比较多的部分
- 比如：头部，尾部

**p**: :

该文件夹下是一些和帖子操作相关的页面

- add.html      发布帖子页面  (帖子内容使用了百度云审核)
(添加视频和图片出现了问题, 待解决)

- detail.html   查看帖子内容页面

和该包相关的请求处理Controller有: ForumPublishController, ForumQuestionController

**talk** :

该文件夹下是一些和说说操作相关的页面

- index.html    说说首页 (可以发送个人说说， 使用了ajax异步请求api/talk/list 以列表形式显示所有的说说)

- detail.html   查看某个说说的具体详情页面(可以为该说说添加评论 和帖子详情页面类似)

和该包相关的请求处理类有: ForumTalkController, TalkApi

**news** :

该文件夹下是一些和看看(新闻)操作相关的页面

- index.html

- detail.html


### 数据库

**user表**

存储用户id和其他平台账号的关联, 同时存储name, email, phone, password

**user_info 表**

存储用户的个人信息, 包括个人介绍、生日、性别、工作、住址

**user_account 表**

存储用户的一些等级, 所属组信息

group_id为21: 表示该用户是管理员

**question 表**

所有的帖子都对应talk表

**talk 表**

所有的说说都对应talk表

**news 表**

所有的看看都对应news表

**thumb 表**

存储用户喜欢的帖子, 说说, 以及评论(通过type来区分喜欢的种类)

- 凡是和 ==收藏/点赞== 相关的操作都会触发对该表的操作


==帖子, 说说, 评论通过type来区别==
enums包下的LikeTypeEnum和CommentTypeEnum

## 其他

MyBatisGenerator: https://www.jianshu.com/p/edb8428fa9df

