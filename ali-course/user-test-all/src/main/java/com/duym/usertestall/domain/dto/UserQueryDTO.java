package com.duym.usertestall.domain.dto;

import java.io.Serializable;

public class UserQueryDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6388963865596623280L;

    /**
     * 用户名
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}