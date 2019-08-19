package com.changan.consumerdemo.config;

import com.netflix.loadbalancer.*;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;

/**
 * 这里不使用@Configuration配置，防止称为全局配置
 *
 * @author zab
 * @date 2019-08-19 20:49
 */
public class AnnotationRibbonClientConfiguration {
    @Bean
    public ServerList<Server> ribbonServerList() {
        Server[] servers = new Server[2];
        servers[0] = new Server("http://localhost:8100");
        servers[1] = new Server("http://localhost:8101");
        ServerList<Server> serverList = new StaticServerList<>(servers);
        return serverList;
    }

    @Bean
    public IRule initRule() {
        // 轮询
        return new RoundRobinRule();

        // 选择一个最小的并发请求的server
        //new BestAvailableRule();

        // 随机
        //return new RandomRule();
    }
}
