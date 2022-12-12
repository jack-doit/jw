package com.example.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class NacosConApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosConApplication.class, args);
    }

    /**
     * 将 restTemplate 注入到 spring ioc容器
     *
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
