package com.duym.usertestall.domain.dto;
  
import java.io.Serializable;  
import java.time.LocalDateTime;  
  
import com.alibaba.excel.annotation.ExcelProperty;  
  
public class UserExportDTO implements Serializable {  
  
    /**  
     * serialVersionUID     */    private static final long serialVersionUID = 7227759579070259119L;  
  
    /**  
     * String 类型  
     */  
    @ExcelProperty(value = "用户名")  
    private String username;  
  
    /**  
     * Integer 类型  
     */  
    @ExcelProperty(value = "年龄")  
    private Integer age;  
  
    /**  
     * 版本  
     */  
    @ExcelProperty(value = "版本号")  
    private Long version;  
  
    /**  
     * 创建时间  
     */  
    @ExcelProperty(value = "创建时间")  
    private LocalDateTime created;  
  
    public String getUsername() {  
        return username;  
    }  
  
    public void setUsername(String username) {  
        this.username = username;  
    }  
  
    public Integer getAge() {  
        return age;  
    }  
  
    public void setAge(Integer age) {  
        this.age = age;  
    }  
  
    public Long getVersion() {  
        return version;  
    }  
  
    public void setVersion(Long version) {  
        this.version = version;  
    }  
  
    public LocalDateTime getCreated() {  
        return created;  
    }  
  
    public void setCreated(LocalDateTime created) {  
        this.created = created;  
    }  
}