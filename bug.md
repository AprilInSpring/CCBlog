# Bug记录表

## 2023/4/26 9:52

BeanUtils类中的copyProperties()方法无法完成属性复制

问题查询

1.类型是否一样
2.有没有对应的getset方法，能不能访问对应的getset方法
3.传入要复制的对象resutl是否被实例化出来了

问题标记

原博文列表类由于是代码工具生成的，没有相应的lombok注解，没有get/set方法，导致反射失败

问题解决

在原博文列表类添加相应的注解



## 2023/5/3 12:05

项目每次启动都会报错，错误信息为mybatisplus的分页插件已经注册，不能重复注册一样的bean对象

问题标记

直接在target文件删除相应的class文件，但是治标不治本，后续出现新的问题，删除后分页插件不起作用

问题解决

@Configuration注解也会生成bean对象，放到spring容器进行管理，导致配置类和分页插件bean的id重复，无法注入相同id的对象，直接修改配置类的名称，解决bug



## 2023/5/3 17:43

redis数据库出现问题，使用命令或者可视化工具无法获取键的值，但是程序可以正确运行

错误截图

![image-20230503174518863](C:\Users\PC\AppData\Roaming\Typora\typora-user-images\image-20230503174518863.png)

问题标记

属于redis的序列化问题，项目本身使用fastjson进行序列化，项目可以正常运行，但是可视化工具无法查看

问题解决

重写redis的序列化方法，改成redis自带的GenericJackson2JsonRed，isSerializer()序列化，问题解决

## 2023/5/6 21:30

使用@ConfigurationProperties注解无法完成配置文件的属性注入，尚未解决

问题标记

使用该注解必须具备以下条件

1.在类上放添加@data注解，生成set/get方法

2.在类上方添加@ConfigurationProperties(prefix = "oss")完成前缀绑定

3.类的属性名必须要和配置文件的字段名一致

4.在配置文件上方添加@EnableConfigurationProperties注解，开启属性绑定功能

问题解决

使用@Value(${xxx})代替上述注解完成配置文件的属性注入,满足以上四种条件，问题解决

## 2023/5/7 20:47

swagger2的api接口文档乱码，尚未解决

问题标记

需要加深swagger2的功能学习，方便工作写接口测试文档

## 2023/5/8 09:30

实体类字段与数据库表字段不一致，存在多余的属性，使用@TableField(exist = false)进行排除

或者建立一个新的实体类，与数据库进行对接

## 2023/5/8 09:32

在查询数据库时，查不到相应的正常字段

问题标记

使用多表左连接查询的时候，某些正常的数据，无法连表，导致正常的数据被过滤掉

问题解决

取消多表联查，或者使用需要数据的表作为主表进行连接查询，这样主表的数据就会全部保留

## 2023/5/8 10:04

实体类的set方法，无法返回实体类对象

问题解决

可以手动修改实体类的set方法，使其返回this对象

使用Lombok注解

```java
//改变set方法的返回值
@Accessors(chain = true)
```

## 2023/5/9 18:44

添加博客文章的时候，无法维护相应的博客标签表

问题标记

博客标签对象添加功能正常，由于先维护博客标签表，再添加博客，导致无法获取到添加博客的主键，无法进行主键回填

问题解决

先添加博客，再维护博客表（通过主键回填功能）



