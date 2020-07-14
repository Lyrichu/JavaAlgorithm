package pers.lyrichu.ML.nd4j;

import java.util.Random;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

/**
 * nd4j 的基础使用
 */
public class Demo1 {
  private static Random random = new Random();
  private static final int dim = 256;
  private static final int N = 200000;

  public static void main(String[] args) {
    double[] arr1 = new double[N * dim];
    for (int i = 0;i < N * dim;i++) {
      arr1[i] = random.nextDouble();
    }
    double[] arr2 = new double[dim];
    for (int i = 0;i < dim;i++) {
      arr2[i] = random.nextDouble();
    }

    // create a matrix,shape:(N,dim)
    INDArray matrix = Nd4j.create(arr1,new int[]{N,dim});
    // create a col vector,shape:(dim,1)
    INDArray v = Nd4j.create(arr2,new int[]{dim,1});
    long start = System.currentTimeMillis();
    INDArray rDot = matrix.mmul(v); // shape:(N,1)
    System.out.println(rDot.shape()[0]);
    System.out.println("cost " + (System.currentTimeMillis() - start) + " ms");
  }
}
