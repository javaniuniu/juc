package com.javaniuniu.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


// java.util.ConcurrentModificationException
public class MapTest {
    public static void main(String[] args) {
        /**
         * 解决方案
         * 1、Map<String,String> map = Collections.synchronizedMap(new HashMap<>());
         * 2、Map<String,String> map = new ConcurrentHashMap<>();
         */
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i <10 ; i++) {
            new Thread(()-> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,5));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
