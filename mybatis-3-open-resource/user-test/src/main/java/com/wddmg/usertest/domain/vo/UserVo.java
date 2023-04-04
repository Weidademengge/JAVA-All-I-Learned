package com.wddmg.usertest.domain.vo;

/**
 * @author duym
 * @version $ Id: UserVo, v 0.1 2023/03/16 17:01 duym Exp $
 */
public class UserVo {
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
