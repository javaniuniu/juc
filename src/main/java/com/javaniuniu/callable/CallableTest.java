package com.javaniuniu.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @auther: javaniuniu
 * @date: 2020/6/28 2:15 AM
 */
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        new Thread(new Runnable()).start();
//        new Thread(new FutureTask<V>()).start(); FutureTask 是 Runnable 的实现类
//        new Thread(new FutureTask<V>(Callable )).start(); FutureTask 的构造函数调用  Callable


        MyThread thread = new MyThread();
        FutureTask futureTask = new FutureTask(thread); // 适配类
        new Thread(futureTask,"A").start();
        new Thread(futureTask,"B").start(); // 结果会被缓存，效率高 所以callable 只打印来一次返回值

        String string = (String) futureTask.get(); // 获取 Callable 的返回结果
        // futureTask.get() 为了获取返回值，可能会出现阻塞，所以把这个方法放在最后 或使用异步通信来处理
        System.out.println(string);


    }
}
class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        // 耗时操作 。。。

        return "隋东风";
    }
}
