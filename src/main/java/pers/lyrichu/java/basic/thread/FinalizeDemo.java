package pers.lyrichu.java.basic.thread;

/**
 * 实验 finalize 方法
 */
public class FinalizeDemo {

  public static void main(String[] args) throws Exception {
    FinalizeDemo demo = new FinalizeDemo();
    demo = null;
    System.gc();
    Thread.sleep(1000);
  }

  /**
   * 覆写 object 的 finalize 方法
   */
  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    System.out.println("finalize method is being called!");
  }
}
