package com.example.cc.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundLoadBalance implements LoadBalance {
    @Autowired
    private DiscoveryClient discoveryClient;
    private AtomicInteger atomicCount = new AtomicInteger(0);

    @Override
    public ServiceInstance getInstances(String serviceId) {
        //1.根据服务的名称 获取 该服务集群地址列表
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        //2.判断是否null
        if (instances == null || instances.size() == 0) {
            return null;
        }
        //3.使用负载均衡算法
        int index = atomicCount.incrementAndGet() % instances.size();// 0+1
        return instances.get(index);
    }
}
