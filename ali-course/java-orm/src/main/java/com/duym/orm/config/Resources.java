package com.duym.orm.config;
  
import java.io.InputStream;  
  
public class Resources {  
  
    /**  
     * 根据配置文件的路径，将配置文件加载成字节输入流，存储在内存中  
     * @param path  
     * @return  
     */  
    public static InputStream getResourceAsStream(String path) {  
        return Resources.class.getClassLoader().getResourceAsStream(path);  
    }  
}