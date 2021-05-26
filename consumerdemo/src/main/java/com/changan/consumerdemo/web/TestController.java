package com.changan.consumerdemo.web;

import com.changan.consumerdemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zab
 * @date 2019/4/4 8:50
 */
@RestController
@RequestMapping("/api")
public class TestController {
    static String uuid = UUID.randomUUID().toString();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestService testService;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/user")
    public String getUser() {
        String s = restTemplate.getForObject("http://microservice-provider-user/api/user", String.class);
        return s;
    }

    @RequestMapping("/user1")
    public String getUser1() {

        String s = testService.getUser();
        return s;
    }

    @RequestMapping("/set/{id}/{content}")
    public void set(@PathVariable String id, @PathVariable String content) {

        redisTemplate.opsForValue().set(id, content);

    }

    @RequestMapping("/del_stock")
    public void delOne() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Long expire = redisTemplate.getExpire("zab");
                        if (expire <= 5 && expire > 0) {
                            uuid = UUID.randomUUID().toString();
                            redisTemplate.opsForValue().set("zab", uuid, 10, TimeUnit.SECONDS);
                        }
                    }
                }, 0, 5000);
            }
        }).start();
        try {
            while (true) {
                if (redisTemplate.opsForValue().setIfAbsent("zab", uuid, 10, TimeUnit.SECONDS)) {
                    String stockStr = (String) redisTemplate.opsForValue().get("stock");
                    Integer stockInt = Integer.parseInt(stockStr);
                    if (stockInt > 0) {
                        Integer stockChanged = stockInt - 1;
                        redisTemplate.opsForValue().set("stock", stockChanged.toString());
                        System.out.println("扣减成功！当前余额为：" + stockChanged);
                    } else {
                        System.out.println("扣减失败，余额不足");
                    }
                    break;
                }
            }
        } finally {
            if (uuid.equals(redisTemplate.opsForValue().get("zab"))) {
                redisTemplate.delete("zab");
            }
        }


    }

    @RequestMapping("/del_stock1")
    public void delOne1() {
        redisLock.lock();
        try {
            String stockStr = (String) redisTemplate.opsForValue().get("stock");
            Integer stockInt = Integer.parseInt(stockStr);
            if (stockInt > 0) {
                Integer stockChanged = stockInt - 1;
                redisTemplate.opsForValue().set("stock", stockChanged.toString());
                System.out.println("扣减成功！当前余额为：" + stockChanged);
            } else {
                System.out.println("扣减失败，余额不足");
            }
        } finally {
            redisLock.unLock();
        }

    }

    @RequestMapping("/get/{id}")
    public String get(@PathVariable String id) {

        String content = (String) redisTemplate.opsForValue().get(id);

        return content;
    }


}
