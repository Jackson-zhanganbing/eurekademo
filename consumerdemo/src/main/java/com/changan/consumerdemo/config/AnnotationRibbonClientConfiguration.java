package com.changan.consumerdemo.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * 这里不使用@Configuration配置，防止称为全局配置
 *
 * @author zab
 * @date 2019-08-19 20:49
 */
public class AnnotationRibbonClientConfiguration {
/*    @Bean
    public ServerList<Server> ribbonServerList() {
        Server[] servers = new Server[2];
        servers[0] = new Server("http://localhost:8001");
        servers[1] = new Server("http://localhost:8002");
        ServerList<Server> serverList = new StaticServerList<>(servers);
        return serverList;
    }
*/

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


 /*   @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        ILoadBalancer lb = this.getLoadBalancer();

        List<Server> upServerList = lb.getReachableServers();
        List<Server> allServerList = lb.getAllServers();

        return null;
    }*/
}
