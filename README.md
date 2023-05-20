# 						CCBlog开发记录
## 2023/4/25

### 1.完成基础架构搭建

### 2.完成hotArticleList功能，热点博文功能（对查看数进行排序）

## 2023/4/26

### 1.完成博文类别功能

### 2.完成博文首页的分页查询和根据类别的分页查询功能

### 3.完成项目的友链功能

## 2023/5/3

### 1.完成前台系统的登录认证与登录校验过滤器功能

### 2.完成权限不足异常处理器和未登录处理器

### 3.完成统一异常处理器

## 2023/5/4

### 1.完成登录校验过滤功能优化，成功登录之后不需要重复替换SecurityContextHolder的凭证信息。

### 2.完成用户退出登录功能，并清除redis缓存和消除认证

## 2023/5/5

### 1.完成博文的评论数据查询展示，并注入相应的二级子评论和评论的昵称

### 2.解决redis无法可视化展示的bug，以及解决分页插件不生效的问题

## 2023/5/6

### 被项目恶心坏了，调试了几天都过不了SpringSecurity的登录逻辑，项目本身使用自定义的登录逻辑，无法完成用户身份认证，决定停更，更换赛道
## 2023/5/7

### 没想到吧，坚持怎么说，坚持，以下是昨今两天的总更新量

### 1.完成博文的添加评论功能

### 2.完成友链的评论展示和添加评论功能

### 3.完成个人信息展示接口、完成个人信息的修改接口

### 4.完成图片的上传功能，使用七牛图片服务（OSS）

### 5.完成前台用户的注册功能，并使用validation对用户数据进行参数校验

### 6.通过AOP实现自定义注解日志功能

### 7.实现实时更新文章浏览量

#### 7.1项目启动是将文章浏览量全部加载进redis缓存

#### 7.2查看文章时，将redis缓存相应的文章浏览量自增

#### 7.3查看文章时，实时查询redis缓存相应文章的浏览量并返回

#### 7.4启动定时任务，每隔两分半完成一次文章浏览量的数据同步（redis->mysql）

### 8.博客后台系统加入api接口文档功能，可以查看相应接口的功能并测试

## 2023/5/8

### 1.完成后台系统的登录和身份校验功能

### 2.完成后台系统用户身份信息的查询和封装功能

## 2023/5/9

### 1.完成后台系统用户退出功能，并清除认证信息

### 2.完成标签的增删改查功能，并添加分页查询

### 3.完成写博客文章功能，该功能包含以下4个子功能

#### 3.1查询所有标签，完成文章标签的绑定

#### 3.2查询所有分类，完成文章分类的绑定

#### 3.3增加上传图片接口，可在文章中添加图片和文章缩略图

#### 3.4完成上传文章接口，将文章保存至数据库

### 4.完成文章分类数据的execl导出，生成execl文件（目前只开放给管理员）

### 5.对execl导出进行权限控制，采用自定义权限认证

### 6.解决部分遗留的bug问题

## 2023/5/10

### 1.完成后台系统的文章功能开发（CURD）

### 2.完成后台菜单功能开发（CURD）包含子菜单的查询

### 3.完成后台角色功能开发（CURD）包含权限的封装

### 4.完成后台用户功能开发（CURD）包含角色的封装

### 5.完成后台文章分类的功能开发（CURD）

### 6.完成后台友链功能的开发（CURD）

### 7.解决一个前端问题

## 总结：4.25-5.10耗时15天开发完毕，谢谢三更老师的项目

## 2023/5/20 功能更新

### 1.将首页文章数据加入缓存，后续浏览会更加迅速

### 2.将热点文章数据加入缓存，后续浏览会更加迅速

### 3.将文章详情加入缓存，后续浏览会更加迅速

### 4.更改浏览量更新逻辑，不再实时更新，而是全部交给定时任务定时更新，所有的浏览量先存在redis中，定时任务触发后，更新浏览量数据，并清除所有缓存，更新所有文章数据，拿到最新的浏览量

### 5.当后端修改文章数据后，也会触发文章数据的缓存清除

### 6.后续更新预告：加入邮件通知功能，对每日的网站浏览量进行统计，发送浏览数据到绑定的qq邮箱