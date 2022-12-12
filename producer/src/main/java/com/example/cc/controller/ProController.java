package com.example.cc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProController {

    @RequestMapping("/getService")
    public String getService(){
        return "得到服务";
    }
}
