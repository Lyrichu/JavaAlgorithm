package pers.lyrichu.ML.algebra;

import org.junit.Test;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;

/**
 * 一个java的矩阵运算库 ujmp 的使用范例
 */
public class UjmpDemo {

  @Test
  public void testCreateMatrix() {
    DenseMatrix matrix = DenseMatrix.Factory.zeros(10,10);
    // update values
    matrix.setAsDouble(1.2,0,1);
    matrix.setAsDouble(3.7, 3, 4);
    // print matrix
    System.out.println(matrix);
  }

  @Test
  public void testSparseMatrix() {
    Matrix matrix = SparseMatrix.Factory.zeros(300,500);
    // set value
    matrix.setAsDouble(10.4,10,20);
    System.out.println(matrix);
  }

  /**
   * 矩阵的基本操作
   */
  @Test
  public void testMatrixOps() {
    Matrix matrix = Matrix.Factory.rand(3,5);
    Matrix matrix1 = Matrix.Factory.rand(5,2);
    SparseMatrix sparse = SparseMatrix.Factory.zeros(3,5);
    sparse.setAsDouble(3.1,0,4);
    System.out.println("matrix:" + matrix);
    // transpose
    Matrix transpose = matrix.transpose();
    System.out.println("transpose:" + transpose);
    // matrix plus
    Matrix plus = matrix.plus(sparse);
    System.out.println("plus:" + plus);
    Matrix minus = matrix.minus(sparse);
    // matrix multi
    Matrix multi = matrix.mtimes(matrix1);
    System.out.println("multi:" + multi);
    // matrix scale
    Matrix scale = matrix.times(2);
    System.out.println("scale:" + scale);
    // matrix inv
    Matrix pinv = matrix.pinv();
    System.out.println("pinv:" + pinv);
    // matrix det
    Matrix matrix3 = Matrix.Factory.randn(6,6);
    double det = matrix3.det();
    System.out.println("matrix3 det:" + det);
  }

  @Test
  public void testMatrixMulSpeed() {
    int N = 100000;
    int dim = 256;
    // create random matrix
    Matrix m1 = Matrix.Factory.randn(N,dim);
    Matrix m2 = Matrix.Factory.randn(dim,1);
    long start = System.currentTimeMillis();
    // matrix multi
    Matrix m3 = m1.mtimes(m2);
    System.out.println("matrix multi cost "
        + (System.currentTimeMillis() - start) + " ms");
    System.out.println("m3 row:" + m3.getRowCount() + ",col:" + m3.getColumnCount());
  }
}
