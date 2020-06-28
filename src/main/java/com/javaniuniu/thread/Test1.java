package com.javaniuniu.thread;

/**
 * @auther: javaniuniu
 * @date: 2020/6/27 7:33 PM
 */
public class Test1 {
    public static void main(String[] args) {
        new Thread().start();
        // 获取CPU核数
        // CPU 密集型，IO密集型
        System.out.println(Runtime.getRuntime().availableProcessors());

    }
}
