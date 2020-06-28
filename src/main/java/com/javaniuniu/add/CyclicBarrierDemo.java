package com.javaniuniu.add;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @auther: javaniuniu
 * @date: 2020/6/28 10:31 AM
 */
// 加法计数器
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        /**
         * 集齐7颗龙珠，召唤神龙
         * 召唤龙珠的线程
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙");
        });
        for (int i = 0; i < 7; i++) {
            final int temp = i;
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"收集"+temp+"个龙珠");
                    cyclicBarrier.await();// 等待，await()会计数，计数完了后，会开启新的线程执行
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
