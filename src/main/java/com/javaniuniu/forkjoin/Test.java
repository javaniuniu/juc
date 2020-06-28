package com.javaniuniu.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

/**
 * 求和计算的任务
 * 解决方案
 * 1、普通for 循环 适合循环次数较少计算
 * 2、forkjon 适合循环次数较大计算
 * 3、Stream 并行流
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test1(); // sum= 499999999500000000 时间 8835
//        test2(); // sum= 500000000500000000 时间 5893
        test3(); // sum= 500000000500000000 时间 310
    }

    // 普通for 循环 适合循环次数较少计算
    public static void test1() {
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (Long i = 0L; i <10_0000_0000 ; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum= "+sum+" 时间 "+(end-start));
    }

    // forkjon 适合循环次数较大计算
    public static void test2() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> task = new ForkJoinDemo(0L,10_0000_0000L);
        ForkJoinTask<Long> submit = forkJoinPool.submit(task);// 提交任务
        Long sum = submit.get();
        long end = System.currentTimeMillis();
        System.out.println("sum= "+sum+" 时间 "+(end-start));
    }

    // Stream 并行流
    public static void test3() {
        long start = System.currentTimeMillis();

        long sum = LongStream.rangeClosed(0L, 10_0000_0000).parallel().reduce(0, Long::sum);



        long end = System.currentTimeMillis();
        System.out.println("sum= "+sum +" 时间 "+(end-start));
    }
}
