package pers.lyrichu.java.advance.understanding_the_jvm;

/**
 * 线程死锁等待实例,可以开启jconsle查看检测死锁
 */
public class SynAddRunnable implements Runnable {
  private int a,b;
  public SynAddRunnable(int a,int b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public void run() {
    synchronized (Integer.valueOf(a)) {
      synchronized (Integer.valueOf(b)) {
        System.out.println(a+b);
      }
    }
  }

  public static void main(String[] args) {
    // 开启200个线程计算a+b
    for (int i = 0;i < 1000;i++) {
      new Thread(new SynAddRunnable(1,2)).start();
      new Thread(new SynAddRunnable(2,1)).start();
    }
  }
}
