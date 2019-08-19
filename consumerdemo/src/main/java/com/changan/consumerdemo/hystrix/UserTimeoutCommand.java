package com.changan.consumerdemo.hystrix;

import com.netflix.hystrix.*;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class UserTimeoutCommand extends HystrixCommand<String> {


    public UserTimeoutCommand() {
        super(Setter
                // 分组名称用于统计
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("userGroup"))
                // 用于监控、熔断、度量发布、缓存的Key值
                .andCommandKey(HystrixCommandKey.Factory.asKey("userCommandKey"))
                // 线程池命名，默认是HystrixCommandGroupKey的名称。
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("userThreadPool"))
                // command 熔断相关参数配置
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        // 隔离方式：线程池和信号量。默认使用线程池
                        // .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        // 超时时间500毫秒
                        // .withExecutionTimeoutInMilliseconds(500)
                        // 信号量隔离的模式下，最大的请求数。和线程池大小的意义一样
                        // .withExecutionIsolationSemaphoreMaxConcurrentRequests(2)
                        // 熔断时间（熔断开启后，各5秒后进入半开启状态，试探是否恢复正常）
                        // .withCircuitBreakerSleepWindowInMilliseconds(5000)
                )
                // 设置线程池参数
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        // 线程池大小
                        .withCoreSize(1)));
    }

    @Override
    protected String run() throws Exception {
        URL url = new URL("http://localhost:8101/api/timeout");
        byte[] result = new byte[1024];
        url.openStream().read(result);
        return new String(result);
    }

    @Override
    protected String getFallback() {
        // 执行超时、出错或者开启熔断之后，使用这个方法返回
        // 这种策略称为服务降级
        // 这里可以是一个固定返回值，查询缓存等
        return "服务降级，暂时不可用";
    }
}
