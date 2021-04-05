package com.yeb.server.config;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.connection.RedisConnectionFactory;
/**
 * @author 冲动火龙果
 * @version 1.0.0
 * @ClassName RedisConfig.java
 * @Description Redis配置类
 * @createTime 2021年04月05日 01:13:00
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //string类型 key序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //string类型 value序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        //hash类型 key序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash类型 key序列器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //创建redis链接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


}
