package com.duym.summer.web;


import com.duym.javasummer.mvc.annotation.Autowired;
import com.duym.javasummer.mvc.annotation.Controller;
import com.duym.javasummer.mvc.annotation.RequestMapping;
import com.duym.summer.service.HeartbeatService;

@Controller
@RequestMapping("/test")
public class HeartbeatController {  
  
    @Autowired
    private HeartbeatService heartbeatService;
  
    @RequestMapping("/heartbeat")  
    public String heartbeat(String msg) {  
        return heartbeatService.getMessage(msg);  
    }  
}