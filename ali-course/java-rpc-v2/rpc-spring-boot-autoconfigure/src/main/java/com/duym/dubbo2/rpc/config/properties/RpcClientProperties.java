package com.duym.dubbo2.rpc.config.properties;
  
import lombok.Data;  
import org.springframework.boot.context.properties.ConfigurationProperties;  
  
@ConfigurationProperties(prefix = "rpc.client")  
@Data  
public class RpcClientProperties {  
  
/**  
* 服务提供者名称  
*/  
private String consumerName = "";  
  
}