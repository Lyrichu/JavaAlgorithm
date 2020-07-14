package pers.lyrichu.ML.algebra;
import	java.util.Random;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * 演示 apache math3 矩阵相关操作
 */
public class Math3MatrixDemo {
  private static final int N = 100000;
  private static final int dim = 256;
  private static Random random = new Random();

  public static void main(String[] args) {
    double[][] arr1 = new double[N][dim];
    double[][] arr2 = new double[dim][1];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < dim; j++) {
        arr1[i][j] = random.nextDouble();
      }
    }
    for (int i = 0;i < dim;i++) {
      arr2[i][0] = random.nextDouble();
    }

    // create matrix
    RealMatrix matrix1 = new Array2DRowRealMatrix(arr1);
    RealMatrix matrix2 = new Array2DRowRealMatrix(arr2);
    long start = System.currentTimeMillis();
    // dot product
    RealMatrix res = matrix1.multiply(matrix2);
    System.out.println("matrix multiply cost " + (System.currentTimeMillis() - start) + " ms.");
    System.out.println("row dim:" + res.getRowDimension() + ",col dim:" + res.getColumnDimension());
  }

}
