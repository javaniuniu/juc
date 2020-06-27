### 多线程进阶==>JUC编发编程

#### 1、什么是JUC 

[java.util.concurrent](https://tool.oschina.net/uploads/apidocs/jdk-zh/java/util/concurrent/package-frame.html) 

Thread

1. Runable 没有返回值，效率相比 Callable 相对较低

#### 2、线程和线程
1. 进程：一个程序 QQ Music 程序的集合
2. 线程：java默认2个线程 main GC

对java而言 Thread Runable Callable

__java真的可以开启线程吗？__ 开不了
```java
public synchronized void start() {
    /**
     * This method is not invoked for the main method thread or "system"
     * group threads created/set up by the VM. Any new functionality added
     * to this method in the future may have to also be added to the VM.
     *
     * A zero status value corresponds to state "NEW".
     */
    if (threadStatus != 0)
        throw new IllegalThreadStateException();

    /* Notify the group that this thread is about to be started
     * so that it can be added to the group's list of threads
     * and the group's unstarted count can be decremented. */
    group.add(this);

    boolean started = false;
    try {
        start0();
        started = true;
    } finally {
        try {
            if (!started) {
                group.threadStartFailed(this);
            }
        } catch (Throwable ignore) {
            /* do nothing. If start0 threw a Throwable then
              it will be passed up the call stack */
        }
    }
}
// 本地方法，底层调用C++ java无法直接操作硬件
private native void start0();
```



> 并发 并行   

并发编程：并发，并行
并发：多线程操作同一个资源
并行：多核cpu下，多个cpu同时处理

并发编程的本质：__充分利用CPU的资源__



> 线程有几个状态？

```java
public enum State {
  NEW, //新生
  RUNNABLE, // 运行
  BLOCKED, // 阻塞
  WAITING, // 等待 死死的等
  TIMED_WAITING, // 超时等待
  TERMINATED; // 终止
}
```



> wait 和sleep 的区别

__1、来自不同的类__

wait ==> Object

sleep ==> Thread

__2、关于锁的释放__

wait： 会释放 

sleep： 睡觉了，抱着睡觉，不会释放

__3、使用的范围不同__

wait： 必须在同步代码块中国呢

sleep：可以在任何地方使用

#### 3、Lock锁（终点）

> synchronized

```java
package com.javaniuniu.demo01;

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
```

> Lock

- [ReentrantLock](https://www.matools.com/file/manual/jdk_api_1.8_google/java/util/concurrent/locks/ReentrantLock.html) 可重入锁
- [ReentrantReadWriteLock.ReadLock](https://www.matools.com/file/manual/jdk_api_1.8_google/java/util/concurrent/locks/ReentrantReadWriteLock.ReadLock.html) 读锁
- [ReentrantReadWriteLock.WriteLock](https://www.matools.com/file/manual/jdk_api_1.8_google/java/util/concurrent/locks/ReentrantReadWriteLock.WriteLock.html) 写锁

1、[ReentrantLock](https://www.matools.com/file/manual/jdk_api_1.8_google/java/util/concurrent/locks/ReentrantLock.html)

NonfairSync ：__非公平锁 ，可插队 （默认）__

FairSync：公平锁 ，不可插队

```java
package com.javaniuniu.demo01;

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
```



> synchronized 和Lock 的区别？

1、synchronized 内置关键字，Lock 是一个java类

2、synchronized 无法判断获取锁的状态， Lock 可以判断是否获取了锁

3、synchronized 会自动释放锁，Lock 必须要手动释放锁，如果不释放，就会__死锁__

4、synchronized 线程1 （获取锁，阻塞），线程2（等待，一直等）；lock不一定会等待下去,可以通过`（lock.tryLock()`  获取锁

5、synchronized 可重入锁，不可以中断的非公平锁，Lock 可以重入锁，可以判断锁释放中段，非公平（可以自己设置）

6、synchronized 适用少量的同步代码，Lock 适用大量的同步锁



> 锁是什么，如何判断锁的是谁？





#### 4、生产者和消费者

面试题：单例模式，排序算法，生产者和消费者，死锁

> synchronized 版

```java
package com.javaniuniu.pc;


/**
 * 线程之间通信稳定：生产者和消费者问题
 * 线程交替执行 A B 操作同一个变量 num=0
 * A num + 1
 * B num - 1
 */
public class A {
    public static void main(String[] args) {
        Data data = new Data();
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
    }
}
// 资源类 独立耦合的
// 判断等待，业务，通知
class Data {

    // 属性和方法
    private int num = 0;

    // + 1
    public synchronized void increment() throws InterruptedException {
        if (num !=0){
            // 等待
            this.wait();
        }
        num ++;
        System.out.println(Thread.currentThread().getName()+"==>"+num);
        // 通知其他线程 我 + 1 完成了
        this.notifyAll();
    }
    // - 1
    public synchronized void decrement() throws InterruptedException {
        if (num ==0){
            // 等待
            this.wait();
        }
        num --;
        System.out.println(Thread.currentThread().getName()+"==>"+num);
        // 通知其他线程 我 - 1 完成了
        this.notifyAll();
    }


}
```

> 存在  A B C D 四个线程 会出现异常， 虚假唤醒，怎么解决？

![image-20200627230238937](./image-20200627230238937.png)



__if 改成while__

![image-20200627231011536](./image-20200627231011536.png)

```java
package com.javaniuniu.pc;

/**
 * 线程之间通信稳定：生产者和消费者问题
 * 线程交替执行 A B 操作同一个变量 num=0
 * A num + 1
 * B num - 1
 */
public class A {
    public static void main(String[] args) {
        Data data = new Data();
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
class Data {

    // 属性和方法
    private int num = 0;

    // + 1
    public synchronized void increment() throws InterruptedException {
        while (num !=0){
            // 等待
            this.wait();
        }
        num ++;
        System.out.println(Thread.currentThread().getName()+"==>"+num);
        // 通知其他线程 我 + 1 完成了
        this.notifyAll();
    }
    // - 1
    public synchronized void decrement() throws InterruptedException {
        while (num ==0){
            // 等待
            this.wait();
        }
        num --;
        System.out.println(Thread.currentThread().getName()+"==>"+num);
        // 通知其他线程 我 - 1 完成了
        this.notifyAll();
    }
}
```



> Lock 版 生产者消费者

`Condition`因素出`Object`监视器方法（ [`wait`](https://www.matools.com/file/manual/jdk_api_1.8_google/java/lang/Object.html#wait--) ， [`notify`](https://www.matools.com/file/manual/jdk_api_1.8_google/java/lang/Object.html#notify--)和[`notifyAll`](https://www.matools.com/file/manual/jdk_api_1.8_google/java/lang/Object.html#notifyAll--) ）成不同的对象，以得到具有多个等待集的每个对象，通过将它们与使用任意的组合的效果[`Lock`个](https://www.matools.com/file/manual/jdk_api_1.8_google/java/util/concurrent/locks/Lock.html)实现。 `Lock`替换`synchronized`方法和语句的使用， `Condition`取代了对象监视器方法的使用。



```java
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
```

__得到的结果是随机的结果 ，我们希望 A->B->C->D 顺序执行__

> Condition 精准的通知和唤醒线程

```java
package com.javaniuniu.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


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
```

#### 5、八锁现象

如何判断锁的是谁，知道什么是锁，锁的是谁

锁的是对象，class

**深刻理解什么是锁**

```java
package com.javaniuniu.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 8锁，关于锁的8个问题
 * 1、标准情况下，两个线程先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 * 1、sendSms 延迟4秒，两个线程先打印 发短信 还是 打电话 ？ 1、发短信 2、打电话？
 */
public class Test1 {

    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            phone.sendSms();
        },"A").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            phone.call();
        },"B").start();
    }
}
class Phone {

    // synchronized 锁的是方法的对象，即调用者
    // 两个方法用的是同一个锁，谁先拿到谁先执行
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");

    }
    public synchronized void call() {
        System.out.println("打电话");
    }
}
```



```java
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
```

```java
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
```

```java
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
```



> 小结

new this 指的是具体的手机

static Class 唯一的一个模版



#### 6、集合类不安全

> List 不安全