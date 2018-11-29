package pers.lyrichu.java.advance.effective_java;
/*
 *@ClassName SingletonPrivateFinal
 *@Description 单例模式的一种构建方法
 *@Author lyrichu
 *@Date 18-11-29
 *@Version 1.0
 */

public class SingletonPrivateFinal {
    public static final SingletonPrivateFinal SINGLE_INSTANCE = new SingletonPrivateFinal();
    // 私有的构造函数
    private SingletonPrivateFinal(){}

}
