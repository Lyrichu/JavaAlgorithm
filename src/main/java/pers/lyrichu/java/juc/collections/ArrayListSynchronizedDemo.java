package pers.lyrichu.java.juc.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ArrayListSynchronizedDemo {

  // 通过查看源码可以知道,Collections.synchronizedList 加锁的对象(mutex) 实际上就是返回的 list 本身
  private static final List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3));
  private static final Object key = new Object();

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
        // 这里必须使用 list 作为锁,而不是其他的 object
        // 原因是:只有这样 当执行到这个代码块的时候，另外一个线程的 list 的 add
        // 方法 加的锁 和 这里的锁是同一个(都是list),才能实现真正的线程安全
        // 如果不使用 list 作为锁,比如使用其他的object,那么此处两个地方都要 加锁
        synchronized (key) {
          for (Integer i : list) {

          }
        }
      }
    }
  }

  private static class ModifyRunnable implements Runnable {

    @Override
    public void run() {
      while (true) {
        synchronized (key) {
          list.add(1);
        }
        try {
          TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("list size:" + list.size());
      }
    }
  }
}
