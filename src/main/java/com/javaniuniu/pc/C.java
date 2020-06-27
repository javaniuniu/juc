package com.javaniuniu.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther: javaniuniu
 * @date: 2020/6/27 11:31 PM
 */
public class C {
    public static void main(String[] args) {
        Data3 data = new Data3();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                data.printA();
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                data.printB();
            }
        },"B").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                data.printC();
            }
        },"C").start();
    }

}

class Data3 {
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int num = 1; // 1A 2B 3C

    public void printA() {
        lock.lock();
        // 业务，判断-> 执行 --> 通知
        try {
            while (num!=1) {
                // 等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName()+"==>AAAAA");
            // 唤醒，唤醒指定的人，B
            num = 2;
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    public void printB() {
        lock.lock();
        // 业务，判断-> 执行 --> 通知
        try {
            while (num!=2) {
                // 等待
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+"==>BBBBB");
            // 唤醒，唤醒指定的人，B
            num = 3;
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void printC() {
        lock.lock();
        // 业务，判断-> 执行 --> 通知
        try {
            while (num!=3) {
                // 等待
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+"==>CCCCC");
            // 唤醒，唤醒指定的人，B
            num = 1;
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}