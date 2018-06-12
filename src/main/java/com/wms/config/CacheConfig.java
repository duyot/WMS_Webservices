//package com.wms.config;
//
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//@Configuration
//public class CacheConfig extends CachingConfigurerSupport {
//
//
//    @Bean
//    public JedisConnectionFactory redisConnectionFactory() {
//        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
//        // Defaults
//        redisConnectionFactory.setHostName("45.32.103.51");
//        redisConnectionFactory.setPort(6379);
//        redisConnectionFactory.setPassword("wms_redis");
//        return redisConnectionFactory;
//    }
//
//    @Bean
//    RedisTemplate<Object, Object> redisTemplate() {
//        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }
//}