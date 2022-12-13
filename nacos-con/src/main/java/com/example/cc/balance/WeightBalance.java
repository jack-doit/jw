package com.example.cc.balance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/*
* 权重
* */
@Component
public class WeightBalance implements LoadBalance {
    @Autowired
    private DiscoveryClient discoveryClient;
    private AtomicInteger countAtomicInteger = new AtomicInteger(0);

    @Override
    public ServiceInstance getInstances(String serviceId) {
        // 1.根据服务的id名称 获取该接口多个实例
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        if (instances == null) {
            return null;
        }

        ArrayList<ServiceInstance> newInstances = new ArrayList<>();
        // 循环遍历该服务名称 对应的多个实例
        instances.forEach((service) -> {
            // 获取该服务实例对应的权重比例
            Double weight = Double.parseDouble(service.getMetadata().get("nacos.weight"));
            for (int i = 0; i < weight; i++) {
                newInstances.add(service);
            }
        });
        // 线程安全性 i++
        return newInstances.get(countAtomicInteger.incrementAndGet() % newInstances.size());
    }
}
