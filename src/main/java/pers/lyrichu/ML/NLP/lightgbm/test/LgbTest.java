package pers.lyrichu.ML.NLP.lightgbm.test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import pers.lyrichu.ML.NLP.lightgbm.LgbParser;
import pers.lyrichu.ML.NLP.lightgbm.Predictor;
import pers.lyrichu.ML.NLP.lightgbm.SparseVector;

public class LgbTest {

  @Test
  public void test() throws Exception {
    String basePath = "/Users/huchengchun/Downloads/ainews/lgb_train_data/200630/libsvm";

    LgbParser parser = new LgbParser();
    Predictor model = parser.parse(basePath + "/lgb_ainews.v1.model");
    List<String> lines = FileUtils.readLines(new File(basePath + "/pos_neg_test.libsvm.data.txt"),
        StandardCharsets.UTF_8);
    List<String> preds = new ArrayList<>(1024);
    long cost = 0;
    long maxCost = 0;
    long minCost = 1000;
    for (String line : lines) {
      String[] items = line.trim().split("\\s+");
      int label = Integer.parseInt(items[0]);
      int[] indices = new int[items.length - 1];
      float[] values = new float[items.length - 1];
      for (int i = 1; i < items.length; ++i) {
        String[] kv = items[i].split(":");
        int index = Integer.parseInt(kv[0]);
        float value = Float.parseFloat(kv[1]);
        indices[i-1] = index;
        values[i-1] = value;
      }
      long start = System.currentTimeMillis();
      List<Double> scores = model.predict(new SparseVector(values, indices));
      long tmpCost = System.currentTimeMillis() - start;
      if (tmpCost > maxCost) {
        maxCost = tmpCost;
      }
      if (tmpCost < minCost) {
        minCost = tmpCost;
      }
      cost += tmpCost;
      double score = scores.get(0);
      preds.add(String.format("%d:%.3f",label,score));
    }
    System.out.printf("avg lgb model predict cost:%.3f ms,maxCost = %d ms,minCost = %d ms.\n",cost / (double) lines.size(),maxCost,minCost);
    String predSavePath = basePath + "/test_pred.txt";
    FileUtils.writeLines(new File(predSavePath),preds,"\n",false);
  }

}
