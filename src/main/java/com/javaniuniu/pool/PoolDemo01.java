package com.javaniuniu.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors 工具类，3大方法
 * new Thread().start(); 传统创建线程池
 * 使用线程池后，使用线程池来创建线程
 */
public class PoolDemo01 {
    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newSingleThreadExecutor(); // 单个线程
//        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建一个固定个数的线程池大小
        ExecutorService threadPool =  Executors.newCachedThreadPool();// 可伸缩，遇强则强，遇弱则弱

        try {
            for (int i = 1; i <= 100; i++) {
                // 使用线程池后，使用线程池来创建线程
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " OK");
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
