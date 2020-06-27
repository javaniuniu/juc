package com.javaniuniu.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 5、增加两个静态的同步方法，先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 *      - 先发短信
 * 6、两个对象，增肌阿两个静态的同步方法，先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 *      - 先发短信
 */
public class Test3 {

    public static void main(String[] args) {
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();
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
class Phone3 {

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
    public static synchronized void call() {
        System.out.println("打电话");
    }

}