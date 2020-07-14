package pers.lyrichu.ML.lightgbm.util;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Boosting implements Serializable {

  private static final Logger logger = LoggerFactory.getLogger(Boosting.class);
  private static final long serialVersionUID = -3370589073161617590L;

  public static Boosting createBoosting(String modelStr) {
    Boosting boosting = new GBDT();
    loadFileToBoosting(boosting, modelStr);
    return boosting;
  }

  static boolean loadFileToBoosting(Boosting boosting, String modelStr) {
    if (boosting != null) {
      if (!boosting.loadModelFromString(modelStr))
        return false;
    }

    return true;
  }

  static String getBoostingTypeFromModelFile(String modelStr) {
    return modelStr.split("\n")[0];
  }

  abstract boolean loadModelFromString(String modelStr);

  abstract boolean needAccuratePrediction();

  abstract int numberOfClasses();

  abstract void initPredict(int num_iteration);

  abstract int numPredictOneRow(int num_iteration, boolean is_pred_leaf);

  abstract int getCurrentIteration();

  abstract int maxFeatureIdx();

  abstract List<Double> predictLeafIndex(SparseVector vector);

  abstract List<Double> predictRaw(SparseVector vector, PredictionEarlyStopInstance early_stop);

  abstract List<Double> predict(SparseVector vector, PredictionEarlyStopInstance early_stop);
}
