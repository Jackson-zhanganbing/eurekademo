package com.changan.consumerdemo.web;

import com.changan.consumerdemo.hystrix.UserAnnotationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hystrix")
public class HystrixController{

    @Autowired
    private UserAnnotationCommand userAnnotationCommand;

    @RequestMapping("/command/timeout")
    public String commandTimeout() {
        return userAnnotationCommand.timeout();
    }

    @RequestMapping("/command/exception")
    public String commandException() {
        return userAnnotationCommand.exception();
    }

}

