package com.javaniuniu.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther: javaniuniu
 * @date: 2020/6/27 11:19 PM
 */
public class B {
    public static void main(String[] args) {
        Data2 data = new Data2();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i <10 ; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();

    }
}
// 资源类 独立耦合的
// 判断等待，业务，通知
class Data2 {

    // 属性和方法
    private int num = 0;
    Lock lock = new ReentrantLock();
    Condition condition =  lock.newCondition();
//    condition.await(); // 等待
//    condition.signalAll(); // 唤醒全部

    // + 1
    public void increment() throws InterruptedException {
        lock.lock();
        try {
            // 业务代码
            while (num !=0){
                // 等待
                condition.await();
            }
            num ++;
            System.out.println(Thread.currentThread().getName()+"==>"+num);
            // 通知其他线程 我 + 1 完成了
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
    // - 1
    public void decrement() throws InterruptedException {
        lock.lock();
        try {
            while (num ==0){
                // 等待
                condition.await();
            }
            num --;
            System.out.println(Thread.currentThread().getName()+"==>"+num);
            // 通知其他线程 我 - 1 完成了
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}