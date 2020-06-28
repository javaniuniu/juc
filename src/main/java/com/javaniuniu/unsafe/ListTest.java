package com.javaniuniu.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @auther: javaniuniu
 * @date: 2020/6/28 1:24 AM
 */
// java.util.ConcurrentModificationException 并发修改异常
public class ListTest {
    public static void main(String[] args) {
        // 并发下 ArrayList 不安全吗 synchronized
        /**
         * 解决方案：
         * 1、List<String> list = new Vector<>();
         * 2、List<String> list = Collections.synchronizedList(new ArrayList<>());
         * 3、List<String> list = new CopyOnWriteArrayList<>();
         */
        // CopyOnWrite 写入是复制 COW ，计算机程序设计领域的一种优化策略
        // 有多个线程调用的时候，list，读取的时候，固定，写入（覆盖）
        // 在写入的时候避免覆盖，造成数据问题
        // CopyOnWriteArrayList 比 Vector 好用在哪里
        //      -  CopyOnWriteArrayList 使用 ReentrantLock 同步锁
        //      -  Vector 使用 synchronized 同步锁
        //      -  ReentrantLock 性能优于 synchronized
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i <10 ; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
