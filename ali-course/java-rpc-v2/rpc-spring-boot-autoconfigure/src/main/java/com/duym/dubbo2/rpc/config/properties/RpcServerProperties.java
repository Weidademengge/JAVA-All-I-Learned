package com.duym.dubbo2.rpc.config.properties;
  
import lombok.Data;  
import org.springframework.boot.context.properties.ConfigurationProperties;  
  
@ConfigurationProperties(prefix = "rpc.server")  
@Data  
public class RpcServerProperties {  
  
/**  
* 服务提供者名称  
*/  
private String providerName = "";  
  
/**  
* 服务提供者端口  
*/  
private Integer providerPort = 9527;  
  
/**  
* 权重，默认为1  
*/  
private Integer weight = 1;  
}