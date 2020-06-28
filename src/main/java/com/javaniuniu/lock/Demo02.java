package com.javaniuniu.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther: javaniuniu
 * @date: 2020/6/29 12:33 AM
 */
public class Demo02 {

    public static void main(String[] args) {
        Phone2 phone = new Phone2();
        new Thread(()->{
            phone.sms();
        },"A").start();

        new Thread(()->{
            phone.sms();
        },"B").start();
    }
}

class Phone2 {
    Lock lock = new ReentrantLock();
    public void sms() {
        lock.lock(); // 细节问题 lock.lock() ，lock.unlock();
        // lock 必须配对，否则就会死在里面
        try {
            System.out.println(Thread.currentThread().getName()+" sms");
            call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public synchronized void call() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+" call");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

