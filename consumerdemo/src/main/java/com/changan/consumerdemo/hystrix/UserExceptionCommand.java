package com.changan.consumerdemo.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.net.URL;

public class UserExceptionCommand extends HystrixCommand<String> {


    public UserExceptionCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("userGroup"));
    }

    @Override
    protected String run() throws Exception {
        URL url = new URL("http://localhost:8100/api/exception");
        byte[] result = new byte[1024];
        url.openStream().read(result);
        return new String(result);
    }

    @Override
    protected String getFallback() {
        return "服务降级，暂时不可用";
    }
}
