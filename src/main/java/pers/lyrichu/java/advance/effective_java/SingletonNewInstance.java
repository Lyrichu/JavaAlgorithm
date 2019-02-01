package pers.lyrichu.java.advance.effective_java;
/*
 *@ClassName SingletonNewInstance
 *@Description 提供一个共有的静态工厂函数来构造单例模式
 *@Author lyrichu
 *@Date 18-11-29
 *@Version 1.0
 */

public class SingletonNewInstance {
    private static final SingletonNewInstance SINGLE_INSTANCE = new SingletonNewInstance();
    private SingletonNewInstance(){}
    // 静态工厂函数
    public static SingletonNewInstance singleton() {
        return SINGLE_INSTANCE;
    }
}
