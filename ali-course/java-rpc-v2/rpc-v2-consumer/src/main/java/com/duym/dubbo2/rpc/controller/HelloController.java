package com.duym.dubbo2.rpc.controller;
  

import com.duym.dubbo2.rpc.annotation.RpcReference;
import com.duym.dubbo2.rpc.api.UserApi;
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RestController;  
  
  
@RestController  
public class HelloController {  
  
// 自定义注解，其中value为服务提供者名称，类似OpenFeign的使用  
@RpcReference("provider01")
private UserApi userApi;
  
@GetMapping("/index/{id}")  
public Object hello(@PathVariable Integer id) {  
return userApi.getById(id);  
}  
}