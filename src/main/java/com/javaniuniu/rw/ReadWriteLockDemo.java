package com.javaniuniu.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁 （写锁） 一次只能被一个线程占有
 * 贡献锁 （读锁） 多个线程可以同时占有
 * ReadWriteLock
 * 读-读 可以共存
 * 读-写 不可共存
 * 写-写 不可共存
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
//        MyCache cache = new MyCache();
        MyCacheLock cache = new MyCacheLock();

        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                cache.put(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int temp = i;
            new Thread(()->{
                cache.get(temp+"");
            },String.valueOf(i)).start();
        }
    }
}

// 加锁版
class MyCacheLock {
    private volatile Map<String,Object> map = new HashMap<>();
    // 读写锁，更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 存 写 , 写入的时候，只有一个线程写
    public void put(String key,Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"写入" +key);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"写入OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
    // 取 读,所有线程可读
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读取" +key);
            map.get(key);
            System.out.println(Thread.currentThread().getName()+"读取OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

/**
 * 自定义缓存
 */
class MyCache{
    private volatile Map<String,Object> map = new HashMap<>();

    // 存 写
    public void put(String key,Object value) {
        System.out.println(Thread.currentThread().getName()+"写入" +key);
        map.put(key,value);
        System.out.println(Thread.currentThread().getName()+"写入OK");
    }
    // 取 读
    public void get(String key) {
        System.out.println(Thread.currentThread().getName()+"读取" +key);
        map.get(key);
        System.out.println(Thread.currentThread().getName()+"读取OK");
    }
}