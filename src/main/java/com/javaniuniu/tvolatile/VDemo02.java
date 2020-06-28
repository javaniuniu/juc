package com.javaniuniu.tvolatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */

// AtomicInteger 保证原子性，而且非常高效
public class VDemo02 {

    private static AtomicInteger num = new AtomicInteger();

    public static void add() {
        num.getAndIncrement(); // AtomicInteger +1 方法 用的是CAS，效率极高
    }

    public static void main(String[] args) {

        // 理论上 num 结果应该为 2 万
        for (int i = 1; i <=20 ; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        while (Thread.activeCount() >2) { // man gc
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() +"  " + num);
    }
}
