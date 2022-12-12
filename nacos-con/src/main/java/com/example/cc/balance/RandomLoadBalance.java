package com.example.cc.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomLoadBalance implements LoadBalance {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public ServiceInstance getInstances(String serviceId) {
        //1.根据服务的名称 获取 该服务集群地址列表
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        //2.判断是否null
        if (instances == null || instances.size() == 0) {
            return null;
        }
        // 生成随机 范围
        Random random = new Random();
        //3  0 1 2
        int index = random.nextInt(instances.size());
        return instances.get(index);
    }
}
