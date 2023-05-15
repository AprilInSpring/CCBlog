/*
 Navicat Premium Data Transfer

 Source Server         : cc
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : ccblog

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 10/05/2023 20:24:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cc_article
-- ----------------------------
DROP TABLE IF EXISTS `cc_article`;
CREATE TABLE `cc_article`  (
  `id` bigint(200) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `summary` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章摘要',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '所属分类id',
  `thumbnail` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图',
  `is_top` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否置顶（0否，1是）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态（0已发布，1草稿）',
  `view_count` bigint(200) NULL DEFAULT 0 COMMENT '访问量',
  `is_comment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否允许评论 1是，0否',
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_article
-- ----------------------------
INSERT INTO `cc_article` VALUES (1, 'SpringSecurity从入门到精通', '## 课程介绍\n![image20211219121555979.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/e7131718e9e64faeaf3fe16404186eb4.png)\n\n## 0. 简介1\n\n​	**Spring Security** 是 Spring 家族中的一个安全管理框架。相比与另外一个安全框架**Shiro**，它提供了更丰富的功能，社区资源也比Shiro丰富。\n\n​	一般来说中大型的项目都是使用**SpringSecurity** 来做安全框架。小项目有Shiro的比较多，因为相比与SpringSecurity，Shiro的上手更加的简单。\n\n​	 一般Web应用的需要进行**认证**和**授权**。\n\n​		**认证：验证当前访问系统的是不是本系统的用户，并且要确认具体是哪个用户**\n\n​		**授权：经过认证后判断当前用户是否有权限进行某个操作**\n\n​	而认证和授权也是SpringSecurity作为安全框架的核心功能。\n\n\n\n## 1. 快速入门\n\n### 1.1 准备工作\n\n​	我们先要搭建一个简单的SpringBoot工程\n\n① 设置父工程 添加依赖\n\n~~~~\n    <parent>\n        <groupId>org.springframework.boot</groupId>\n        <artifactId>spring-boot-starter-parent</artifactId>\n        <version>2.5.0</version>\n    </parent>\n    <dependencies>\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-web</artifactId>\n        </dependency>\n        <dependency>\n            <groupId>org.projectlombok</groupId>\n            <artifactId>lombok</artifactId>\n            <optional>true</optional>\n        </dependency>\n    </dependencies>\n~~~~\n\n② 创建启动类\n\n~~~~\n@SpringBootApplication\npublic class SecurityApplication {\n\n    public static void main(String[] args) {\n        SpringApplication.run(SecurityApplication.class,args);\n    }\n}\n\n~~~~\n\n③ 创建Controller\n\n~~~~java\n\nimport org.springframework.web.bind.annotation.RequestMapping;\nimport org.springframework.web.bind.annotation.RestController;\n\n@RestController\npublic class HelloController {\n\n    @RequestMapping(\"/hello\")\n    public String hello(){\n        return \"hello\";\n    }\n}\n\n~~~~\n\n\n\n### 1.2 引入SpringSecurity\n\n​	在SpringBoot项目中使用SpringSecurity我们只需要引入依赖即可实现入门案例。\n\n~~~~xml\n        <dependency>\n            <groupId>org.springframework.boot</groupId>\n            <artifactId>spring-boot-starter-security</artifactId>\n        </dependency>\n~~~~\n\n​	引入依赖后我们在尝试去访问之前的接口就会自动跳转到一个SpringSecurity的默认登陆页面，默认用户名是user,密码会输出在控制台。\n\n​	必须登陆之后才能对接口进行访问。\n\n\n\n## 2. 认证\n\n### 2.1 登陆校验流程\n![image20211215094003288.png](https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/414a87eeed344828b5b00ffa80178958.png)', 'SpringSecurity框架教程-Spring Security+JWT实现项目级前端分离认证授权', 1, 'https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png', '1', '0', 120, '0', NULL, '2022-01-23 23:20:11', NULL, NULL, 0);
INSERT INTO `cc_article` VALUES (5, 'cc1', 'cc1', 'cc1', 1, '', '1', '0', 50, '0', NULL, '2022-01-17 14:58:37', 1, '2023-05-10 10:05:39', 0);
INSERT INTO `cc_article` VALUES (8, 'ccc', '# Bug记录表\n\n## 2023/4/26 9:52\n\nBeanUtils类中的copyProperties()方法无法完成属性复制\n\n问题查询\n\n1.类型是否一样\n2.有没有对应的getset方法，能不能访问对应的getset方法\n3.传入要复制的对象resutl是否被实例化出来了\n\n问题标记\n\n原博文列表类由于是代码工具生成的，没有相应的lombok注解，没有get/set方法，导致反射失败\n\n问题解决\n\n在原博文列表类添加相应的注解\n\n\n\n## 2023/5/3 12:05\n\n项目每次启动都会报错，错误信息为mybatisplus的分页插件已经注册，不能重复注册一样的bean对象\n\n问题标记\n\n直接在target文件删除相应的class文件，但是治标不治本，后续出现新的问题，删除后分页插件不起作用\n\n问题解决\n\n@Configuration注解也会生成bean对象，放到spring容器进行管理，导致配置类和分页插件bean的id重复，无法注入相同id的对象，直接修改配置类的名称，解决bug\n\n\n\n## 2023/5/3 17:43\n\nredis数据库出现问题，使用命令或者可视化工具无法获取键的值，但是程序可以正确运行\n\n错误截图\n\n![image-20230503174518863](C:\\Users\\PC\\AppData\\Roaming\\Typora\\typora-user-images\\image-20230503174518863.png)\n\n问题标记\n\n属于redis的序列化问题，项目本身使用fastjson进行序列化，项目可以正常运行，但是可视化工具无法查看\n\n问题解决\n\n重写redis的序列化方法，改成redis自带的GenericJackson2JsonRed，isSerializer()序列化，问题解决\n\n## 2023/5/6 21:30\n\n使用@ConfigurationProperties注解无法完成配置文件的属性注入，尚未解决\n\n问题解决\n\n使用@Value(${xxx})代替上述注解完成配置文件的属性注入\n\n## 2023/5/7 20:47\n\nswagger2的api接口文档乱码，尚未解决\n\n问题标记\n\n需要加深swagger2的功能学习，方便工作写接口测试文档\n\n## 2023/5/8 09:30\n\n实体类字段与数据库表字段不一致，存在多余的属性，使用@TableField(exist = false)进行排除\n\n或者建立一个新的实体类，与数据库进行对接\n\n## 2023/5/8 09:32\n\n在查询数据库时，查不到相应的正常字段\n\n问题标记\n\n使用多表左连接查询的时候，某些正常的数据，无法连表，导致正常的数据被过滤掉\n\n问题解决\n\n取消多表联查，或者使用需要数据的表作为主表进行连接查询，这样主表的数据就会全部保留\n\n## 2023/5/8 10:04\n\n实体类的set方法，无法返回实体类对象\n\n问题解决\n\n可以手动修改实体类的set方法，使其返回this对象\n\n使用Lombok注解\n\n```java\n//改变set方法的返回值\n@Accessors(chain = true)\n```\n\n## 2023/5/9 18:44\n\n添加博客文章的时候，无法维护相应的博客标签表\n\n问题标记\n\n博客标签对象添加功能正常，由于先维护博客标签表，再添加博客，导致无法获取到添加博客的主键，无法进行主键回填\n\n问题解决\n\n先添加博客，再维护博客表（通过主键回填功能）\n\n\n\n', 'cc', 1, 'http://ru8h77emb.hn-bkt.clouddn.com/2023/05/09/095b7eb52c034e9f81c07506d1244af4.png', '1', '0', 0, '0', 1, '2023-05-09 21:28:52', 1, '2023-05-10 10:01:32', 0);

-- ----------------------------
-- Table structure for cc_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `cc_article_tag`;
CREATE TABLE `cc_article_tag`  (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `tag_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '标签id',
  PRIMARY KEY (`article_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_article_tag
-- ----------------------------
INSERT INTO `cc_article_tag` VALUES (1, 4);
INSERT INTO `cc_article_tag` VALUES (2, 1);
INSERT INTO `cc_article_tag` VALUES (2, 4);
INSERT INTO `cc_article_tag` VALUES (3, 4);
INSERT INTO `cc_article_tag` VALUES (3, 5);
INSERT INTO `cc_article_tag` VALUES (5, 1);
INSERT INTO `cc_article_tag` VALUES (5, 4);
INSERT INTO `cc_article_tag` VALUES (5, 6);
INSERT INTO `cc_article_tag` VALUES (8, 1);
INSERT INTO `cc_article_tag` VALUES (8, 4);
INSERT INTO `cc_article_tag` VALUES (8, 6);

-- ----------------------------
-- Table structure for cc_category
-- ----------------------------
DROP TABLE IF EXISTS `cc_category`;
CREATE TABLE `cc_category`  (
  `id` bigint(200) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `pid` bigint(200) NULL DEFAULT -1 COMMENT '父分类id，如果没有父分类为-1',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态0:正常,1禁用',
  `create_by` bigint(200) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(200) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_category
-- ----------------------------
INSERT INTO `cc_category` VALUES (1, 'java', -1, 'wsd', '0', NULL, NULL, NULL, NULL, 0);
INSERT INTO `cc_category` VALUES (2, 'PHP', -1, 'wsd', '0', NULL, NULL, NULL, NULL, 0);
INSERT INTO `cc_category` VALUES (3, 'mysql', -1, '数据库', '0', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for cc_comment
-- ----------------------------
DROP TABLE IF EXISTS `cc_comment`;
CREATE TABLE `cc_comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '评论类型（0代表文章评论，1代表友链评论）',
  `article_id` bigint(20) NULL DEFAULT NULL COMMENT '文章id',
  `root_id` bigint(20) NULL DEFAULT -1 COMMENT '根评论id',
  `content` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `to_comment_user_id` bigint(20) NULL DEFAULT -1 COMMENT '所回复的目标评论的userid',
  `to_comment_id` bigint(20) NULL DEFAULT -1 COMMENT '回复目标评论id',
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_comment
-- ----------------------------
INSERT INTO `cc_comment` VALUES (1, '0', 1, -1, 'asS', -1, -1, 1, '2022-01-29 07:59:22', 1, '2022-01-29 07:59:22', 0);
INSERT INTO `cc_comment` VALUES (2, '0', 1, -1, '[哈哈]SDAS', -1, -1, 1, '2022-01-29 08:01:24', 1, '2022-01-29 08:01:24', 0);
INSERT INTO `cc_comment` VALUES (3, '0', 1, -1, '是大多数', -1, -1, 1, '2022-01-29 16:07:24', 1, '2022-01-29 16:07:24', 0);
INSERT INTO `cc_comment` VALUES (4, '0', 1, -1, '撒大声地', -1, -1, 1, '2022-01-29 16:12:09', 1, '2022-01-29 16:12:09', 0);
INSERT INTO `cc_comment` VALUES (9, '0', 1, 2, '你说什么', 1, 2, 1, '2022-01-29 22:18:40', 1, '2022-01-29 22:18:40', 0);
INSERT INTO `cc_comment` VALUES (10, '0', 1, 2, '哈哈哈哈[哈哈]', 1, 9, 1, '2022-01-29 22:29:15', 1, '2022-01-29 22:29:15', 0);
INSERT INTO `cc_comment` VALUES (11, '0', 1, 2, 'we全文', 1, 10, 3, '2022-01-29 22:29:55', 1, '2022-01-29 22:29:55', 0);
INSERT INTO `cc_comment` VALUES (16, '0', 1, 2, '3333', 1, 11, 1, '2022-01-29 22:34:47', 1, '2022-01-29 22:34:47', 0);
INSERT INTO `cc_comment` VALUES (17, '0', 1, 2, '回复weqedadsd', 3, 11, 1, '2022-01-29 22:38:00', 1, '2022-01-29 22:38:00', 0);
INSERT INTO `cc_comment` VALUES (18, '0', 1, -1, 'sdasd', -1, -1, 1, '2022-01-29 23:18:19', 1, '2022-01-29 23:18:19', 0);
INSERT INTO `cc_comment` VALUES (19, '0', 1, -1, '111', -1, -1, 1, '2022-01-29 23:22:23', 1, '2022-01-29 23:22:23', 0);
INSERT INTO `cc_comment` VALUES (20, '0', 1, 1, '你说啥？', 1, 1, 1, '2022-01-30 10:06:21', 1, '2022-01-30 10:06:21', 0);
INSERT INTO `cc_comment` VALUES (21, '0', 1, -1, '友链添加个呗', -1, -1, 1, '2022-01-30 10:06:50', 1, '2022-01-30 10:06:50', 0);
INSERT INTO `cc_comment` VALUES (22, '1', 1, -1, '友链评论2', -1, -1, 1, '2022-01-30 10:08:28', 1, '2022-01-30 10:08:28', 0);
INSERT INTO `cc_comment` VALUES (23, '1', 1, 22, '回复友链评论3', 1, 22, 1, '2022-01-30 10:08:50', 1, '2022-01-30 10:08:50', 0);
INSERT INTO `cc_comment` VALUES (24, '1', 1, -1, '友链评论4444', -1, -1, 1, '2022-01-30 10:09:03', 1, '2022-01-30 10:09:03', 0);
INSERT INTO `cc_comment` VALUES (25, '1', 1, 22, '收到的', 1, 22, 1, '2022-01-30 10:13:28', 1, '2022-01-30 10:13:28', 0);
INSERT INTO `cc_comment` VALUES (26, '0', 1, -1, 'sda', -1, -1, 1, '2022-01-30 10:39:05', 1, '2022-01-30 10:39:05', 0);
INSERT INTO `cc_comment` VALUES (27, '0', 1, 1, '说你咋地', 1, 20, 14787164048662, '2022-01-30 17:19:30', 14787164048662, '2022-01-30 17:19:30', 0);
INSERT INTO `cc_comment` VALUES (28, '0', 1, 1, 'sdad', 1, 1, 14787164048662, '2022-01-31 11:11:20', 14787164048662, '2022-01-31 11:11:20', 0);
INSERT INTO `cc_comment` VALUES (29, '0', 1, -1, '你说是的ad', -1, -1, 14787164048662, '2022-01-31 14:10:11', 14787164048662, '2022-01-31 14:10:11', 0);
INSERT INTO `cc_comment` VALUES (30, '0', 1, 1, '撒大声地', 1, 1, 14787164048662, '2022-01-31 20:19:18', 14787164048662, '2022-01-31 20:19:18', 0);
INSERT INTO `cc_comment` VALUES (31, '0', 1, -1, 'cc', -1, -1, 1, '2023-05-05 15:15:17', 1, '2023-05-05 15:15:17', 0);
INSERT INTO `cc_comment` VALUES (32, '0', 1, -1, '泰酷辣', -1, -1, 1, '2023-05-05 15:16:06', 1, '2023-05-05 15:16:06', 0);
INSERT INTO `cc_comment` VALUES (38, '0', 1, -1, '求求了，给我过吧', -1, -1, 1, '2023-05-05 19:11:59', 1, '2023-05-05 19:11:59', 0);
INSERT INTO `cc_comment` VALUES (41, '0', 1, -1, 'cc', -1, -1, 1, '2023-05-05 19:26:53', 1, '2023-05-05 19:26:53', 0);
INSERT INTO `cc_comment` VALUES (42, '0', 1, -1, 'context我草泥马\n', -1, -1, 1, '2023-05-05 19:29:34', 1, '2023-05-05 19:29:34', 0);
INSERT INTO `cc_comment` VALUES (43, '0', 1, 42, '太对了', 1, 42, 1, '2023-05-05 19:31:10', 1, '2023-05-05 19:31:10', 0);
INSERT INTO `cc_comment` VALUES (44, '1', 1, -1, 'dadada', -1, -1, 1, '2023-05-05 19:41:31', 1, '2023-05-05 19:41:31', 0);
INSERT INTO `cc_comment` VALUES (47, '0', 1, -1, '傻逼项目', -1, -1, 1, '2023-05-06 17:02:10', 1, '2023-05-06 17:02:10', 0);
INSERT INTO `cc_comment` VALUES (48, '0', 1, 47, '说的太对辣\n[微笑]', 1, 47, 1, '2023-05-06 17:03:41', 1, '2023-05-06 17:03:41', 0);
INSERT INTO `cc_comment` VALUES (49, '0', 1, 47, '我都无语了[生病]', 1, 48, 1, '2023-05-06 17:03:52', 1, '2023-05-06 17:03:52', 0);
INSERT INTO `cc_comment` VALUES (53, '0', 1, -1, 'cc', -1, -1, 1, '2023-05-06 17:25:52', 1, '2023-05-06 17:25:52', 0);
INSERT INTO `cc_comment` VALUES (54, '1', 1, -1, 'cc', -1, -1, 1, '2023-05-06 17:26:01', 1, '2023-05-06 17:26:01', 0);
INSERT INTO `cc_comment` VALUES (55, '1', 1, 54, 'cc', 1, 54, 1, '2023-05-06 17:26:04', 1, '2023-05-06 17:26:04', 0);
INSERT INTO `cc_comment` VALUES (56, '0', 1, -1, '111', -1, -1, 1, '2023-05-06 17:44:03', 1, '2023-05-06 17:44:03', 0);
INSERT INTO `cc_comment` VALUES (57, '1', 1, -1, '11', -1, -1, 1, '2023-05-06 17:44:20', 1, '2023-05-06 17:44:20', 0);

-- ----------------------------
-- Table structure for cc_link
-- ----------------------------
DROP TABLE IF EXISTS `cc_link`;
CREATE TABLE `cc_link`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `logo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '2' COMMENT '审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)',
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '友链' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_link
-- ----------------------------
INSERT INTO `cc_link` VALUES (1, 'sda', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'sda', 'https://www.baidu.com', '0', NULL, '2022-01-13 08:25:47', NULL, '2022-01-13 08:36:14', 0);
INSERT INTO `cc_link` VALUES (2, 'sda', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'dada', 'https://www.qq.com', '0', NULL, '2022-01-13 09:06:10', NULL, '2022-01-13 09:07:09', 0);
INSERT INTO `cc_link` VALUES (3, 'sa', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fn1.itc.cn%2Fimg8%2Fwb%2Frecom%2F2016%2F05%2F10%2F146286696706220328.PNG&refer=http%3A%2F%2Fn1.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1646205529&t=f942665181eb9b0685db7a6f59d59975', 'da', 'https://www.taobao.com', '0', NULL, '2022-01-13 09:23:01', NULL, '2022-01-13 09:23:01', 0);
INSERT INTO `cc_link` VALUES (4, 'ccc', 'cc', 'ccc', 'cc', '2', NULL, NULL, NULL, NULL, 1);
INSERT INTO `cc_link` VALUES (5, 'cc', 'cc', 'cc', 'cc', '0', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for cc_menu
-- ----------------------------
DROP TABLE IF EXISTS `cc_menu`;
CREATE TABLE `cc_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2032 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_menu
-- ----------------------------
INSERT INTO `cc_menu` VALUES (1, '系统管理', 0, 1, 'system', NULL, 1, 'M', '0', '0', '', 'system', 0, '2021-11-12 10:46:19', 0, NULL, '系统管理目录', '0');
INSERT INTO `cc_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', 1, 'C', '0', '0', 'system:user:list', 'user', 0, '2021-11-12 10:46:19', 1, '2022-07-31 15:47:58', '用户管理菜单', '0');
INSERT INTO `cc_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', 1, 'C', '0', '0', 'system:role:list', 'peoples', 0, '2021-11-12 10:46:19', 0, NULL, '角色管理菜单', '0');
INSERT INTO `cc_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', 1, 'C', '0', '0', 'system:menu:list', 'tree-table', 0, '2021-11-12 10:46:19', 0, NULL, '菜单管理菜单', '0');
INSERT INTO `cc_menu` VALUES (1001, '用户查询', 100, 1, '', '', 1, 'F', '0', '0', 'system:user:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1002, '用户新增', 100, 2, '', '', 1, 'F', '0', '0', 'system:user:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1003, '用户修改', 100, 3, '', '', 1, 'F', '0', '0', 'system:user:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1004, '用户删除', 100, 4, '', '', 1, 'F', '0', '0', 'system:user:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1005, '用户导出', 100, 5, '', '', 1, 'F', '0', '0', 'system:user:export', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1006, '用户导入', 100, 6, '', '', 1, 'F', '0', '0', 'system:user:import', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1007, '重置密码', 100, 7, '', '', 1, 'F', '0', '0', 'system:user:resetPwd', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1008, '角色查询', 101, 1, '', '', 1, 'F', '0', '0', 'system:role:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1009, '角色新增', 101, 2, '', '', 1, 'F', '0', '0', 'system:role:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1010, '角色修改', 101, 3, '', '', 1, 'F', '0', '0', 'system:role:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1011, '角色删除', 101, 4, '', '', 1, 'F', '0', '0', 'system:role:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1012, '角色导出', 101, 5, '', '', 1, 'F', '0', '0', 'system:role:export', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1013, '菜单查询', 102, 1, '', '', 1, 'F', '0', '0', 'system:menu:query', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1014, '菜单新增', 102, 2, '', '', 1, 'F', '0', '0', 'system:menu:add', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1015, '菜单修改', 102, 3, '', '', 1, 'F', '0', '0', 'system:menu:edit', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (1016, '菜单删除', 102, 4, '', '', 1, 'F', '0', '0', 'system:menu:remove', '#', 0, '2021-11-12 10:46:19', 0, NULL, '', '0');
INSERT INTO `cc_menu` VALUES (2017, '内容管理', 0, 4, 'content', NULL, 1, 'M', '0', '0', NULL, 'table', NULL, '2022-01-08 02:44:38', 1, '2022-07-31 12:34:23', '', '0');
INSERT INTO `cc_menu` VALUES (2018, '分类管理', 2017, 1, 'category', 'content/category/index', 1, 'C', '0', '0', 'content:category:list', 'example', NULL, '2022-01-08 02:51:45', NULL, '2022-01-08 02:51:45', '', '0');
INSERT INTO `cc_menu` VALUES (2019, '文章管理', 2017, 0, 'article', 'content/article/index', 1, 'C', '0', '0', 'content:article:list', 'build', NULL, '2022-01-08 02:53:10', NULL, '2022-01-08 02:53:10', '', '0');
INSERT INTO `cc_menu` VALUES (2021, '标签管理', 2017, 6, 'tag', 'content/tag/index', 1, 'C', '0', '0', 'content:tag:index', 'button', NULL, '2022-01-08 02:55:37', NULL, '2022-01-08 02:55:50', '', '0');
INSERT INTO `cc_menu` VALUES (2022, '友链管理', 2017, 4, 'link', 'content/link/index', 1, 'C', '0', '0', 'content:link:list', '404', NULL, '2022-01-08 02:56:50', NULL, '2022-01-08 02:56:50', '', '0');
INSERT INTO `cc_menu` VALUES (2023, '写博文', 0, 0, 'write', 'content/article/write/index', 1, 'C', '0', '0', 'content:article:writer', 'build', NULL, '2022-01-08 03:39:58', 1, '2022-07-31 22:07:05', '', '0');
INSERT INTO `cc_menu` VALUES (2024, '友链新增', 2022, 0, '', NULL, 1, 'F', '0', '0', 'content:link:add', '#', NULL, '2022-01-16 07:59:17', NULL, '2022-01-16 07:59:17', '', '0');
INSERT INTO `cc_menu` VALUES (2025, '友链修改', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:edit', '#', NULL, '2022-01-16 07:59:44', NULL, '2022-01-16 07:59:44', '', '0');
INSERT INTO `cc_menu` VALUES (2026, '友链删除', 2022, 1, '', NULL, 1, 'F', '0', '0', 'content:link:remove', '#', NULL, '2022-01-16 08:00:05', NULL, '2022-01-16 08:00:05', '', '0');
INSERT INTO `cc_menu` VALUES (2027, '友链查询', 2022, 2, '', NULL, 1, 'F', '0', '0', 'content:link:query', '#', NULL, '2022-01-16 08:04:09', NULL, '2022-01-16 08:04:09', '', '0');
INSERT INTO `cc_menu` VALUES (2028, '导出分类', 2018, 1, '', NULL, 1, 'F', '0', '0', 'content:category:export', '#', NULL, '2022-01-21 07:06:59', NULL, '2022-01-21 07:06:59', '', '0');
INSERT INTO `cc_menu` VALUES (2029, 'cc', 0, 4, 'cc', NULL, 1, 'M', '0', '0', NULL, 'eye', 1, '2023-05-10 10:43:27', 1, '2023-05-10 10:43:27', '', '1');
INSERT INTO `cc_menu` VALUES (2030, 'ccc', 0, 5, 'cc', NULL, 1, 'M', '0', '0', NULL, '404', 1, '2023-05-10 14:12:13', 1, '2023-05-10 14:12:13', '', '1');
INSERT INTO `cc_menu` VALUES (2031, '测试cc', 2017, 5, 'ccc', NULL, 1, 'M', '0', '0', NULL, '404', 1, '2023-05-10 14:17:15', 1, '2023-05-10 14:35:17', '', '1');

-- ----------------------------
-- Table structure for cc_role
-- ----------------------------
DROP TABLE IF EXISTS `cc_role`;
CREATE TABLE `cc_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 1代表删除）',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_role
-- ----------------------------
INSERT INTO `cc_role` VALUES (1, '超级管理员', 'admin', 1, '0', '0', 0, '2021-11-12 10:46:19', 0, NULL, '超级管理员');
INSERT INTO `cc_role` VALUES (2, '普通角色', 'common', 2, '0', '0', 0, '2021-11-12 10:46:19', 0, '2022-01-01 22:32:58', '普通角色');
INSERT INTO `cc_role` VALUES (11, '嘎嘎嘎', 'aggag', 5, '0', '0', NULL, '2022-01-06 14:07:40', NULL, '2022-01-07 03:48:48', '嘎嘎嘎');
INSERT INTO `cc_role` VALUES (12, '友链审核员', 'link', 1, '0', '0', NULL, '2022-01-16 06:49:30', NULL, '2022-01-16 08:05:09', NULL);

-- ----------------------------
-- Table structure for cc_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `cc_role_menu`;
CREATE TABLE `cc_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_role_menu
-- ----------------------------
INSERT INTO `cc_role_menu` VALUES (0, 0);
INSERT INTO `cc_role_menu` VALUES (2, 1);
INSERT INTO `cc_role_menu` VALUES (2, 102);
INSERT INTO `cc_role_menu` VALUES (2, 1013);
INSERT INTO `cc_role_menu` VALUES (2, 1014);
INSERT INTO `cc_role_menu` VALUES (2, 1015);
INSERT INTO `cc_role_menu` VALUES (2, 1016);
INSERT INTO `cc_role_menu` VALUES (2, 2000);
INSERT INTO `cc_role_menu` VALUES (3, 2);
INSERT INTO `cc_role_menu` VALUES (3, 3);
INSERT INTO `cc_role_menu` VALUES (3, 4);
INSERT INTO `cc_role_menu` VALUES (3, 100);
INSERT INTO `cc_role_menu` VALUES (3, 101);
INSERT INTO `cc_role_menu` VALUES (3, 103);
INSERT INTO `cc_role_menu` VALUES (3, 104);
INSERT INTO `cc_role_menu` VALUES (3, 105);
INSERT INTO `cc_role_menu` VALUES (3, 106);
INSERT INTO `cc_role_menu` VALUES (3, 107);
INSERT INTO `cc_role_menu` VALUES (3, 108);
INSERT INTO `cc_role_menu` VALUES (3, 109);
INSERT INTO `cc_role_menu` VALUES (3, 110);
INSERT INTO `cc_role_menu` VALUES (3, 111);
INSERT INTO `cc_role_menu` VALUES (3, 112);
INSERT INTO `cc_role_menu` VALUES (3, 113);
INSERT INTO `cc_role_menu` VALUES (3, 114);
INSERT INTO `cc_role_menu` VALUES (3, 115);
INSERT INTO `cc_role_menu` VALUES (3, 116);
INSERT INTO `cc_role_menu` VALUES (3, 500);
INSERT INTO `cc_role_menu` VALUES (3, 501);
INSERT INTO `cc_role_menu` VALUES (3, 1001);
INSERT INTO `cc_role_menu` VALUES (3, 1002);
INSERT INTO `cc_role_menu` VALUES (3, 1003);
INSERT INTO `cc_role_menu` VALUES (3, 1004);
INSERT INTO `cc_role_menu` VALUES (3, 1005);
INSERT INTO `cc_role_menu` VALUES (3, 1006);
INSERT INTO `cc_role_menu` VALUES (3, 1007);
INSERT INTO `cc_role_menu` VALUES (3, 1008);
INSERT INTO `cc_role_menu` VALUES (3, 1009);
INSERT INTO `cc_role_menu` VALUES (3, 1010);
INSERT INTO `cc_role_menu` VALUES (3, 1011);
INSERT INTO `cc_role_menu` VALUES (3, 1012);
INSERT INTO `cc_role_menu` VALUES (3, 1017);
INSERT INTO `cc_role_menu` VALUES (3, 1018);
INSERT INTO `cc_role_menu` VALUES (3, 1019);
INSERT INTO `cc_role_menu` VALUES (3, 1020);
INSERT INTO `cc_role_menu` VALUES (3, 1021);
INSERT INTO `cc_role_menu` VALUES (3, 1022);
INSERT INTO `cc_role_menu` VALUES (3, 1023);
INSERT INTO `cc_role_menu` VALUES (3, 1024);
INSERT INTO `cc_role_menu` VALUES (3, 1025);
INSERT INTO `cc_role_menu` VALUES (3, 1026);
INSERT INTO `cc_role_menu` VALUES (3, 1027);
INSERT INTO `cc_role_menu` VALUES (3, 1028);
INSERT INTO `cc_role_menu` VALUES (3, 1029);
INSERT INTO `cc_role_menu` VALUES (3, 1030);
INSERT INTO `cc_role_menu` VALUES (3, 1031);
INSERT INTO `cc_role_menu` VALUES (3, 1032);
INSERT INTO `cc_role_menu` VALUES (3, 1033);
INSERT INTO `cc_role_menu` VALUES (3, 1034);
INSERT INTO `cc_role_menu` VALUES (3, 1035);
INSERT INTO `cc_role_menu` VALUES (3, 1036);
INSERT INTO `cc_role_menu` VALUES (3, 1037);
INSERT INTO `cc_role_menu` VALUES (3, 1038);
INSERT INTO `cc_role_menu` VALUES (3, 1039);
INSERT INTO `cc_role_menu` VALUES (3, 1040);
INSERT INTO `cc_role_menu` VALUES (3, 1041);
INSERT INTO `cc_role_menu` VALUES (3, 1042);
INSERT INTO `cc_role_menu` VALUES (3, 1043);
INSERT INTO `cc_role_menu` VALUES (3, 1044);
INSERT INTO `cc_role_menu` VALUES (3, 1045);
INSERT INTO `cc_role_menu` VALUES (3, 1046);
INSERT INTO `cc_role_menu` VALUES (3, 1047);
INSERT INTO `cc_role_menu` VALUES (3, 1048);
INSERT INTO `cc_role_menu` VALUES (3, 1049);
INSERT INTO `cc_role_menu` VALUES (3, 1050);
INSERT INTO `cc_role_menu` VALUES (3, 1051);
INSERT INTO `cc_role_menu` VALUES (3, 1052);
INSERT INTO `cc_role_menu` VALUES (3, 1053);
INSERT INTO `cc_role_menu` VALUES (3, 1054);
INSERT INTO `cc_role_menu` VALUES (3, 1055);
INSERT INTO `cc_role_menu` VALUES (3, 1056);
INSERT INTO `cc_role_menu` VALUES (3, 1057);
INSERT INTO `cc_role_menu` VALUES (3, 1058);
INSERT INTO `cc_role_menu` VALUES (3, 1059);
INSERT INTO `cc_role_menu` VALUES (3, 1060);
INSERT INTO `cc_role_menu` VALUES (3, 2000);
INSERT INTO `cc_role_menu` VALUES (11, 1);
INSERT INTO `cc_role_menu` VALUES (11, 100);
INSERT INTO `cc_role_menu` VALUES (11, 101);
INSERT INTO `cc_role_menu` VALUES (11, 102);
INSERT INTO `cc_role_menu` VALUES (11, 103);
INSERT INTO `cc_role_menu` VALUES (11, 104);
INSERT INTO `cc_role_menu` VALUES (11, 105);
INSERT INTO `cc_role_menu` VALUES (11, 106);
INSERT INTO `cc_role_menu` VALUES (11, 107);
INSERT INTO `cc_role_menu` VALUES (11, 108);
INSERT INTO `cc_role_menu` VALUES (11, 500);
INSERT INTO `cc_role_menu` VALUES (11, 501);
INSERT INTO `cc_role_menu` VALUES (11, 1001);
INSERT INTO `cc_role_menu` VALUES (11, 1002);
INSERT INTO `cc_role_menu` VALUES (11, 1003);
INSERT INTO `cc_role_menu` VALUES (11, 1004);
INSERT INTO `cc_role_menu` VALUES (11, 1005);
INSERT INTO `cc_role_menu` VALUES (11, 1006);
INSERT INTO `cc_role_menu` VALUES (11, 1007);
INSERT INTO `cc_role_menu` VALUES (11, 1008);
INSERT INTO `cc_role_menu` VALUES (11, 1009);
INSERT INTO `cc_role_menu` VALUES (11, 1010);
INSERT INTO `cc_role_menu` VALUES (11, 1011);
INSERT INTO `cc_role_menu` VALUES (11, 1012);
INSERT INTO `cc_role_menu` VALUES (11, 1013);
INSERT INTO `cc_role_menu` VALUES (11, 1014);
INSERT INTO `cc_role_menu` VALUES (11, 1015);
INSERT INTO `cc_role_menu` VALUES (11, 1016);
INSERT INTO `cc_role_menu` VALUES (11, 1017);
INSERT INTO `cc_role_menu` VALUES (11, 1018);
INSERT INTO `cc_role_menu` VALUES (11, 1019);
INSERT INTO `cc_role_menu` VALUES (11, 1020);
INSERT INTO `cc_role_menu` VALUES (11, 1021);
INSERT INTO `cc_role_menu` VALUES (11, 1022);
INSERT INTO `cc_role_menu` VALUES (11, 1023);
INSERT INTO `cc_role_menu` VALUES (11, 1024);
INSERT INTO `cc_role_menu` VALUES (11, 1025);
INSERT INTO `cc_role_menu` VALUES (11, 1026);
INSERT INTO `cc_role_menu` VALUES (11, 1027);
INSERT INTO `cc_role_menu` VALUES (11, 1028);
INSERT INTO `cc_role_menu` VALUES (11, 1029);
INSERT INTO `cc_role_menu` VALUES (11, 1030);
INSERT INTO `cc_role_menu` VALUES (11, 1031);
INSERT INTO `cc_role_menu` VALUES (11, 1032);
INSERT INTO `cc_role_menu` VALUES (11, 1033);
INSERT INTO `cc_role_menu` VALUES (11, 1034);
INSERT INTO `cc_role_menu` VALUES (11, 1035);
INSERT INTO `cc_role_menu` VALUES (11, 1036);
INSERT INTO `cc_role_menu` VALUES (11, 1037);
INSERT INTO `cc_role_menu` VALUES (11, 1038);
INSERT INTO `cc_role_menu` VALUES (11, 1039);
INSERT INTO `cc_role_menu` VALUES (11, 1040);
INSERT INTO `cc_role_menu` VALUES (11, 1041);
INSERT INTO `cc_role_menu` VALUES (11, 1042);
INSERT INTO `cc_role_menu` VALUES (11, 1043);
INSERT INTO `cc_role_menu` VALUES (11, 1044);
INSERT INTO `cc_role_menu` VALUES (11, 1045);
INSERT INTO `cc_role_menu` VALUES (11, 2000);
INSERT INTO `cc_role_menu` VALUES (11, 2003);
INSERT INTO `cc_role_menu` VALUES (11, 2004);
INSERT INTO `cc_role_menu` VALUES (11, 2005);
INSERT INTO `cc_role_menu` VALUES (11, 2006);
INSERT INTO `cc_role_menu` VALUES (11, 2007);
INSERT INTO `cc_role_menu` VALUES (11, 2008);
INSERT INTO `cc_role_menu` VALUES (11, 2009);
INSERT INTO `cc_role_menu` VALUES (11, 2010);
INSERT INTO `cc_role_menu` VALUES (11, 2011);
INSERT INTO `cc_role_menu` VALUES (11, 2012);
INSERT INTO `cc_role_menu` VALUES (11, 2013);
INSERT INTO `cc_role_menu` VALUES (11, 2014);
INSERT INTO `cc_role_menu` VALUES (12, 2017);
INSERT INTO `cc_role_menu` VALUES (12, 2022);
INSERT INTO `cc_role_menu` VALUES (12, 2024);
INSERT INTO `cc_role_menu` VALUES (12, 2025);
INSERT INTO `cc_role_menu` VALUES (12, 2026);
INSERT INTO `cc_role_menu` VALUES (12, 2027);

-- ----------------------------
-- Table structure for cc_tag
-- ----------------------------
DROP TABLE IF EXISTS `cc_tag`;
CREATE TABLE `cc_tag`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(1) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_tag
-- ----------------------------
INSERT INTO `cc_tag` VALUES (1, 'Mybatis', NULL, NULL, NULL, '2022-01-11 09:20:50', 0, 'weqwe');
INSERT INTO `cc_tag` VALUES (2, 'asdas', NULL, '2022-01-11 09:20:55', NULL, '2022-01-11 09:20:55', 1, 'weqw');
INSERT INTO `cc_tag` VALUES (3, 'weqw', NULL, '2022-01-11 09:21:07', NULL, '2022-01-11 09:21:07', 1, 'qweqwe');
INSERT INTO `cc_tag` VALUES (4, 'Java', NULL, '2022-01-13 15:22:43', 1, '2023-05-09 16:33:06', 0, '基础');
INSERT INTO `cc_tag` VALUES (5, 'WAD', NULL, '2022-01-13 15:22:47', NULL, '2022-01-13 15:22:47', 1, 'ASDAD');
INSERT INTO `cc_tag` VALUES (6, 'Spring', 1, '2023-05-09 16:31:23', 1, '2023-05-09 16:31:23', 0, 'java核心');

-- ----------------------------
-- Table structure for cc_user
-- ----------------------------
DROP TABLE IF EXISTS `cc_user`;
CREATE TABLE `cc_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL' COMMENT '用户名',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL' COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL' COMMENT '密码',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户类型：0代表普通用户，1代表管理员',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phonenumber` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人的用户id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '删除标志（0代表未删除，1代表已删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14787164048666 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_user
-- ----------------------------
INSERT INTO `cc_user` VALUES (1, 'cc', 'cc', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '0', 'ccgccg@yeah.net', '13268014239', '0', 'http://ru8h77emb.hn-bkt.clouddn.com/2023/05/06/c9c4abd005c043669e0af0c7e5693edc.png', NULL, '2022-01-05 09:01:56', 1, '2023-05-10 18:34:24', 0);
INSERT INTO `cc_user` VALUES (3, 'cc1', 'weqe', '$2a$10$ydv3rLkteFnRx9xelQ7elOiVhFvXOooA98xCqk/omh7G94R.K/E3O', '1', '0', NULL, NULL, '0', NULL, NULL, '2022-01-05 13:28:43', NULL, '2022-01-05 13:28:43', 0);
INSERT INTO `cc_user` VALUES (4, 'cc2', 'dsadd', '$2a$10$kY4T3SN7i4muBccZppd2OOkhxMN6yt8tND1sF89hXOaFylhY2T3he', '1', '0', '23412332@qq.com', '19098790742', '0', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `cc_user` VALUES (5, 'sg2233', 'tteqe', '', '1', '0', NULL, '18246845873', '1', NULL, NULL, '2022-01-06 03:51:13', NULL, '2022-01-06 07:00:50', 1);
INSERT INTO `cc_user` VALUES (6, 'sangeng', 'sangeng', '$2a$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn.JeVcj2NdeZy', '1', '0', '2312321', '17777777777', '0', NULL, NULL, '2022-01-16 06:54:26', NULL, '2022-01-16 07:06:34', 1);
INSERT INTO `cc_user` VALUES (14787164048662, 'weixin', 'cc3', '$2a$10$y3k3fnMZsBNihsVLXWfI8uMNueVXBI08k.LzWYaKsW8CW7xXy18wC', '0', '0', 'weixin@qq.com', '13368014239', '0', NULL, -1, '2022-01-30 17:18:44', 1, '2023-05-10 18:39:29', 0);
INSERT INTO `cc_user` VALUES (14787164048663, 'ccg', 'ccg', '$2a$10$lk0mPu.DUrbPFOuSa.52vuZyQrjJnNvwhUZAbEy63VN54TC9VqWLe', '0', '0', 'ccgccg@yeah.net', NULL, '1', 'http://ru8h77emb.hn-bkt.clouddn.com/2023/05/07/a4416178a8a74452a118b3e81703bd60.png', NULL, NULL, NULL, NULL, 0);
INSERT INTO `cc_user` VALUES (14787164048664, 'ccc', 'ccc', '$2a$10$aQnEl8LqDJs1FyNrX0ryqOidQ//2GyWANuTGeA0pcZUD3c/mcczWq', '0', '0', '1321132adada@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `cc_user` VALUES (14787164048665, 'ccc', 'ccc', '$2a$10$UZy/38O9CizByRGa81FTEe/B6kMZmh/aNADv06/DjJNGFSEtJqXRO', '0', '0', 'ccgccg1@yeah.net', '13268014238', '0', NULL, 1, '2023-05-10 18:42:40', 1, '2023-05-10 18:42:40', 0);

-- ----------------------------
-- Table structure for cc_user_role
-- ----------------------------
DROP TABLE IF EXISTS `cc_user_role`;
CREATE TABLE `cc_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cc_user_role
-- ----------------------------
INSERT INTO `cc_user_role` VALUES (1, 1);
INSERT INTO `cc_user_role` VALUES (2, 2);
INSERT INTO `cc_user_role` VALUES (14787164048662, 2);
INSERT INTO `cc_user_role` VALUES (14787164048665, 2);

SET FOREIGN_KEY_CHECKS = 1;
