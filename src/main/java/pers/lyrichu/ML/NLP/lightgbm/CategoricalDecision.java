package pers.lyrichu.ML.NLP.lightgbm;

/**
 * @author lwj
 */
public class CategoricalDecision<T extends Comparable<T>> extends Decision<T> {
    public boolean decision(T fval, T threshold) {
        if (((Integer) fval).intValue() == ((Integer) threshold).intValue()) {
            return true;
        } else {
            return false;
        }
    }
}
