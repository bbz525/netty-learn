package com.poplar.test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created By poplar on 2019/11/4
 * AtomicIntegerFieldUpdater 类的测试
 * Netty引用计数中没有采用 AtomicInteger而是使用了 AtomicIntegerFieldUpdater 主要是为了减少对象的创建
 */
public class AtomicUpdateTest {

    public static void main(String[] args) {
        Student student = new Student();
       /* public int getAndIncrement(T obj) {
            int prev, next;
            do {
                prev = get(obj);
                next = prev + 1;
            } while (!compareAndSet(obj, prev, next));
            return prev;
        }*/
        AtomicIntegerFieldUpdater<Student> atomicIntegerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Student.class, "age");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println(atomicIntegerFieldUpdater.getAndIncrement(student));
            }).start();
        }
    }
}

class Student {
    volatile int age = 1;
}