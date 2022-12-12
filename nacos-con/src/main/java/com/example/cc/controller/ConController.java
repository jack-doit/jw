package com.example.cc.controller;

import com.example.cc.balance.RandomLoadBalance;
import com.example.cc.balance.RoundLoadBalance;
import com.example.cc.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class ConController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RoundLoadBalance roundLoadBalance;
    @Autowired
    private RandomLoadBalance randomLoadBalance;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getService")
    public String getService(){
        List<ServiceInstance> instances = discoveryClient.getInstances("mayikt-member");
        ServiceInstance serviceInstance = instances.get(0);
        String memberUrl = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + "getMember";
        return "订单服务调用会员服务:" + HttpClientUtils.doGet(memberUrl, null);
    }

    @RequestMapping("/restService")
    public String restService(){
        List<ServiceInstance> instances = discoveryClient.getInstances("mayikt-member");
        ServiceInstance serviceInstance = instances.get(0);
        String memberUrl = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + "getMember";
        return "订单服务调用会员服务:" + restTemplate.getForObject(memberUrl, String.class);
    }

    @RequestMapping("/getBalanceOne")
    public String getBalanceOne(){
        ServiceInstance serviceInstance = randomLoadBalance.getInstances("mayikt-member");
        String memberUrl = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + "getMember";
        return "订单服务调用会员服务:" + HttpClientUtils.doGet(memberUrl, null);
    }
    @RequestMapping("/getBalanceTwo")
    public String getBalanceTwo(){
        ServiceInstance serviceInstance = roundLoadBalance.getInstances("mayikt-member");
        String memberUrl = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + "getMember";
        return "订单服务调用会员服务:" + HttpClientUtils.doGet(memberUrl, null);
    }

    @RequestMapping("/downService")
    public String downService() {
        List<ServiceInstance> instances = discoveryClient.getInstances("mayikt-member");
        for (int i = 0; i < instances.size(); i++) {
            try {
                ServiceInstance serviceInstance = instances.get(i);
                String memberUrl = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + "getMember";
                return "订单服务调用会员服务:" + restTemplate.getForObject(memberUrl, String.class);
            } catch (RestClientException e) {
                log.error("[rpc远程调用发生了故障 开始故障转移 切换下一个地址调用 e:{}]", e);
            }
        }
        return "fail";
    }

}
