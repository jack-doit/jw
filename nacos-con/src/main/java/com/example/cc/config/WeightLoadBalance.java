package com.example.cc.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
* ribbon配置权重算法
* */
@Component
public class WeightLoadBalance extends AbstractLoadBalancerRule {
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    private AtomicInteger countAtomicInteger = new AtomicInteger(0);

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        // 获取该服务接口地址 多个
        List<Server> upList = lb.getReachableServers();
        ArrayList<NacosServer> newNacosServers = new ArrayList<>();
        upList.forEach((s) -> {
            NacosServer nacosServer = (NacosServer) s;
            double weight = nacosServer.getInstance().getWeight();
            for (int i = 0; i < weight; i++) {
                newNacosServers.add(nacosServer);
            }
        });
        return newNacosServers.get(countAtomicInteger.incrementAndGet() % newNacosServers.size());

    }
}
