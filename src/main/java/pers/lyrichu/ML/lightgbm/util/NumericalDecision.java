package pers.lyrichu.ML.lightgbm.util;

public class NumericalDecision<T extends Comparable<T>> extends Decision<T> {

  boolean decision(T fval, T threshold) {
    if (fval.compareTo(threshold) <= 0) {
      return true;
    } else {
      return false;
    }
  }
}
