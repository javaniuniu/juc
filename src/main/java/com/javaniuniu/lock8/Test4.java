package com.javaniuniu.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 7、一个静态普通同步方法 一个普通同步方法，一个对象 ，先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 *      - 先打电话 因为 sendSms 睡了4秒 ，且两个不是同一把锁
 * 8、一个静态普通同步方法 一个普通同步方法，两个对象，先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 *      - 先打电话 因为 sendSms 睡了4秒 ，且两个不是同一把锁
 */
public class Test4 {

    public static void main(String[] args) {
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();
        new Thread(()->{
            phone1.sendSms();
        },"A").start();

//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        new Thread(()->{
            phone2.call();
        },"B").start();
    }
}
class Phone4 {

    // synchronized 锁的是方法的对象，即调用者
    // static 静态方法
    // 类一加载就有了 锁的是 Class class 全局唯一
    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");

    }
    // 普通同步方法
    public synchronized void call() {
        System.out.println("打电话");
    }

}