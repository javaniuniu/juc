package com.javaniuniu.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * 加锁解锁的时候 不断的判断 是否加锁 或者解了锁
 */
public class SpinLockDemo {

    // int 0
    // Thread null
    AtomicReference<Thread> atomicReference = new AtomicReference<>();
    // 加锁
    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+ " ==>mylock");

        // 自旋锁
        while (!atomicReference.compareAndSet(null,thread)) {
            System.out.println(Thread.currentThread().getName()); //T2 一直在自旋
        }
    }

    // 解锁
    public void myUnLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+ " ==>myUnlock");

        atomicReference.compareAndSet(thread,null);
    }

}

class TestSpinLock {
    public static void main(String[] args) {
        SpinLockDemo lock = new SpinLockDemo();
        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnLock();
            }
        },"T1").start();

        new Thread(()->{
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.myUnLock();
            }
        },"T2").start();


    }
}
