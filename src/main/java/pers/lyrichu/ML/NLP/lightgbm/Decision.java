package pers.lyrichu.ML.NLP.lightgbm;

/**
 * @author lwj
 */
public abstract class Decision<T> {
   public abstract boolean decision(T fval, T threshold);
}
