package com.duym.patterns.observer;



/**
 * 主题
 *
 * @author duym
 * @date 2023/04/19
 */
public interface Subject {

    // 添加订阅者（添加观察者对象）
    void attach(Observer observer);

    // 删除订阅者
    void detach(Observer observer);

    // 通知订阅者更新消息
    void notify(String message);
}
