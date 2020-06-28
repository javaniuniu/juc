package com.javaniuniu.add;

import java.util.concurrent.CountDownLatch;

/**
 * @auther: javaniuniu
 * @date: 2020/6/28 10:21 AM
 */
// 减法计数器
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 总数是6，必须要执行任务的时候 再使用
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"Go out");
                countDownLatch.countDown();// 数量 -1
            },String.valueOf(i)).start();
        }

        countDownLatch.await(); // 等待计数器归零，再向下执行
        System.out.println("close Door");
    }
}
