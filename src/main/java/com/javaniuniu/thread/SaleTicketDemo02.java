package com.javaniuniu.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @auther: javaniuniu
 * @date: 2020/6/27 9:59 PM
 */
public class SaleTicketDemo02 {

    public static void main(String[] args) {
        // 并发：多线程操作同一资源类，把资源类丢入线程
        Ticket2 ticket = new Ticket2();

        // @FunctionalInterface 函数式编程
        new Thread(() ->{ for (int i = 1; i <=60 ; i++) ticket.salse();},"A").start();
        new Thread(() ->{ for (int i = 1; i <=60 ; i++) ticket.salse();},"B").start();
        new Thread(() ->{ for (int i = 1; i <=60 ; i++) ticket.salse();},"C").start();

    }
}

// lock 三部曲
// 1、 new ReentrantLock()
// 2、 lock.lock(); // 加锁
// 3、 lock.unlock(); // 解锁
class Ticket2{
    // 属性，方法
    private int number = 30;

    Lock lock = new ReentrantLock();

    // synchronized 本质 排队 锁
    public synchronized void salse() {
        lock.lock(); // 加锁


        try {
            if (number > 0) {
                System.out.println(
                        Thread.currentThread().getName() + "卖出了1票,剩余："+(--number) +"张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); // 解锁
        }

    }
}