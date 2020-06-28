package com.javaniuniu.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用 可以想象 Ajax
 * 异步执行
 * 成功回调
 * 失败回调
 */
public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        test01();
        test02();
    }

    // 没有返回值的异步回调
    public static void test01() throws ExecutionException, InterruptedException {
        // 没有返回值的异步回调
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ "runAsync=>Void");
        });

        System.out.println("1111");
        completableFuture.get(); // 获取阻塞执行结果

    }

    // 有返回值的异步回调
    // 类似 ajax 成功和失败都要回调
    //
    public static void test02() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ "supplyAsync=>Void");
//            int i = 10/0;
            return 1024;

        });
        completableFuture.whenComplete((t,u)->{
            System.out.println("t:==> " +t); // 正常的返回结果
            System.out.println("u:==> " +u); // 错误信息 java.util.concurrent.ExecutionException: java.lang.ArithmeticException: / by zero
        }).exceptionally(e ->{
            System.out.println(e.getMessage());
            return 233; // 可以获取到错误的返回结果
        });



        System.out.println("1111");
        completableFuture.get(); // 获取阻塞执行结果

    }

}
