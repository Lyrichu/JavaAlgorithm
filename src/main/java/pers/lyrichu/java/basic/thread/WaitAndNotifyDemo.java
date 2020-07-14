package pers.lyrichu.java.basic.thread;

/**
 * 实验 object 的 wait/notify 方法
 */
public class WaitAndNotifyDemo {
  private static final Object lock = new Object();

  public static void main(String[] args) {

    Thread t1 = new Thread(
        () -> {
          synchronized (lock) {
            System.out.println("t1 get lock!");
            System.out.println("t1 start to wait and release lock...");
            long start = System.currentTimeMillis();
            try {
              lock.wait(1000);
            } catch (InterruptedException e) {
              System.err.println("t1 occur error:" + e);
            }
            System.out.println("t1 wake up,wait "
                + (System.currentTimeMillis() - start) + " ms." );
          }
        }
        );

    Thread t2 = new Thread(
        () -> {
          synchronized (lock) {
            System.out.println("t2 get lock!");
            System.out.println("t2 start to wait and release lock...");
            long start = System.currentTimeMillis();
            try {
              lock.wait(0);
            } catch (InterruptedException e) {
              System.err.println("t2 occur error:" + e);
            }
            System.out.println("t2 wake up,wait "
                + (System.currentTimeMillis() - start) + " ms." );
          }
        }
    );

    Thread t3 = new Thread(new Runnable() {

      @Override
      public void run() {
        synchronized (lock) {
          System.out.println("t3 get lock!");
          System.out.println("t3 start to sleep...");
          try {
            Thread.sleep(500);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println("t3 start to notify...");
          lock.notifyAll();
        }
      }
    });

    t1.start();
    t2.start();
    t3.start();


  }


}
