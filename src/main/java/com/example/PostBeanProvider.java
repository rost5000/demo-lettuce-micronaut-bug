package com.example;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import jakarta.inject.Singleton;

import java.lang.reflect.Field;

@Singleton
public class PostBeanProvider implements BeanCreatedEventListener<RedisClient> {
    @Override
    public RedisClient onCreated(BeanCreatedEvent<RedisClient> event) {
        var expectedBean = event.getBean();
        var clazz = RedisClient.class;
        try {
            Field uriField = clazz.getDeclaredField("redisURI");
            uriField.setAccessible(true);
            RedisURI redisURI = (RedisURI) uriField.get(expectedBean);
            System.out.println("My Password from application.yaml: " + (redisURI.getPassword() == null ? null : new String(redisURI.getPassword())));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return expectedBean;
    }
}
