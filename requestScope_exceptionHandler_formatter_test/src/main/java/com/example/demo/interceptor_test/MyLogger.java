package com.example.demo.interceptor_test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Slf4j
@Component
@Scope(value ="request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL){
        this.requestURL = requestURL;
    }

    public void log(String message) {
        log.info("[{}] [{}] [{}]", uuid, requestURL, message);
    }

    @PostConstruct
    public void init(){
        uuid = UUID.randomUUID().toString();
        log.info("[{}] MyLogger : request scope bean create", uuid);
    }

    @PreDestroy
    public void destroy(){
        log.info("[{}] MyLogger : request scope bean close", uuid);
    }
}
