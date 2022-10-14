package com.example.demo.interceptor_test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("interceptor")
public class InterCeptorTestController {

    private final MyLogger myLogger;

    @RequestMapping("test")
    public String test(){
        myLogger.log("test");
        return "ok";
    }

    @RequestMapping("test2")
    public String test2(){
        myLogger.log("test2");
        return "ok";
    }

    @RequestMapping("test3")
    public String test3(){
        myLogger.log("test3");
        return "ok";
    }
}
