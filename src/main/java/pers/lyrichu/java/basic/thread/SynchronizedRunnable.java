package pers.lyrichu.java.basic.thread;

/*
 *实现Runnable 接口
 */
class MyRunnable implements Runnable {
    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0;i<5;i++) {
                System.out.println(Thread.currentThread().getName() + " loop "+i);
            }
        }
    }
}

public class SynchronizedRunnable {
    public static void main(String[] args) {
        Runnable run1 = new MyRunnable();
        // 根据 Runnable对象创建Thread
        Thread t1 = new Thread(run1,"t1");
        Thread t2 = new Thread(run1,"t2");
        // 开启线程,由于t1,t2两个线程都是从同一个Runnable
        // 对象得到的,所以加了synchronized关键字之后,一个线程在运行时
        // 另外一个线程只能阻塞等待
        t1.start();
        t2.start();
    }
}