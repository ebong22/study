package com.example.demo.interceptor_test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class ReqLogInterceptor implements HandlerInterceptor {

    private final MyLogger myLogger;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        myLogger.setRequestURL(request.getRequestURL().toString());
        myLogger.log("prehandle");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        myLogger.log("postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        myLogger.log("afterCompletion");
        if(ex != null){
            myLogger.log("ex = " + ex.getMessage());
        }
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
