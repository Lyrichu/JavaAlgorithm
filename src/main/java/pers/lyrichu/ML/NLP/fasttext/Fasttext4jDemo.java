package pers.lyrichu.ML.NLP.fasttext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.mayabot.mynlp.fasttext.FastText;
import com.mayabot.mynlp.fasttext.FloatStringPair;
import com.mayabot.mynlp.fasttext.ModelName;
import pers.lyrichu.tools.utils.FileUtils;

public class Fasttext4jDemo {

  private static final String BASE_PATH = "/Users/huchengchun/Downloads/小米工作/data/news_intention_hq";
  private static final String BASE_TRAIN_TEST_PATH_V1 = BASE_PATH + "/dataset/train2000_test200";
  private static final String INPUT_TRAIN_FILE = BASE_TRAIN_TEST_PATH_V1 + "/labeled/newsLabeled.train.4000.txt";
  private static final String INPUT_TEST_FILE = BASE_TRAIN_TEST_PATH_V1 + "/labeled/newsLabeled.test.200.txt";
  private static final String MODEL_SAVE_PATH = BASE_TRAIN_TEST_PATH_V1 + "/model/query.news.intention.fasttext4j.model";
  private static final String PREDICT_TEST_FILE = BASE_TRAIN_TEST_PATH_V1 + "/predict/newsLabeled.test.predict.fasttext4j.200.txt";

  private static final String HALF_YEAR_HQ_PATH = "/Users/huchengchun/Downloads/小米工作/data/gs_query/gs_time_hotquery/gs_hq_2019-06-11_2019-12-11.txt";
  private static final String HALF_YEAR_HQ_PREDICT_PATH =
      "/Users/huchengchun/Downloads/小米工作/data/gs_query/gs_time_hotquery/gs_hq_2019-06-11_2019-12-11.predict.txt";

  private static final String GS_FILTER_HQ_TEST_FILE = BASE_PATH +
      "/dataset/test_labeled_data/gs_hq_19_12_04_to_12_11/newsLabeled.txt";
  private static final String GS_FILTER_HQ_PREDICT_TEST_FILE = BASE_PATH +
      "/dataset/predict_labeled_data/gs_hq_19_12_04_to_12_11/predict.txt";
  private static final double SCORE_THRESHOLD = 0.65;
  private static final String NEWS_LABEL = "news";
  private static final String NON_NEWS_LABEL = "nonNews";

  private static FastText FT;
  private static final JiebaSegmenter segmenter = new JiebaSegmenter();

  public static void main(String[] args) {
//    String inputPath = "/Users/huchengchun/Downloads/gs_rand_history_hq_5000.txt";
//    String savePath = "/Users/huchengchun/Downloads/gs_rand_history_hq_5000.predict.txt";
//    predictRawGsHqs(inputPath,savePath);
    testClassification();
  }

  private static void trainModel(String trainData,String saveModelPath) {
    try {
      FastText fastText = FastText.train(new File(trainData), ModelName.sup);
      fastText.saveModel(saveModelPath);
    } catch (Exception e) {
      System.err.println(e);
    }

  }

  private static void predictSingle(String query) {
    FT = FastText.loadModel(MODEL_SAVE_PATH,true);
    List<String> words = segmenter.process(query, SegMode.SEARCH).stream().map(p -> p.word).collect(
        Collectors.toList());
    List<FloatStringPair> predicts = FT.predict(words,5);
    System.out.println(predicts);
  }

  private static void testClassification() {
    FT = FastText.loadModel(MODEL_SAVE_PATH,true);
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
      List<String> segWords = Arrays.asList(words.split(" "));
      List<FloatStringPair> predicts = FT.predict(segWords,5);
      String predictLabel;
      totalNum++;
      if (NEWS_LABEL.equals(label)) {
        newsNum++;
        if (isMatchNews(predicts)) {
          newsCorrectNum++;
          // 修正 predictLabel
          predictLabel = NEWS_LABEL;
        } else {
          predictLabel = NON_NEWS_LABEL;
        }
      } else {
        nonNewsNum++;
        if (isMatchNonNews(predicts)) {
          nonNewsCorrectNum++;
          predictLabel = NON_NEWS_LABEL;
        } else {
          predictLabel = NEWS_LABEL;
        }
      }
      String predictLine = String.format("%s\t%s\t%s\t%s",words,"__label__" + label,
          "__predict__" + predictLabel,"__raw__" + predicts);
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

  private static List<String> getSegWords(String query) {
    List<String> words = segmenter.process(query, SegMode.SEARCH).stream().map(p -> p.word).collect(
        Collectors.toList());
    return words;
  }

  private static boolean isMatchNews(List<FloatStringPair> predicts) {
    if (predicts.size() != 2) {
      return false;
    }
    FloatStringPair fsp = predicts.get(0);
    double score = fsp.first;
    String label = fsp.second.replace("__label__","");
    return "news".equals(label) && score >= SCORE_THRESHOLD;
  }

  private static boolean isMatchNonNews(List<FloatStringPair> predicts) {
    return !isMatchNews(predicts);
  }

  private static void predictRawGsHqs(String inputPath,String savePath) {
    FT = FastText.loadModel(MODEL_SAVE_PATH,true);
    List<String> testLines = FileUtils.readLines(inputPath);
    List<String> writeLines = Lists.newArrayListWithCapacity(testLines.size());
    for (String line : testLines) {
      String query = line.trim().replaceAll("#","");
      List<String> segWords = getSegWords(query);
      List<FloatStringPair> predicts = FT.predict(segWords,5);
      String predictLabel;
      if (isMatchNews(predicts)) {
        predictLabel = "news";
      } else {
        predictLabel = "nonNews";
      }
      String writeLine = String.format("%s\t__predict__%s",query,predictLabel);
      writeLines.add(writeLine);
    }
    FileUtils.writeLines(writeLines,savePath);
  }


}
