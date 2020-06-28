package com.javaniuniu.unsafe;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;


// java.util.ConcurrentModificationException
public class SetTest {
    public static void main(String[] args) {
        /**
         * 解决方案：
         * 1、Set<String> list = Collections.synchronizedSet(new HashSet<>());
         * 2、Set<String> list = new CopyOnWriteArraySet();
         */

//        Set<String> list = new CopyOnWriteArraySet();
        Set<String> list = new HashSet<>();
        for (int i = 0; i <20 ; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
