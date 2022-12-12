package com.example.cc.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MemberController {
    @Value("${server.port}")
    private String serverPort;

    /**
     * 会员服务提供接口
     *
     * @return
     */
    @RequestMapping("/getMember")
    public String getMember() {
        return "我是会员服务接口...端口：" + serverPort;
    }
}
