package pers.lyrichu.ML.lightgbm.util;

import java.util.Arrays;


public class SparseVector {

  private float[] values;
  private int[] indices;
  //private double[] leafs; 后续添加lgb mark用

  public SparseVector(float[] values, int[] indices) {
    this.values = values;
    this.indices = indices;
  }

  public double get(int idx, float defaultValue) {
    int pos = Arrays.binarySearch(indices, idx);
    if (pos < 0)
      return defaultValue;
    return values[pos];
  }
}
