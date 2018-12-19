package pers.lyrichu.java.basic.thread;

/*
 * basic thread demo
 */
public class MyThreadDemo extends Thread {
    public MyThreadDemo(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is running!");
    }

    public static void main(String[] args) {
        MyThreadDemo myThreadDemo = new MyThreadDemo("mythread");
        // 直接调用run(),会在当前线程(即main thread)中调用
        System.out.println(Thread.currentThread().getName()+" is called!");
        myThreadDemo.run();
        // 调用start方法,会在设定的线程中进行调用run()方法
        myThreadDemo.start();
    }
}