package com.zxnk.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
//redis工具类，配置redis的序列化
@Configuration
public class RedisConfig {

    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        //创建模板
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //设置工厂
        template.setConnectionFactory(connectionFactory);

        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        //	使用redis自带的序列化工具，完成值的序列化处理
        template.setValueSerializer(serializer);

        // Hash的key也采用StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        //	使用redis自带的序列化工具，完成值的序列化处理
        template.setHashValueSerializer(serializer);

        //已解决，在redisTemplate完成属性注入之后，进行一些初始化操作，具体为设置模板的连接工厂判断，不能为null，否则会抛出异常，
        //该方法就是设置抛出异常，并设置异常信息
        template.afterPropertiesSet();
        return template;
    }
}