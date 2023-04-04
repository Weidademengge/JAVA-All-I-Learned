package com.wddmg.usertest.domain.dto;

import java.io.Serializable;

/**
 * @author duym
 * @version $ Id: UserDao, v 0.1 2023/03/16 16:31 duym Exp $
 */
public class UserDTO implements Serializable {
    private Integer id;

    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

}
