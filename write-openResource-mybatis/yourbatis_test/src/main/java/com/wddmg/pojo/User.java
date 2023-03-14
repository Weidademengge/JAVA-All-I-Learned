package com.wddmg.pojo;

/**
 * @author duym
 * @version $ Id: User, v 0.1 2023/03/08 14:36 banma-0163 Exp $
 */
public class User {

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
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
