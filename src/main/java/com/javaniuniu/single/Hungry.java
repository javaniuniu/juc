package com.javaniuniu.single;

// 饿汉式单例
// 一上来就是把所有的资源都加载进来，可能会浪费资源
public class Hungry {
    private byte[] date1 = new byte[1024*1024];
    private byte[] date2 = new byte[1024*1024];
    private byte[] date3 = new byte[1024*1024];
    private byte[] date4 = new byte[1024*1024];



    private Hungry(){}

    // 以上来 就会 new 一个对象
    private final static Hungry HUNGRY = new Hungry();

    public static Hungry getInstance() {
        return HUNGRY;
    }
}