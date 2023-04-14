package com.duym.summer.service;


import com.duym.javasummer.mvc.annotation.Service;

@Service("heartbeatService")
public class HeartbeatServiceImpl implements HeartbeatService {  
    @Override  
    public String getMessage(String msg) {  
        System.out.println("HeartbeatServiceImpl 中的入参：" + msg);  
        return msg;  
    }  
}