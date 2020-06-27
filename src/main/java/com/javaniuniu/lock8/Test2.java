package com.javaniuniu.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 3、增加普通方法（hello() ）， 先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 *      - sendSms 延迟了4 秒 所以先输出 hello
 * 4、两个对象，两个同步方法 先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 *      - sendSms 延迟了4 秒 所以先输出 打电话
 */
public class Test2 {

    public static void main(String[] args) {
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();
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
class Phone2 {

    // synchronized 锁的是方法的对象，即调用者
    // 两个方法用的是同一个锁，谁先拿到谁先执行
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");

    }
    public synchronized void call() {
        System.out.println("打电话");
    }

    // 这里没有锁，不是同步方法，不受锁的影响
    public void hello() {
        System.out.println("hello");
    }
}