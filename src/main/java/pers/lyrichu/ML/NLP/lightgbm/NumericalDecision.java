package pers.lyrichu.ML.NLP.lightgbm;

/**
 * @author lwj
 */
public class NumericalDecision<T extends Comparable<T>> extends Decision<T> {
    public boolean decision(T fval, T threshold) {
        if (fval.compareTo(threshold) <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
