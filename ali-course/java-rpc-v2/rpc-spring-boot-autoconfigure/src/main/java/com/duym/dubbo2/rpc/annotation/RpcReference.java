package com.duym.dubbo2.rpc.annotation;
  
import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;  
  
/**  
* 该注解用于注入远程服务  
*/  
@Target(ElementType.FIELD) // 方法注解  
@Retention(RetentionPolicy.RUNTIME) // 运行时注解  
public @interface RpcReference {  
  
String value();  
}