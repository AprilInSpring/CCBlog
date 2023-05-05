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

问题标记

直接在target文件删除相应的class文件，但是治标不治本，后续出现新的问题，删除后分页插件不起作用

问题解决

@Configuration注解也会生成bean对象，放到spring容器进行管理，导致配置类和分页插件bean的id重复，无法注入相同id的对象，直接修改配置类的名称，解决bug



### 2023/5/3 17:43

redis数据库出现问题，使用命令或者可视化工具无法获取键的值，但是程序可以正确运行

错误截图

![image-20230503174518863](C:\Users\PC\AppData\Roaming\Typora\typora-user-images\image-20230503174518863.png)

问题标记

属于redis的序列化问题，项目本身使用fastjson进行序列化，项目可以正常运行，但是可视化工具无法查看

问题解决

重写redis的序列化方法，改成redis自带的GenericJackson2JsonRedisSerializer()序列化，问题解决

