package com.wddmg.concurrent.test;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author duym
 * @version $ Id: Test40, v 0.1 2023/04/12 10:41 duym Exp $
 */
public class Test40 {
    public static void main(String[] args) {
        Student stu = new Student();

        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class,String.class,"name");

        System.out.println(updater.compareAndSet(stu,null,"张三"));
        System.out.println(stu);
    }
}

class Student{
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
