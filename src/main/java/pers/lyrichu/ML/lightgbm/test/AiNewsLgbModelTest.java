package pers.lyrichu.ML.lightgbm.test;
import	java.nio.charset.StandardCharsets;
import	java.io.IOException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import pers.lyrichu.ML.lightgbm.util.LgbParser;
import pers.lyrichu.ML.lightgbm.util.Predictor;
import pers.lyrichu.ML.lightgbm.util.SparseVector;

public class AiNewsLgbModelTest {

  @Test
  public void test() throws IOException {
    String modelStr = FileUtils.readFileToString(new File("/Users/huchengchun/Downloads/lgbm_theme_100-6")); // lgbm_theme_100-6,ainews_lgb.v1.model.txt

    LgbParser parser = new LgbParser();
    Predictor model = parser.parse(modelStr);

    List<String> datas = FileUtils.readLines(new File("/Users/huchengchun/Downloads/ainews_test_data.txt"),StandardCharsets.UTF_8);
    long start = System.currentTimeMillis();
    int n = Math.min(8,datas.size());
    int N = 1;
    long maxCost = 0;
    long totalCost = 0;
    for (int t = 0;t < N;t++) {
      for (int i = 0;i < n; ++i) {
        String line = datas.get(i);
        String[] splits = line.trim().split("\t");
        int label = Integer.parseInt(splits[0]);
        String[] items = splits[1].split(",");
        int[] indices = new int[items.length];
        float[] values = new float[items.length];
        for (int j = 0; j < items.length; ++j) {
          indices[j] = j + 1;
          values[j] = Float.parseFloat(items[j]);
        }
        long tmpStart = System.currentTimeMillis();
        List<Double> scores = model.predict(new SparseVector(values, indices));
        long tmpCost = System.currentTimeMillis() - tmpStart;
        totalCost += tmpCost;
        if (tmpCost > maxCost) {
          maxCost = tmpCost;
        }
        //System.out.printf("%d:label = %d,predict socre = %.3f\n",i,label,scores.get(0));
      }
    }
    System.out.printf("lgb predict total cost:%d ms,avg cost = %.3f ms,max cost = %d ms.\n",totalCost,totalCost / (double) (n*N),maxCost);
  }

  @Test
  public void predictAinews() throws IOException {
    String basePath = "/Users/huchengchun/Downloads/ainews/lgb_train_data/200630/libsvm";
    String modelStr = FileUtils.readFileToString(new File(basePath + "/lgb_ainews.v1.model"));
    LgbParser parser = new LgbParser();
    Predictor model = parser.parse(modelStr);
    List<String> lines = FileUtils.readLines(new File(basePath + "/pos_neg_test.libsvm.data.txt"),
        StandardCharsets.UTF_8);
    List<String> preds = new ArrayList<>(1024);
    long cost = 0;
    long maxCost = 0;
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
      cost += tmpCost;
      double score = scores.get(0);
      preds.add(String.format("%d:%.3f",label,score));
    }
    System.out.printf("avg lgb model predict cost:%.3f ms,maxCost = %d ms.\n",cost / (double) lines.size(),maxCost);
    String predSavePath = basePath + "/test_pred.txt";
    FileUtils.writeLines(new File(predSavePath),preds,"\n",false);
  }


}
