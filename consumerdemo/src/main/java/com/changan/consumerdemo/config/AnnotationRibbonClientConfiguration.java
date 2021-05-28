package com.changan.consumerdemo.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;


/**
 * 这里不使用@Configuration配置，防止称为全局配置
 * 可以继承AbstractLoadBalancerRule获得所有服务列表，自定义算法
 *
 * @author zab
 * @date 2021-5-28 13:47:08
 */
public class AnnotationRibbonClientConfiguration {

    @Autowired
    private IClientConfig config;

    @Bean
    public IRule ribbonRule(IClientConfig config) {
        // 轮询
        return new RoundRobinRule();

        // 选择一个最小的并发请求的server
        //new BestAvailableRule();

        // 随机
        //return new RandomRule();
    }

/*
    @Override
    public Server choose(Object o) {
        ILoadBalancer lb = this.getLoadBalancer();

        List<Server> upServerList = lb.getReachableServers();
        List<Server> allServerList = lb.getAllServers();

        int size = allServerList.size();

        Random random = new Random(10);
        int i = random.nextInt(size);
        return allServerList.get(i - 1);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }*/

}
