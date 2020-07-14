package pers.lyrichu.java.basic.thread;
import	java.util.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * reference:https://www.twle.cn/c/yufei/javatm/javatm-basic-forkjoin.html
 */
public class ForkJoinDemo {
  // 使用公共池
  private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
  private Random random = new Random();
  private CustomeRecursiveAction task1 = new CustomeRecursiveAction();
  private CustomRecursiveTask task2 = new CustomRecursiveTask();

  public static void main(String[] args) {
    ForkJoinDemo demo = new ForkJoinDemo();
    demo.compute();
  }

  private void compute() {
    task1.setWorkS("ahdgsudgusabxhsagdysadiuwdhsagdhsagdyasfvhg");
    int[] datas = new int[1000000];
    int oriSum = 0;
    for (int i = 0;i < 1000000;i++) {
      int a = random.nextInt(500);
      datas[i] = a;
    }
    long start = System.currentTimeMillis();
    for (int i = 0;i < datas.length; ++i) {
      oriSum += datas[i];
    }
    System.out.println("oriSum:" + oriSum);
    System.out.println("cost " + (System.currentTimeMillis() - start) + " ms");
    task2.setDatas(datas);
    forkJoinPool.invoke(task1);
    start = System.currentTimeMillis();
    int resSum = forkJoinPool.invoke(task2);
    System.out.println("cost " + (System.currentTimeMillis() - start) + " ms");
    System.out.println("result sum:" + resSum);
  }

  // 无返回值
  private class CustomeRecursiveAction extends RecursiveAction {

    private String workS = "";
    // 字符串长度大于4的时候则划分子任务(fork)
    private final int LEN_THRESHOLD = 4;

    public CustomeRecursiveAction(String workS) {
      this.workS = workS;
    }

    public CustomeRecursiveAction() {}

    public void setWorkS(String workS) {
      this.workS = workS;
    }

    public String getWorkS() {
      return workS;
    }

    @Override
    protected void compute() {
      if (workS.length() > LEN_THRESHOLD) {
        ForkJoinTask.invokeAll(createSubTasks());
      } else {
        process(workS);
      }
    }

    // 创建子任务
    private List<CustomeRecursiveAction> createSubTasks() {
      List<CustomeRecursiveAction> tasks = new ArrayList<>();
      tasks.add(new CustomeRecursiveAction(workS.substring(0,workS.length() / 2)));
      tasks.add(new CustomeRecursiveAction(workS.substring(workS.length() / 2)));
      return tasks;
    }

    private void process(String result) {
      System.out.println("Thread:" + Thread.currentThread().getName()
          + " is processing result:" + result);
    }
  }


  // 有返回值
  private class CustomRecursiveTask extends RecursiveTask<Integer> {

    private int[] datas;
    private final int THRESHOLD = 5;

    public CustomRecursiveTask(int[] datas) {
      this.datas = datas;
    }

    public CustomRecursiveTask() {}

    public int[] getDatas() {
      return datas;
    }

    public void setDatas(int[] datas) {
      this.datas = datas;
    }

    @Override
    protected Integer compute() {
      if (datas != null && datas.length > THRESHOLD) {
        // 返回一个 List<ForkJoinTask> 但不会立刻执行
        // 需要 join() 触发
        return ForkJoinTask.invokeAll(createSubTasks())
            .stream()
            .mapToInt(ForkJoinTask::join)
            .sum();
      } else {
        return process();
      }
    }

    private Collection<CustomRecursiveTask> createSubTasks() {
      List<CustomRecursiveTask> tasks = new ArrayList<> ();
      tasks.add(new CustomRecursiveTask(Arrays.copyOfRange(datas,0,datas.length / 2)));
      tasks.add(new CustomRecursiveTask(Arrays.copyOfRange(datas,datas.length / 2,datas.length)));
      return tasks;
    }

    private Integer process() {
      if (datas == null || datas.length == 0) {
        return 0;
      }
      return Arrays.stream(datas).sum();
    }

  }

}
