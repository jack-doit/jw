package com.example.cc.balance;

import org.springframework.cloud.client.ServiceInstance;

public interface LoadBalance {
    /**
     * 负载均衡算法 给我多个地址 负载均衡 取出一个地址返回使用
     */
    ServiceInstance getInstances(String serviceId);
}
