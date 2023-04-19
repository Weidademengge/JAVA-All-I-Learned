package com.duym.myspring.spring;

/**
 * @author duym
 * @version $ Id: BeanDefinition, v 0.1 2023/04/17 14:27 duym Exp $
 */
public class BeanDefinition {

    private Class type;

    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
