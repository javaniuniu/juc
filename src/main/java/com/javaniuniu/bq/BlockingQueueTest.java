package com.javaniuniu.bq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
        test1();
    }
    // 抛出异常
    public synchronized static void test1() {
        // 队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.add("A"));
        System.out.println(blockingQueue.add("B"));
        System.out.println(blockingQueue.add("C"));

        // IllegalStateException: Queue full 抛出异常
//        System.out.println(blockingQueue.add("D"));
        System.out.println("====================");

        System.out.println(blockingQueue.element());// 查看对首元素

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // java.util.NoSuchElementException 抛出异常
//        System.out.println(blockingQueue.remove());



    }

    // 有返回值 没有异常
    public static void test2() {
        // 队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        System.out.println(blockingQueue.offer("A"));
        System.out.println(blockingQueue.offer("B"));
        System.out.println(blockingQueue.offer("C"));

        // false 不抛出异常
//        System.out.println(blockingQueue.offer("D"));
        System.out.println("====================");

        System.out.println(blockingQueue.peek());// 查看对首元素

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // null 不抛出异常
//        System.out.println(blockingQueue.poll());

    }

    // 等待，阻塞（一直阻塞）
    public static void test3() throws InterruptedException {
        // 队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);


        blockingQueue.put("A");
        blockingQueue.put("B");
        blockingQueue.put("C");

        // 一直阻塞
//        blockingQueue.put("D");
        System.out.println("====================");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        // 一直阻塞
//        System.out.println(blockingQueue.take());

    }


    // 等待，阻塞（等待超时）
    public static void test4() throws InterruptedException {
        // 队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue(3);


        blockingQueue.offer("A");
        blockingQueue.offer("B");
        blockingQueue.offer("C");
        blockingQueue.offer("D",2, TimeUnit.SECONDS); // 等待 超过2秒 退出

        System.out.println("====================");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());


        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS)); // 等待 超过2秒 退出

    }

}
