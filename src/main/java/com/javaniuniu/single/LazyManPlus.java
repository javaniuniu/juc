package com.javaniuniu.single;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * // 懒汉式单例
 *
 */
public class LazyManPlus {

    // 通过红绿灯的形式
    private static boolean flag = false;

    private LazyManPlus() {
        synchronized (LazyManPlus.class) {
            if (flag== false) {
                flag = true;
            }else {
                throw new RuntimeException("不要试图使用发射破坏异常");
            }
        }
    }

    //  volatile 防止指令重排
    public volatile static LazyManPlus lazyManPlus;

    // 给lazyMan 赋值 新生成的实体类同样需要被 static 修饰，以保证可见性
    // 双重检查锁模式 懒汉式单例 DCL懒汉式
    public static LazyManPlus getInstance() {
        // 加锁
        if (lazyManPlus == null) {
            synchronized (LazyManPlus.class) {
                if (lazyManPlus == null) {
                    lazyManPlus = new LazyManPlus();// 不是一个原子操作
                    /**
                     * 1、分配内存空间
                     * 2、执行构造方法，初始化对象
                     * 3、把这个对象指向这个空间
                     *
                     * 期望 123
                     * 可能 132 A线程
                     *          B线程 // 此时LazyMan 还没有完成构造
                     */
                }
            }
        }

        return lazyManPlus;
    }

    // 反射
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
//        LazyManPlus instance1 = LazyManPlus.getInstance();
        Field flag = LazyManPlus.class.getDeclaredField("flag");
        flag.setAccessible(true);

        Constructor<LazyManPlus> constructor = LazyManPlus.class.getDeclaredConstructor(null);
        constructor.setAccessible(true); // 无视私有构造器
        LazyManPlus instance2 = constructor.newInstance(); // 1. 一个对象使用发射 一个对象通过 new
        flag.set(instance2,false); // 3. 破坏红绿灯 使得可以继续执行
        LazyManPlus instance1 = constructor.newInstance(); // 2. 两个对象都通过反射

        System.out.println(instance1);
        System.out.println(instance2);

    }
}