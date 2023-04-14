package com.duym.javasummer.mvc.model;
  
import java.lang.reflect.Method;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.regex.Pattern;  
  
public class Handler {  
    private Object controller;  
  
    private Method method;  
  
    private Pattern pattern;  
  
    private Map<String, Integer> paraIndexMapping;  
  
    public Handler(Object controller, Method method, Pattern pattern) {  
        this.controller = controller;  
        this.method = method;  
        this.pattern = pattern;  
        this.paraIndexMapping = new HashMap<>();  
    }  
  
    public Object getController() {  
        return controller;  
    }  
  
    public void setController(Object controller) {  
        this.controller = controller;  
    }  
  
    public Method getMethod() {  
        return method;  
    }  
  
    public void setMethod(Method method) {  
        this.method = method;  
    }  
  
    public Pattern getPattern() {  
        return pattern;  
    }  
  
    public void setPattern(Pattern pattern) {  
        this.pattern = pattern;  
    }  
  
    public Map<String, Integer> getParaIndexMapping() {  
        return paraIndexMapping;  
    }  
  
    public void setParaIndexMapping(Map<String, Integer> paraIndexMapping) {  
        this.paraIndexMapping = paraIndexMapping;  
    }  
}