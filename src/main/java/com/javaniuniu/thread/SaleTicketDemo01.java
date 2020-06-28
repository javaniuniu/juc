package com.javaniuniu.thread;

/**
 * @auther: javaniuniu
 * @date: 2020/6/27 7:55 PM
 */

/**
 * 真正的多线程并发，公司中的开发，降低耦合性
 * 线程就是一个单独的资源类，没有任何附属操作
 * 1、属性，方法
 */
public class SaleTicketDemo01 {

    public static void main(String[] args) {
        // 并发：多线程操作同一资源类，把资源类丢入线程
        Ticket ticket = new Ticket();

        // @FunctionalInterface 函数式编程
        new Thread(() ->{
            for (int i = 1; i <=60 ; i++) {
                ticket.salse();
            }

        },"A").start();
        new Thread(() ->{
            for (int i = 1; i <=60 ; i++) {
                ticket.salse();
            }
        },"B").start();
        new Thread(() ->{
            for (int i = 1; i <=60 ; i++) {
                ticket.salse();
            }
        },"C").start();
    }
}

// 资源类 OOP
class Ticket{
    // 属性，方法
    private int number = 50;

    // synchronized 本质 排队 锁
    public synchronized void salse() {
        if (number > 0) {
            System.out.println(
                    Thread.currentThread().getName() + "卖出了1票,剩余："+(--number) +"张票");
        }
    }
}