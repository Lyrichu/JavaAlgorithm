package pers.lyrichu.ML.NLP.fasttext;

import java.util.ArrayList;
import java.util.List;

import com.github.jfasttext.JFastText;
import com.github.jfasttext.JFastText.ProbLabel;
import pers.lyrichu.tools.utils.FileUtils;

/**
 * 使用fastext 识别 query 是否具有新闻意图
 */
public class NewsIntentionClassification {

  private static final String BASE_PATH = "/Users/huchengchun/Downloads/小米工作/data/news_intention_hq";
  private static final String BASE_TRAIN_TEST_PATH_V1 = BASE_PATH + "/dataset/train1000_test100";
  private static final String INPUT_TRAIN_FILE = BASE_TRAIN_TEST_PATH_V1 + "/labeled/newsLabeled.train.2000.txt";
  private static final String INPUT_TEST_FILE = BASE_TRAIN_TEST_PATH_V1 + "/labeled/newsLabeled.test.200.txt";
  private static final String MODEL_SAVE_PATH = BASE_TRAIN_TEST_PATH_V1 + "/model/query.news.intention.model";
  private static final String PREDICT_TEST_FILE = BASE_TRAIN_TEST_PATH_V1 + "/predict/newsLabeled.test.predict.200.txt";

  private static final String HALF_YEAR_HQ_PATH = "/Users/huchengchun/Downloads/小米工作/data/gs_query/gs_time_hotquery/gs_hq_2019-06-11_2019-12-11.txt";
  private static final String HALF_YEAR_HQ_PREDICT_PATH =
      "/Users/huchengchun/Downloads/小米工作/data/gs_query/gs_time_hotquery/gs_hq_2019-06-11_2019-12-11.predict.txt";

  private static final String GS_FILTER_HQ_TEST_FILE = BASE_PATH +
      "/dataset/test_labeled_data/gs_filter_hq_19_12_11/newsLabeled.txt";
  private static final String GS_FILTER_HQ_PREDICT_TEST_FILE = BASE_PATH +
      "/dataset/predict_labeled_data/gs_filter_hq_19_12_11/predict.txt";
  private static final double SCORE_THRESHOLD = 0.65;




  private static final JFastText jft = new JFastText();

  public static void main(String[] args) {
    predictQueries(HALF_YEAR_HQ_PATH,HALF_YEAR_HQ_PREDICT_PATH);
  }

  private static void trainClassification() {
    jft.runCmd(new String[] {
        "supervised",
        "-input", INPUT_TRAIN_FILE,
        "-output", MODEL_SAVE_PATH
    });
  }

  private static void testClassification() {
    jft.loadModel(MODEL_SAVE_PATH + ".bin");
    List<String> testLines = FileUtils.readLines(GS_FILTER_HQ_TEST_FILE);
    List<String> predictLines = new ArrayList<>();
    int totalCorrectNum = 0; // 整体判别正确数量
    int totalNum = 0;
    int newsNum = 0; // 新闻数量
    int nonNewsNum = 0; // 非新闻数量
    int newsCorrectNum = 0; // 新闻判定正确数量(新闻判定为新闻)
    int nonNewsCorrectNum = 0; // 非新闻判定正确数量(非新闻判定为非新闻)
    for (String line : testLines) {
      String[] splits = line.trim().split("__label__");
      String words = splits[0].trim();
      String label = splits[1];
      ProbLabel probLabel = jft.predictProba(words);
      if (probLabel == null) {
        System.out.printf("%s get null probLabel\n",words);
        continue;
      }
      totalNum++;
      String predictLabel = probLabel.label.replace("__label__","");
      double predictProb = Math.exp(probLabel.logProb);
      if ("news".equals(label)) {
        newsNum++;
        if (isMatchNews(probLabel)) {
          newsCorrectNum++;
          // 修正 predictLabel
          predictLabel = "news";
        }
      }
      if ("nonNews".equals(label)) {
        nonNewsNum++;
        if (isMatchNonNews(probLabel)) {
          nonNewsCorrectNum++;
          predictLabel = "nonNews";
        }
      }
      String predictLine = String.format("%s\t%s\t%s\t%s",words,"__label__" + label,
          "__predict__" + predictLabel,"__prob__" + predictProb);
      predictLines.add(predictLine);
    }
    FileUtils.writeLines(predictLines,GS_FILTER_HQ_PREDICT_TEST_FILE);
    totalCorrectNum = newsCorrectNum + nonNewsCorrectNum;
    double totalCorrectRatio = (double) totalCorrectNum / totalNum;
    double newsWrongRatio = (double) newsCorrectNum / newsNum;
    double nonNewsWrongRatio = (double) nonNewsCorrectNum / nonNewsNum;
    System.out.printf("总体准确率:%.3f(%d/%d),新闻准确率:%.3f(%d/%d),非新闻准确率:%.3f(%d/%d).",
        totalCorrectRatio,totalCorrectNum,totalNum,
        newsWrongRatio,newsCorrectNum,newsNum,
        nonNewsWrongRatio,nonNewsCorrectNum,nonNewsNum);
  }

  private static void predictQueries(String filePath,String savePath) {
    List<String> lines = FileUtils.readLines(filePath);
    List<String> predictLines = new ArrayList<>();
    jft.loadModel(MODEL_SAVE_PATH + ".bin");
    for (String query : lines) {
      query = query.trim();
      ProbLabel probLabel = jft.predictProba(query);
      String predictLabel;
      if (probLabel == null) {
        continue;
      }
      // 判定为新闻意图
      if (isMatchNews(probLabel)) {
        predictLabel = "news";
      } else {
        predictLabel = "nonNews";
      }
      predictLines.add(String.format("%s\t__predict__%s",query,predictLabel));
    }
    FileUtils.writeLines(predictLines,savePath);
  }

  private static boolean isMatchLabel(ProbLabel probLabel,String label) {
    if (probLabel == null) {
      return false;
    }
    String predictLabel = probLabel.label.replace("__label__","");
    double predictProb = Math.exp(probLabel.logProb);
    if (label == null) {
      return true;
    }
    return label.equals(predictLabel) || predictProb <= SCORE_THRESHOLD;
  }

  private static boolean isMatchNews(ProbLabel probLabel) {
    return isMatchLabel(probLabel,"news");
  }

  private static boolean isMatchNonNews(ProbLabel probLabel) {
    return isMatchLabel(probLabel,"nonNews");
  }

}
