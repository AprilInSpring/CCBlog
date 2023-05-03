# Bug记录表

### 2023/4/26 9:52

BeanUtils类中的copyProperties()方法无法完成属性复制

问题查询

1.类型是否一样
2.有没有对应的getset方法，能不能访问对应的getset方法
3.传入要复制的对象resutl是否被实例化出来了

问题标记

原博文列表类由于是代码工具生成的，没有相应的lombok注解，没有get/set方法，导致反射失败

问题解决

在原博文列表类添加相应的注解



### 2023/5/3 12:05

项目每次启动都会报错，错误信息为mybatisplus的分页插件已经注册，不能重复注册一样的bean对象

问题解决

直接在target文件删除相应的class文件，但是治标不治本



### 2023/5/3 17:43

redis数据库出现问题，使用命令或者可视化工具无法获取键的值，但是程序可以正确运行

错误截图

![image-20230503174518863](C:\Users\PC\AppData\Roaming\Typora\typora-user-images\image-20230503174518863.png)