package com.example.cc.config;


import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
/*
* ribbon配置随机算法
* */
//@Configuration
public class LoadBalancerConfig {
    /**
     * 整合随机算法
     * @return
     */
    @Bean
    public RandomRule randomRule() {
        return new RandomRule();
    }
}
