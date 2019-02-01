package pers.lyrichu.java.basic.thread;

class Count {
    // 同步方法
    public void synchronizedMethod() {
        synchronized (this) {
            try {
                for (int i = 0;i<5;i++) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " loop " + i);
                }
            } catch (InterruptedException e) {
                System.err.println("synchronizedMethod sleep is interrupted:"+e);
            }
        }
    }

    // 非同步方法
    public void nonSynchronizedMethod() {
        try {
            for (int i = 0;i<5;i++) {
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " loop " + i);
            }
        } catch (InterruptedException e) {
            System.err.println("nonSynchronizedMethod sleep is interrupted:"+e);
        }
    }
}

public class SynchronizedVsNonSynchronized {
    public static void main(String[] args) {
        Count count = new Count();
        /*
         * 访问对象的同步代码的同时,同一个对象的非同步代码块的访问
         * 不会阻塞
         */
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                // 调用count的同步方法
                count.synchronizedMethod();
            }
        },"t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                count.nonSynchronizedMethod();
            }
        },"t2");
        t1.start();
        t2.start();
    }
}