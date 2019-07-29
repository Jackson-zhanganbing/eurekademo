package com.changan.consumerdemo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

    public static String lockKey = "lockKey";
    private static String lockValue = UUID.randomUUID().toString();

    public Boolean tryLock(){
        boolean lockResult = redisTemplate.opsForValue().setIfAbsent(lockKey,lockValue);
        if(!lockResult){
            return false;
        }

        redisTemplate.expire(lockKey,1000, TimeUnit.SECONDS);
        return true;
    }
    public void lock(){
        while(true){

            if(tryLock()){
                return;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
    }

    public void unLock(){
        redisTemplate.delete(lockKey);
    }
}
