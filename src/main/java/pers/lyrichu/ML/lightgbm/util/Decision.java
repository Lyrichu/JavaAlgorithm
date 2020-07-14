package pers.lyrichu.ML.lightgbm.util;

public abstract class Decision<T> {

  abstract boolean decision(T fval, T threshold);

}
