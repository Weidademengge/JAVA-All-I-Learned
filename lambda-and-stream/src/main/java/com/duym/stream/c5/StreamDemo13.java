package com.duym.stream.c5;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author duym
 * @version $ Id: StreamDemo13, v 0.1 2023/03/31 22:45 duym Exp $
 */
public class StreamDemo13 {
    public static void main(String[] args) {

        /**
         * 需求：
         *      现在又两个Arraylist集合
         *      第一个集合中：存储6名男演员的名字和年龄。第二个集合中：存储6名女演员的名字和年龄
         *      姓名和年龄中间用逗号隔开。比如：张三，23
         *      要求完成如下的操作：
         *      1.男演员只要名字为3个字的前两人
         *      2.女演员只要姓杨的，并且不要第一个
         *      3.把过滤后的男演员姓名和女演员的姓名合并到一起
         *      4.将上一步的演员信息封装成Actor对象
         *      5.将所有的演员对象都保存到list集合中
         *      备注：演员类Actor，属性只有一个：name，age
         *
         */
        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        Collections.addAll(list1,"zhangsan,23","zhangsi,24","zhangwu,25","zhangliu,26","zhangqi,27");
        Collections.addAll(list2,"杨三,23","杨四,24","杨无,25","杨六,26","李7,27");

        List<Actor> resList = Stream.concat(
                        list1.stream().limit(3),
                        list2.stream()
                                .skip(1)
                                .filter(s -> s.startsWith("杨"))
                )
                .map(s -> {
                    Actor temp = new Actor();
                    temp.name = s.split(",")[0];
                    temp.age = Integer.parseInt(s.split(",")[1]);
                    return temp;
                })
                .collect(Collectors.toList());
        System.out.println(resList);
    }

    static class Actor{
        public String name;
        public Integer age;

        public Actor() {

        }
        public Actor(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o){
                return true;
            }
            if (o == null || getClass() != o.getClass()){
                return false;
            }
            Actor actor = (Actor) o;
            return Objects.equals(name, actor.name) && Objects.equals(age, actor.age);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return "Actor{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
