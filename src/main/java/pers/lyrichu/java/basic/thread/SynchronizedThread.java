package pers.lyrichu.java.basic.thread;

class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        // for loop
        synchronized (this) {
            for (int i = 0;i<5;i++) {
                System.out.println(Thread.currentThread().getName() + " loop " + i);
            }
        }

    }
}

public class SynchronizedThread {
    public static void main(String[] args) {
        Thread t1 = new MyThread("t1");
        Thread t2 = new MyThread("t2");
        /*
         * t1和t2是两个不同的对象,所以synchronized关键字在此失效,
         * 同步失效
         */
        t1.start();
        t2.start();
    }
}