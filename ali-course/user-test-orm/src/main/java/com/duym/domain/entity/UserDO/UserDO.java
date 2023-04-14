package com.duym.domain.entity.UserDO;
  
import java.util.Date;  
  
public class UserDO {  
  
    /*** 用户主信息 ***/  
    /**     * 用户名  
     */  
    private String username;  
      
    /**  
     * 用户密码  
     */  
    private String password;  
      
    /**  
     * 邮箱  
     */  
    private String email;  
  
    /**  
     * 年龄  
     */  
    private Integer age;  
  
    /**  
     * 手机号  
     */  
    private String phone;  
  
  
    /*** 系统主信息 ***/  
    /**     * 数据库主键  
     */  
    private Long id;  
  
    /**  
     * 数据的创建时间  
     */  
    private Date created;  
  
    /**  
     * 数据修改时间  
     */  
    private Date modified;  
  
    /**  
     * 创建者  
     */  
    private String creator;  
  
    /**  
     * 最后修改者  
     */  
    private String operator;  
  
    /**  
     * 逻辑删除字段：0：正常，1：逻辑删除  
     */  
    private Integer deleted;  
  
    /**  
     * 版本号  
     */  
    private Long version;  
  
    public String getUsername() {  
        return username;  
    }  
  
    public void setUsername(String username) {  
        this.username = username;  
    }  
  
    public String getPassword() {  
        return password;  
    }  
  
    public void setPassword(String password) {  
        this.password = password;  
    }  
  
    public String getEmail() {  
        return email;  
    }  
  
    public void setEmail(String email) {  
        this.email = email;  
    }  
  
    public Integer getAge() {  
        return age;  
    }  
  
    public void setAge(Integer age) {  
        this.age = age;  
    }  
  
    public String getPhone() {  
        return phone;  
    }  
  
    public void setPhone(String phone) {  
        this.phone = phone;  
    }  
  
    public Long getId() {  
        return id;  
    }  
  
    public void setId(Long id) {  
        this.id = id;  
    }  
  
    public Integer getDeleted() {  
        return deleted;  
    }  
  
    public void setDeleted(Integer deleted) {  
        this.deleted = deleted;  
    }  
  
    public Long getVersion() {  
        return version;  
    }  
  
    public void setVersion(Long version) {  
        this.version = version;  
    }  
  
    public String getCreator() {  
        return creator;  
    }  
  
    public void setCreator(String creator) {  
        this.creator = creator;  
    }  
  
    public String getOperator() {  
        return operator;  
    }  
  
    public void setOperator(String operator) {  
        this.operator = operator;  
    }  
  
    public Date getCreated() {  
        return created;  
    }  
  
    public void setCreated(Date created) {  
        this.created = created;  
    }  
  
    public Date getModified() {  
        return modified;  
    }  
  
    public void setModified(Date modified) {  
        this.modified = modified;  
    }  
  
    @Override  
    public String toString() {  
        return "UserDO{" +  
            "username='" + username + '\'' +  
            ", password='" + password + '\'' +  
            ", email='" + email + '\'' +  
            ", age=" + age +  
            ", phone='" + phone + '\'' +  
            ", id=" + id +  
            ", created=" + created +  
            ", modified=" + modified +  
            ", creator='" + creator + '\'' +  
            ", operator='" + operator + '\'' +  
            ", deleted=" + deleted +  
            ", version=" + version +  
            '}';  
    }  
}