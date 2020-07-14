package pers.lyrichu.ML.xgboost;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;

/**
 * 从本地加载 xgboost 模型文件并且进行预测
 */

public class XbgModelLoaderDemo {

  public static void main(String[] args) throws Exception {

    String xgbModelPath = "/Users/huchengchun/Downloads/quora_qp.xgb.v2.model";
    Booster booster = XGBoost.loadModel(xgbModelPath);
    float[] data = new float[] {
        0.4f,
        0.405492f,
        0.444444f,
        4f,
        9f,
        4f,
        1f,
        0.857143f,
        1f,
        0.857143f,
        1f,
        0.666667f,
        1f,
        1f,
        0.973684f,
        0f
    };
    DMatrix dMatrix = new DMatrix(data,1,data.length,Float.NaN);
    float[][] preds = booster.predict(dMatrix);
    System.out.println(preds[0][0]);
  }

}
