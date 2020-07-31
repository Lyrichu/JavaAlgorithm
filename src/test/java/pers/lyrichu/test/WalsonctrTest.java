package pers.lyrichu.test;

import org.junit.Test;

public class WalsonctrTest {

  @Test
  public void test() {
    System.out.println(walsonCtr(298,27797));
    System.out.println(walsonCtr(1,1213));
    System.out.println(walsonCtr(0,10));
  }

  public static double walsonCtr(int clickCount,int exposeCount,double z) {
    if (exposeCount <= 0) {
      return 0.0;
    }
    double p = (double) clickCount / exposeCount;
    if (p > 0.9) {
      return 0.001;
    }
    double A = p + z * z / (2 * exposeCount);
    double B = Math.sqrt(p * (1 - p) / exposeCount + z * z / (4 * exposeCount * exposeCount));
    double C = z * B;
    double D = 1 + z * z / exposeCount;
    double ctr = (A - C) / D;
    return ctr;
  }

  public static double walsonCtr(int clickCount,int exposeCount) {
    return walsonCtr(clickCount,exposeCount,1.96);
  }
}
