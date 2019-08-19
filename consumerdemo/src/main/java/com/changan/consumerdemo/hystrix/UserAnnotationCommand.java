package com.changan.consumerdemo.hystrix;

import com.changan.consumerdemo.service.TestService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAnnotationCommand {

    @Autowired
    private TestService testService;

    @HystrixCommand(fallbackMethod = "timeoutFallback", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")
    }, commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "8000")
    })
    public String timeout() {

        return testService.timeout();
    }

    public String timeoutFallback() {
        return "timeout 降级";
    }


    @HystrixCommand(fallbackMethod = "exceptionFallback", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")
    }, commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String exception() {
        return testService.exception();
    }

    public String exceptionFallback() {
        return "exception 降级";
    }
}
