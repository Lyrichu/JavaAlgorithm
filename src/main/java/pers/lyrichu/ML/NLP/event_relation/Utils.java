package pers.lyrichu.ML.NLP.event_relation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.XGBoost;
import pers.lyrichu.tools.utils.FileUtils;

public class Utils {

  private ClassLoader classloader;

  private String STOPWORDS_PATH;
  private String IDF_PATH;
  private String EVENT_MODEL_V1_PATH;
  private String STORY_MODEL_V1_PATH;

  private JiebaSegmenter segmenter;

  public Utils() {
    classloader = getClass().getClassLoader();
    STOPWORDS_PATH = classloader.getResource("event_relation/stopwords-zh.txt").getPath();
    IDF_PATH = classloader.getResource("event_relation/idf.txt").getPath();
    EVENT_MODEL_V1_PATH = classloader.getResource("event_relation/article_pair_matching.xgb.event.v1.model").getPath();
    STORY_MODEL_V1_PATH = classloader.getResource("event_relation/article_pair_matching.xgb.story.v1.model").getPath();
    segmenter = new JiebaSegmenter();
  }


  public Set<String> getStopwords() {
    List<String> words = FileUtils.readLines(STOPWORDS_PATH);
    return new HashSet<>(words);
  }

  public Map<String,Double> getIdfMap() {
    Map<String,Double> idfMap = new HashMap<>(1024);

    List<String> lines = FileUtils.readLines(IDF_PATH);

    for (String line : lines) {
      String[] splits = line.split(" ");
      String word = splits[0];
      double idfScore = Double.valueOf(splits[1]);
      idfMap.put(word,idfScore);
    }
    return idfMap;
  }

  public Booster getXgbModel(String modelPath) {
    try {
      return XGBoost.loadModel(modelPath);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Booster getEventXgbModel() {
    return getXgbModel(EVENT_MODEL_V1_PATH);
  }

  public Booster getStoryXgbModel() {
    return getXgbModel(STORY_MODEL_V1_PATH);
  }


  public List<String> cutText(String text,Set<String> stopWords) {

    List<String> words = new ArrayList<>();

    List<SegToken> tokens = segmenter.process(text,SegMode.SEARCH);
    for (SegToken token : tokens) {
      if (!stopWords.contains(token.word) && token.word.length() > 1) {
        words.add(token.word);
      }
    }
    return words;
  }

  public List<String> cutTextByBlank(String text,Set<String> stopWords) {

    List<String> words = new ArrayList<>();
    String[] splits = text.split(" ");
    for (String word : splits) {
      if (!stopWords.contains(word)) {
        words.add(word);
      }
    }
    return words;
  }


  public List<String> cutText(String text) {

    List<String> words = new ArrayList<>();

    List<SegToken> tokens = segmenter.process(text, SegMode.SEARCH);
    for (SegToken token : tokens) {
      if (token.word.length() > 1) {
        words.add(token.word);
      }
    }
    return words;
  }


}
