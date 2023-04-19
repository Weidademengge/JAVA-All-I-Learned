package com.duym.patterns.observer;

/**
 * @author duym
 * @version $ Id: Client, v 0.1 2023/04/19 10:49 duym Exp $
 */
public class Client {
    public static void main(String[] args) {
        // 1.创建公众号对象
        SubscriptionSubject subject = new SubscriptionSubject();

        // 2.订阅公众号
        subject.attach(new WeiXInUser("孙悟空"));
        subject.attach(new WeiXInUser("猪悟能"));
        subject.attach(new WeiXInUser("沙悟净"));

        // 3.公众号更新，发消息给订阅者（观察者对象）
        subject.notify("专栏更新了");
    }
}
