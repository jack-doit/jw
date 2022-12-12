package com.example.cc.controller;

import com.example.cc.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConController {
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/getService")
    public String getService(){
        List<ServiceInstance> instances = discoveryClient.getInstances("mayikt-member");
        ServiceInstance serviceInstance = instances.get(0);
        String memberUrl = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/" + "getService";
        return "获得服务:" + HttpClientUtils.doGet(memberUrl, null);
    }
}
