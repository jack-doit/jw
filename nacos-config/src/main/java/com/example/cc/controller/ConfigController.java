package com.example.cc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
//@Scope("prototype")
@Slf4j
public class ConfigController {
    @Value("${mayikt.name}")
    private String userName;

    public void ConfigController(){
        log.info("初始化");
    }

    @RequestMapping("/getConfig")
    public String getConfig() {
        return userName;
    }
}
