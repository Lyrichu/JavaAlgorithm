package pers.lyrichu.ML.NLP.lightgbm;

/**
 * @author lwj
 */
public interface EarlyStopFunction {
     boolean callback(double[] d, int i);
}
