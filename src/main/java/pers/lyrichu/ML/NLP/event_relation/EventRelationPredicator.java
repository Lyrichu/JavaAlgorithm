package pers.lyrichu.ML.NLP.event_relation;
import java.util.Arrays;
import java.util.List;
import	java.util.Map;

import java.util.Set;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;

public class EventRelationPredicator {

  private static final Utils utils = new Utils();
  private static final Set<String> STOPWORDS = utils.getStopwords();
  private static final Map<String,Double> IDF_MAP = utils.getIdfMap();

  private static final Booster EVENT_XGB_MODEL = utils.getEventXgbModel();
  private static final Booster STORY_XGB_MODEL = utils.getStoryXgbModel();

  private static final int EVENT_XGB_FILEDS_NUM = 29; // event xgb 特征数量

  public static void main(String[] args) {
    String title1 = "深 铁 接盘 万科 是 另外 一 种 意义 的 混改";
    String title2 = "宝能 ： 欢迎 深 铁 投资 万科 ， 愿 共 为 深圳 及 万科 发展 而 努力";
    String keywords1 = "万科,媒体人,许家印,王石,重大,媒体,意外,股权,拯救,万科股价,华润,安邦,恒大,独董";
    String keywords2 = "华润,万科,投资,投资者,香港,万科企业,恒大,股份转让,深圳";

    title1 = "古力娜扎 屡遭 网友 吐槽 张翰 两 点";
    title2 = "拍照 曝光 张翰 娜扎 好事 网友 迫不及待";
    keywords1 = "吐槽,微博,古力娜扎,鹿晗,年轻,择天记,加盟,亲近,娜扎,爱豆,张翰,谜,郑爽,古力";
    keywords2 = "恋人,娜扎,古力娜扎,张翰,浴室" ;

    title1 = "专利 授权 模式 太 霸道 ， 高通 遭 韩国 监管 机构 重罚 8.54 亿 美元";
    title2 = "高 通 接连 被 罚 真的 是 因为 垄断 吗 ？";
    keywords1 = "科技,台湾,必要,高通,联发科技,苹果,神迹,英特尔,欧盟,路透社,法院,美国,韩国,华为,芯片,专利费,首尔,新锐,手机厂商,反垄断,供应链,三星电子,中国,美元,专利,三星";
    keywords2 = "移动,诺基亚,韩国,华为,智能机,芯片,安卓系统,营销,海思,联芯,专利费,高通,反垄断,通信,联通,联发科,中国,魅族,处理器,自然,人民币,专利,三星";

    title1 = "国家 卫健 委 ： 对 入境 人员 主张 集中 隔离 观察 ， 不 居家 隔离";
    title2 = "北京 口岸 入境 人员 集散 点内 设有 驻馆 医疗点";
    keywords1 = "居家隔离,首席,国家卫健委,送医,联防,吴尊友,入境,经济日报,新闻发布会,隔离,人员,中国经济网,居家,吴尊,潜伏";
    keywords2 = "新国,人员,居民,顺义区,集散,北京,口岸";
    long start = System.currentTimeMillis();
    float eventScore = predictForEvent(title1,title2,keywords1,keywords2);
    float storyScore = predictForStory(title1,title2,keywords1,keywords2);
    System.out.printf("predict cost %d ms\n",System.currentTimeMillis() - start);
    System.out.println("eventScore:" + eventScore);
    System.out.println("storyScore:" + storyScore);
  }

  public static float[] buildEventFeatures(String title1,String title2,String keywords1,String keywords2) {

    List<String> titleWords1 = utils.cutText(title1,STOPWORDS);
    System.out.println(titleWords1);
    List<String> titleWords2 = utils.cutText(title2,STOPWORDS);
    System.out.println(titleWords2);

    // keywords 以逗号分隔
    List<String> keywordsWords1 = Arrays.asList(keywords1.split(","));
    List<String> keywordsWords2 = Arrays.asList(keywords2.split(","));

    float[] features = new float[EVENT_XGB_FILEDS_NUM];

    features[0] = EventRelationFeatures.getWordMatchShare(titleWords1,titleWords2);
    features[1] = EventRelationFeatures.getWordMatchShare(keywordsWords1,keywordsWords2);
    features[2] = EventRelationFeatures.getWordMatchShareUnique(titleWords1, titleWords2);
    features[3] = EventRelationFeatures.getWordMatchShareUnique(keywordsWords1,keywordsWords2);
    features[4] = EventRelationFeatures.getIdfWordMatchShare(titleWords1,titleWords2,IDF_MAP);
    features[5] = EventRelationFeatures.getIdfWordMatchShare(keywordsWords1,keywordsWords2,IDF_MAP);
    features[6] = EventRelationFeatures.getIdfWordMatchShareUnique(titleWords1,titleWords2,IDF_MAP);
    features[7] = EventRelationFeatures.getIdfWordMatchShareUnique(keywordsWords1,keywordsWords2,IDF_MAP);
    features[8] = EventRelationFeatures.getCommonWordsLen(titleWords1,titleWords2);
    features[9] = EventRelationFeatures.getCommonWordsLen(keywordsWords1,keywordsWords2);
    features[10] = EventRelationFeatures.getCommonWordsUniqueLen(titleWords1,titleWords2);
    features[11] = EventRelationFeatures.getCommonWordsUniqueLen(keywordsWords1,keywordsWords2);
    features[12] = EventRelationFeatures.getWordCountDiff(titleWords1,titleWords2);
    features[13] = EventRelationFeatures.getWordCountDiff(keywordsWords1,keywordsWords2);
    features[14] = EventRelationFeatures.getWordCountUniqueDiff(titleWords1,titleWords2);
    features[15] = EventRelationFeatures.getWordCountUniqueDiff(keywordsWords1,keywordsWords2);
    features[16] = EventRelationFeatures.getWordCountRatio(titleWords1,titleWords2);
    features[17] = EventRelationFeatures.getWordCountRatio(keywordsWords1,keywordsWords2);
    features[18] = EventRelationFeatures.getWordCountUniqueRatio(titleWords1,titleWords2);
    features[19] = EventRelationFeatures.getWordCountUniqueRatio(keywordsWords1,keywordsWords2);
    features[20] = EventRelationFeatures.ifSameStartWord(titleWords1,titleWords2);
    features[21] = EventRelationFeatures.getCharDiff(titleWords1,titleWords2);
    features[22] = EventRelationFeatures.getCharDiff(keywordsWords1,keywordsWords2);
    features[23] = EventRelationFeatures.getCharUniqueDiff(titleWords1,titleWords2);
    features[24] = EventRelationFeatures.getCharUniqueDiff(keywordsWords1,keywordsWords2);
    features[25] = EventRelationFeatures.getCharRatio(titleWords1,titleWords2);
    features[26] = EventRelationFeatures.getCharRatio(keywordsWords1,keywordsWords2);
    features[27] = EventRelationFeatures.getCharUniqueRatio(titleWords1,titleWords2);
    features[28] = EventRelationFeatures.getCharUniqueRatio(keywordsWords1,keywordsWords2);

    return features;
  }

  public static float predictForEvent(float[] features) {
    for (int i = 0; i < features.length; i++) {
      System.out.print(features[i] + ",");
    }
    System.out.println();
    return predict(features,EVENT_XGB_MODEL);
  }

  public static float predictForEvent(String title1,String title2,String keywords1,String keywords2) {
    return predictForEvent(buildEventFeatures(title1,title2,keywords1,keywords2));
  }

  public static float predictForStory(float[] features) {
    return predict(features,STORY_XGB_MODEL);
  }

  public static float predictForStory(String title1,String title2,String keywords1,String keywords2) {
    return predictForStory(buildEventFeatures(title1,title2,keywords1,keywords2));
  }

  private static float predict(float[] features,Booster model) {
    if (features == null || model == null) {
      System.err.println("features or event model is null!");
      return Float.NaN;
    }
    try {
      DMatrix dMatrix = new DMatrix(features,1,features.length,Float.NaN);
      float[][] scores = model.predict(dMatrix);
      return scores[0][0];
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Float.NaN;
  }


}
