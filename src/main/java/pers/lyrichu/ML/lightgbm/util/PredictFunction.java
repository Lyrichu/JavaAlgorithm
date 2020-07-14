package pers.lyrichu.ML.lightgbm.util;

import java.util.List;

public abstract class PredictFunction {

  abstract List<Double> predict(SparseVector vector);

}
