package pers.lyrichu.ML.lightgbm.util;

import java.util.HashMap;
import java.util.List;


public class Predictor {

  private PredictionEarlyStopInstance earlyStop;
  private Boosting boosting;
  private PredictFunction predictFun;
  public HashMap<String, Long> FEATURE_ID_DICT = new HashMap<String, Long>();

  public Predictor(Boosting boosting, int numIteration, boolean isRawScore, boolean isPredictLeafIndex,
                   boolean earlyStop, int earlyStopFreq, double earlyStopMargin) {

    this.earlyStop =
        PredictionEarlyStopInstance.createPredictionEarlyStopInstance("none", new PredictionEarlyStopConfig());
    if (earlyStop && !boosting.needAccuratePrediction()) {
      PredictionEarlyStopConfig pred_early_stop_config = new PredictionEarlyStopConfig();
      pred_early_stop_config.marginThreshold = earlyStopMargin;
      pred_early_stop_config.roundPeriod = earlyStopFreq;
      if (boosting.numberOfClasses() == 1) {
        this.earlyStop =
            PredictionEarlyStopInstance.createPredictionEarlyStopInstance("binary", pred_early_stop_config);
      } else {
        this.earlyStop = PredictionEarlyStopInstance.createPredictionEarlyStopInstance("multiclass",
            pred_early_stop_config);
      }
    }

    boosting.initPredict(numIteration);
    this.boosting = boosting;

    if (isPredictLeafIndex) {
      this.predictFun = new PredictFunction1();
    } else {
      if (isRawScore) {
        this.predictFun = new PredictFunction2();
      } else {
        this.predictFun = new PredictFunction3();
      }
    }
  }

  public List<Double> predict(SparseVector sparseVector) {
    return predictFun.predict(sparseVector);
  }

  public List<Double> getLeaf(SparseVector sparseVector) {
    return boosting.predictLeafIndex(sparseVector);
  }

  class PredictFunction1 extends PredictFunction {
    @Override
    List<Double> predict(SparseVector vector) {
      return boosting.predictLeafIndex(vector);
    }
  }

  class PredictFunction2 extends PredictFunction {
    @Override
    List<Double> predict(SparseVector vector) {
      return boosting.predictRaw(vector, earlyStop);
    }
  }

  class PredictFunction3 extends PredictFunction {
    @Override
    List<Double> predict(SparseVector vector) {
      return boosting.predict(vector, earlyStop);
    }
  }
}
