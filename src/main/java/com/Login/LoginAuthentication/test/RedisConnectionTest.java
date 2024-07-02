package com.Login.LoginAuthentication.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void testConnection() {
        try {
            stringRedisTemplate.opsForValue().set("testKey", "testValue");
            String value = stringRedisTemplate.opsForValue().get("testKey");
            System.out.println("Redis connection successful, value: " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
