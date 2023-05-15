package com.duym.dubbo2.rpc.model;
  
import lombok.Data;  
  
@Data  
public class ProviderBean {  
/**  
* 服务名称  
*/  
private String name;  
/**  
* 服务地址，格式：ip:port  
*/  
private String address;  
/**  
* 权重，越大优先级越高  
*/  
private Integer weight;  
}