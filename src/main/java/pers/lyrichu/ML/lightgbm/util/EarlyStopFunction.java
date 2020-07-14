package pers.lyrichu.ML.lightgbm.util;

public interface EarlyStopFunction {

  boolean callback(double[] d, int i);

}
