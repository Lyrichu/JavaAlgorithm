package pers.lyrichu.java.juc.Executors;

import java.util.Arrays;
import java.util.List;
import	java.util.concurrent.Callable;
import java.util.concurrent.Future;
import	java.util.concurrent.LinkedBlockingQueue;
import	java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

  private ExecutorService executor = new ThreadPoolExecutor(
      3,5,1000, TimeUnit.MILLISECONDS,
      new LinkedBlockingQueue<Runnable> ());

  public static void main(String[] args) {
    ThreadPoolExecutorDemo demo = new ThreadPoolExecutorDemo();
    demo.processMultiTasks();
  }

  /**
   * 处理多任务
   */
  private void processMultiTasks() {
    // lambda expression Runnable Task
    Runnable task1 = () -> {
      try {
        Thread.sleep(1000);
        System.out.println("finish task1!");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };
    Runnable task2 = () -> {
      try {
        Thread.sleep(2000);
        System.out.println("finish task2!");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    };

    // Callable Task
    Callable<String> task3 = () -> {
      Thread.sleep(500);
      return "finish task3!";
    };

    Callable<String> task4 = () -> {
      long count = 0;
      for (int i = 0;i < 5000000;i++) {
        count += i;
      }
      System.out.println("finish task4!");
      return String.valueOf(count);
    };
    List<Callable<String>> callableTasks = Arrays.asList(task3,task4);

    // executor runnable task
    executor.execute(task1);
    executor.execute(task2);

    // invoke all callable tasks
    try {
      // 1s timesout
      List<Future<String>> futures = executor.invokeAll(callableTasks,1000,TimeUnit.MILLISECONDS);
      for (Future<String> future : futures) {
        String r = future.get();
        System.out.println("result:" + r);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // close executor connection
    close();
  }

  private void close() {
    try {
      Thread.sleep(2000);
      executor.shutdown();
      if (!executor.awaitTermination(500,TimeUnit.MILLISECONDS)) {
        executor.shutdownNow();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
