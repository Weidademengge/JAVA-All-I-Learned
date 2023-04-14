package com.duym.orm.model;
  
public class MappedStatement {  
  
    private String id;  
  
    private String resultType;  
  
    private String paramterType;  
  
    private String sql;  
  
    public String getId() {  
        return id;  
    }  
  
    public void setId(String id) {  
        this.id = id;  
    }  
  
    public String getResultType() {  
        return resultType;  
    }  
  
    public void setResultType(String resultType) {  
        this.resultType = resultType;  
    }  
  
    public String getParamterType() {  
        return paramterType;  
    }  
  
    public void setParamterType(String paramterType) {  
        this.paramterType = paramterType;  
    }  
  
    public String getSql() {  
        return sql;  
    }  
  
    public void setSql(String sql) {  
        this.sql = sql;  
    }  
}