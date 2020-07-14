package pers.lyrichu.test;

import java.util.Random;

/**
 * 随机采样测试
 */
public class RandomeSampleTest {
  private static Random random = new Random();

  public static void main(String[] args) {
    double[] values = {1,2,3,4,5,6,9,10};
    int index = randomSampleIndex(values);
    System.out.println(index);
  }

  public static int randomSampleIndex(double[] values) {
    try {
      // 首先归一化
      double sum = 0;
      int n = values.length;
      for (int i = 0;i < n;i++) {
        sum += values[i];
      }
      for (int i = 0;i < n;i++) {
        values[i] /= sum;
      }
      double r = random.nextDouble();
      double p = 0;
      for (int i = 0;i < n;i++) {
        if (r >= p && r < p + values[i]) {
          return i;
        }
        p += values[i];
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

}
