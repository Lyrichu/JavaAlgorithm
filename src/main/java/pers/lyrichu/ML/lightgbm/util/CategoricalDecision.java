package pers.lyrichu.ML.lightgbm.util;

public class CategoricalDecision<T extends Comparable<T>> extends Decision<T> {

  boolean decision(T fval, T threshold) {
    if (((Integer) fval).intValue() == ((Integer) threshold).intValue()) {
      return true;
    } else {
      return false;
    }
  }
}
