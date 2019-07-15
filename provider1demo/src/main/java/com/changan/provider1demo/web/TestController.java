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
        return "hello eureka!provider1,8101";
    }
}
