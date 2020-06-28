package com.javaniuniu.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther: javaniuniu
 * @date: 2020/6/28 11:58 PM
 */
public class CasDemo2 {

    // CAS compareAndSet : 比较并交换
    public static void main(String[] args) {

        // AtomicStampedReference 注意：如果范型是一个包装类，注意对象的引用问题
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference(1,1);

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp(); // 获取版本号
            System.out.println("a1=>"+stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 2,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a2=>"+atomicStampedReference.getStamp());

            System.out.println(atomicStampedReference.compareAndSet(2, 1,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("a3=>"+atomicStampedReference.getStamp());


        },"a").start();

        // 和乐观锁的原理相同
        new Thread(()->{
            int stamp = atomicStampedReference.getStamp(); // 获取版本号
            System.out.println("b1=>"+stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(1, 6,
                    atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1));
            System.out.println("b2=>"+atomicStampedReference.getStamp());

        },"b").start();

    }
}
