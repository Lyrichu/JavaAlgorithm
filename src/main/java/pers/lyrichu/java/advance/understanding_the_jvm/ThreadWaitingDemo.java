package pers.lyrichu.java.advance.understanding_the_jvm;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 线程等待实例代码
 */
public class ThreadWaitingDemo {

  private static void createBusyThread() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          ;
        }
      }
    },"busyThread");
    thread.start();
  }

  /**
   * 线程锁等待实例
   * @param lock:锁对象
   */
  private static void createLockThread(final Object lock) {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        synchronized (lock) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    },"lockThread");
    thread.start();
  }

  public static void main(String[] args) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    reader.readLine();
    createBusyThread();
    reader.readLine();
    Object obj = new Object();
    createLockThread(obj);
  }
}
