package com.javaniuniu.single;

/**
 * 完整版 枚举单例模式
 */
public class User {
    //构造器私有化
    private User(){}

    static enum SingletonEnum{
        // 创建一个枚举对象，该对象天生为单例模式
        INSTANCE;
        private User user;
        // 私有化枚举的构造函数
        private SingletonEnum() {
            user = new User();
        }
        public User getInstance() {
            return user;
        }

    }
    //对外暴露一个获取User对象的静态方法
    public static User getInstance() {
        return SingletonEnum.INSTANCE.getInstance();
    }

    public static void main(String[] args) {
        System.out.println(User.getInstance());
        System.out.println(User.getInstance());
    }
}
