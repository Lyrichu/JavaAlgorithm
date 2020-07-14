package pers.lyrichu.ML.lightgbm.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LgbParser {

  public Predictor parse(String modelStr) throws IOException {
    Predictor predictor = null;
    try {
      Boosting boosting = Boosting.createBoosting(modelStr);
      Map<String, String> map = new HashMap<String, String>();
      OverallConfig config = new OverallConfig();
      config.set(map);
      predictor =
          new Predictor(boosting, config.io_config.num_iteration_predict, config.io_config.is_predict_raw_score,
              config.io_config.is_predict_leaf_index, config.io_config.pred_early_stop,
              config.io_config.pred_early_stop_freq, config.io_config.pred_early_stop_margin);
    } catch (Exception e) {
      throw new IOException("LGB MODEL LOAD ERROR not a file path.");

    }

    return predictor;
  }
}
