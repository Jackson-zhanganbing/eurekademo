package com.changan.provider1demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *@author zab
 *@date 2019/4/4 8:50
*/
@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/user")
    public String getUser(){
        return "hello eureka!provider1,8002";
    }

    @RequestMapping("/timeout")
    public String timeout() throws InterruptedException{
        Thread.sleep(1000000L);
        return "hello eureka!provider1,8002";
    }

    @RequestMapping("/exception")
    public String exception(){
        if (System.currentTimeMillis() % 2 == 0) {
            throw new RuntimeException("random exception");
        }
        return "success";

    }
}
