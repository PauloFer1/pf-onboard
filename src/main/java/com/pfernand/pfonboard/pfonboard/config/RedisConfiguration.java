package com.pfernand.pfonboard.pfonboard.config;

import com.pfernand.pfonboard.pfonboard.core.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    JedisConnectionFactory jedisConnectionFactory(@Value("${spring.redis.host}") final String host,
                                                  @Value("${spring.redis.port}") final int port) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        jedisConnectionFactory.getPoolConfig().setMaxIdle(30);
        jedisConnectionFactory.getPoolConfig().setMinIdle(10);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(final JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.setEnableTransactionSupport(true);
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

    @Bean
    public HashOperations<String, String, User> hashOperations(final RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }
}
