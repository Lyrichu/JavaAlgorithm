package pers.lyrichu.java.advance.understanding_the_jvm;

/**
 * 测试jvm 的 空间分配担保策略
 */
public class HandlePromotion {
  private static final int _1M = 1024 * 1024;
  /**
   * 运行时的jvm参数: -Xms20M -Xmx20M -Xmn10M -XX:PrintGCDetails -XX:SurvivorRatio=8 -XX:-HandlePromotionFailure
   */
  public static void main(String[] args) {
    testHandlePromotion();
  }

  @SuppressWarnings("unused")
  public static void testHandlePromotion() {
    byte[] allocation1,allocation2,allocation3,allocation4,allocation5,allocation6,allocation7;
    allocation1 = new byte[2*_1M];
    allocation2 = new byte[2*_1M];
    allocation3 = new byte[2*_1M];
    allocation1 = null;
    allocation4 = new byte[2*_1M];
    allocation5 = new byte[2*_1M];
    allocation6 = new byte[2*_1M];

    allocation4 = null;
    allocation5 = null;
    allocation6 = null;
    allocation7 = new byte[2*_1M];
  }
}
