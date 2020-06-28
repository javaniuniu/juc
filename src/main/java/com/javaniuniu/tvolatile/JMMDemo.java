package com.javaniuniu.tvolatile;

import java.util.concurrent.TimeUnit;

/**
 * JMM 可见性
 */
public class JMMDemo {
    // 不加 volatile 程序出现死循环
    // 加 volatile 可以保证可见性
    private volatile static int num = 0;
    public static void main(String[] args) {

        new Thread(()->{ // 线程1 对主线程的变化不知道
            while (num ==0) {

            }
        }).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        num =1;
        System.out.println(num);
    }
}
