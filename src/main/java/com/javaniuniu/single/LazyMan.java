package com.javaniuniu.single;

// 懒汉式单例
public class LazyMan {

    private LazyMan() {
        System.out.println(Thread.currentThread().getName() + " ok");
    }

    //  volatile 防止指令重排
    public volatile static LazyMan lazyMan ;

    // 给lazyMan 赋值 新生成的实体类同样需要被 static 修饰，以保证可见性
    // 双重检查锁模式 懒汉式单例 DCL懒汉式
    public static LazyMan getInstance() {
        // 加锁
        if (lazyMan == null) {
            synchronized (LazyMan.class) {
                if (lazyMan == null) {
                    lazyMan = new LazyMan();// 不是一个原子操作
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

        return lazyMan;
    }

    // 但线程下OK
    // 多线程下会有问题

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lazyMan.getInstance();
            }).start();
//        }
        }
    }

}
