package com.javaniuniu.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CasDemo {

    // CAS compareAndSet : 比较并交换
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2020); // 设置初始值 2020 默认是 0

        // 对于我们平时写的SQL ： 乐观锁

        // 期望 更新
        // public final boolean compareAndSet(int expect, int update)
        // 如果期望达到了，就更新，否则就不更新 ,CAS 是CPU的并发原语
        // ===============================捣乱的线程============================
        System.out.println(atomicInteger.compareAndSet(2020, 2021)); // true
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(2021, 2020)); // true
        System.out.println(atomicInteger.get());

        // ===============================期望的线程============================
        System.out.println(atomicInteger.compareAndSet(2020, 6666)); // true
        System.out.println(atomicInteger.get());


    }
}
