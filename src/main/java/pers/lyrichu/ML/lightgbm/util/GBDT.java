package pers.lyrichu.ML.lightgbm.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GBDT extends Boosting {

  private static final Logger logger = LoggerFactory.getLogger(GBDT.class);
  private static final long serialVersionUID = -1459139427941842408L;
  List<Tree> models_ = new ArrayList<Tree>();
  int num_tree_per_iteration_;
  int num_class_;
  int label_idx_;
  int max_feature_idx_;
  boolean boost_from_average_;
  String[] feature_names_;
  String[] feature_infos_;
  ObjectiveFunction objective_function_;
  int num_iteration_for_pred_;
  int num_init_iteration_;
  int iter_;

  boolean loadModelFromString(String model_str) {
    models_.clear();
    String[] lines = model_str.split("\n");

    String line = Common.findFromLines(lines, "num_class=");
    if (line.length() > 0) {
      num_class_ = Integer.parseInt(line.split("=")[1]);
    } else {
      logger.error("Model file doesn't specify the number of classes");
      return false;
    }

    line = Common.findFromLines(lines, "num_tree_per_iteration=");
    if (line.length() > 0) {
      num_tree_per_iteration_ = Integer.parseInt(line.split("=")[1]);
    } else {
      num_tree_per_iteration_ = num_class_;
    }

    line = Common.findFromLines(lines, "label_index=");
    if (line.length() > 0) {
      label_idx_ = Integer.parseInt(line.split("=")[1]);
    } else {
      logger.error("Model file doesn't specify the label index");
      return false;
    }
    line = Common.findFromLines(lines, "max_feature_idx=");
    if (line.length() > 0) {
      max_feature_idx_ = Integer.parseInt(line.split("=")[1]);
    } else {
      logger.error("Model file doesn't specify max_feature_idx");
      return false;
    }
    line = Common.findFromLines(lines, "boost_from_average");
    if (line.length() > 0) {
      boost_from_average_ = true;
    }
    line = Common.findFromLines(lines, "feature_names=");
    if (line.length() > 0) {
      feature_names_ = line.substring("feature_names=".length()).split(" ");
      if (feature_names_.length != max_feature_idx_ + 1) {
        logger.error("Wrong size of feature_names");
        return false;
      }
    } else {
      logger.error("Model file doesn't contain feature names");
      return false;
    }

    line = Common.findFromLines(lines, "feature_infos=");
    if (line.length() > 0) {
      feature_infos_ = line.substring("feature_infos=".length()).split(" ");
      if (feature_infos_.length != max_feature_idx_ + 1) {
        logger.error("Wrong size of feature_infos");
        return false;
      }
    } else {
      logger.error("Model file doesn't contain feature infos");
      return false;
    }

    line = Common.findFromLines(lines, "objective=");

    if (line.length() > 0) {
      String str = line.split("=")[1];
      objective_function_ = ObjectiveFunction.createObjectiveFunction(str);
    }

    int i = 0;
    while (i < lines.length) {
      int find_pos = lines[i].indexOf("Tree=");
      if (find_pos >= 0) {
        ++i;
        int start = i;
        while (i < lines.length && !lines[i].contains("Tree=")) {
          ++i;
        }
        int end = i;
        String treeStr = Common.join(lines, start, end, "\n");
        Tree newTree = new Tree(treeStr);
        models_.add(newTree);
      } else {
        ++i;
      }
    }
    logger.info("Finished loading " + models_.size() + " models");
    num_iteration_for_pred_ = models_.size() / num_tree_per_iteration_;
    num_init_iteration_ = num_iteration_for_pred_;
    iter_ = 0;

    return true;
  }

  boolean needAccuratePrediction() {
    if (objective_function_ == null) {
      return true;
    } else {
      return objective_function_.needAccuratePrediction();
    }
  }

  int numberOfClasses() {
    return num_class_;
  }

  void initPredict(int num_iteration) {
    num_iteration_for_pred_ = models_.size() / num_tree_per_iteration_;
    if (num_iteration > 0) {
      num_iteration_for_pred_ = Math
          .min(num_iteration + (boost_from_average_ ? 1 : 0), num_iteration_for_pred_);
    }
  }

  int numPredictOneRow(int num_iteration, boolean is_pred_leaf) {
    int num_preb_in_one_row = num_class_;
    if (is_pred_leaf) {
      int max_iteration = getCurrentIteration();
      if (num_iteration > 0) {
        num_preb_in_one_row *= Math.min(max_iteration, num_iteration);
      } else {
        num_preb_in_one_row *= max_iteration;
      }
    }
    return num_preb_in_one_row;
  }

  int getCurrentIteration() {
    return models_.size() / num_tree_per_iteration_;
  }

  int maxFeatureIdx() {
    return max_feature_idx_;
  }

  List<Double> predictLeafIndex(SparseVector vector) {
    List<Double> outputs = new ArrayList<Double>();
    for (int i = 0; i < num_iteration_for_pred_; ++i) {
      for (int j = 0; j < num_class_; ++j) {
        outputs.add((double) models_.get(i * num_class_ + j).PredictLeafIndex(vector));
      }
    }
    return outputs;
  }

  List<Double> predictRaw(SparseVector features, PredictionEarlyStopInstance early_stop) {
    double[] output = new double[num_class_];
    int early_stop_round_counter = 0;
    for (int i = 0; i < num_iteration_for_pred_; ++i) {
      for (int k = 0; k < num_tree_per_iteration_; ++k) {
        output[k] += models_.get(i * num_tree_per_iteration_ + k).Predict(features);
      }
      ++early_stop_round_counter;
      if (early_stop.roundPeriod == early_stop_round_counter) {
        if (early_stop.callbackFunction.callback(output, num_tree_per_iteration_))
          break;
        early_stop_round_counter = 0;
      }
    }
    List<Double> ret = new ArrayList<Double>();
    for (double d : output)
      ret.add(d);
    return ret;
  }

  List<Double> predict(SparseVector features, PredictionEarlyStopInstance early_stop) {
    List<Double> ret = predictRaw(features, early_stop);
    ;
    if (objective_function_ != null) {
      double[] output = new double[ret.size()];
      for (int i = 0; i < ret.size(); i++)
        output[i] = ret.get(i);
      objective_function_.convertOutput(output, output);
      ret = new ArrayList<Double>();
      for (double d : output)
        ret.add(d);
    }
    return ret;
  }

}
