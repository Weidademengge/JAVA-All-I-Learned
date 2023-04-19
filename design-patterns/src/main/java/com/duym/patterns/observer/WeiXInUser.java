package com.duym.patterns.observer;

/**
 * @author duym
 * @version $ Id: WeiXInUser, v 0.1 2023/04/19 10:45 duym Exp $
 */
public class WeiXInUser implements Observer {
    public void update(String message){
        System.out.println(name+"-"+message);
    }

    private String name;

    public WeiXInUser(String name) {
        this.name = name;
    }


}
