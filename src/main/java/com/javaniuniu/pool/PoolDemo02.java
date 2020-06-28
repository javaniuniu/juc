package com.javaniuniu.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Executors 工具类，4大拒绝策略
 * new ThreadPoolExecutor.AbortPolicy() // 拒绝策略，对应，银行满来，还有人进来，不处理这个人，抛出异常
 * new ThreadPoolExecutor.CallerRunsPolicy() //  哪里来的去哪里 这里多出的线程 由 main 线程执行
 * new ThreadPoolExecutor.DiscardPolicy() // 队列满了，丢掉任务，不会抛出异常
 * new ThreadPoolExecutor.DiscardOldestPolicy() // 队列满了,尝试和最早的竞争，也不会抛出异常
 */
//  以银行为例
public class PoolDemo02 {
    public static void main(String[] args) {
        // 自定义线程池，工作中 使用 ThreadPoolExecutor
        // 通过设置参数 可知，银行同一时间 最大接待人数 5 + 3

        // 最大线程数应该是多少？
        // CPU 密集型 ，cpu几核，就是几，可以保持CPU 的效率最高
        // IO 密集型  ，一般设置两倍， 判断你的程序中 十分耗 IO 的线程数
        //     - 比如一个程序 15个大型业务， IO 十分占用资源

        // 获取 CPU 的核数
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = new ThreadPoolExecutor(
                2, // 银行核心柜台数
                5, // 最大柜台数
                3, // 等待超时时间 会释放线程
                TimeUnit.SECONDS, // 超时时间 秒
                new LinkedBlockingDeque<>(3), // 对应排队人数
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy() // 队列满了,尝试和最早的竞争，也不会抛出异常
                );
        try {
            // 最大承载： Deque + max
            // 超出 RejectedExecutionException
            for (int i = 1; i <=9 ; i++) {
                // 使用线程池后，使用线程池来创建线程
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName()+ " ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
