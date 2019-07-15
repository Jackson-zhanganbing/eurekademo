package com.changan.consumerdemo.web;

import com.changan.consumerdemo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *@author zab
 *@date 2019/4/4 8:50
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TestService testService;

    @RequestMapping("/user")
    public String getUser(){
        String s = restTemplate.getForObject("http://microservice-provider-user/api/user",String.class);
        return s;
    }
    @RequestMapping("/user1")
    public String getUser1(){
        String s = testService.getUser();
        return s;
    }


}
