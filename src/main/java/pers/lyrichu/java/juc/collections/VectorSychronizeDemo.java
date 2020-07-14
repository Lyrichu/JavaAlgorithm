package pers.lyrichu.java.juc.collections;
import java.util.Arrays;
import	java.util.concurrent.TimeUnit;

import java.util.Vector;

public class VectorSychronizeDemo {

  private static Vector<Integer> vector = new Vector<Integer> (Arrays.asList(1,2,3));

  public static void main(String[] args) throws Exception {
    Thread t1 = new Thread(new IteratorRunnable());
    t1.start();
    TimeUnit.SECONDS.sleep(2);
    Thread t2 = new Thread(new ModifyRunnable());
    t2.start();
  }

  private static class IteratorRunnable implements Runnable {

    @Override
    public void run() {
      // 这里必须手动 sychronize 一下，不然会 报 ConcurrentModificationException
      while (true) {
        synchronized (vector) {
          for (Integer i : vector) {

          }
        }
      }
    }
  }

  private static class ModifyRunnable implements Runnable {

    @Override
    public void run() {
      while (true) {
        vector.add(1);
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("vector size:" + vector.size());
      }
    }
  }
}
