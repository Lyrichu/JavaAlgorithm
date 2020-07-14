package pers.lyrichu.ML.NLP.lightgbm;

import java.util.List;


/**
 * @author lwj
 */
public abstract class PredictFunction {
    abstract List<Double> predict(SparseVector vector);
}
