package pers.lyrichu.ML.NLP.hanlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.IndexTokenizer;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;
import com.hankcs.hanlp.tokenizer.SpeedTokenizer;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;

import java.io.IOException;
import java.util.List;

/*
 * 分词相关,reference:https://github.com/hankcs/HanLP
 */
public class SegmentDemo {
  public static void main(String[] args) throws Exception{
    firstSegment();
    standardSegment();
    //nlpSegment();
//    indexTokenizer();
//    shortSegment();
//    crfSegment();
//    highSpeedSegment();
  }

  private static void firstSegment() {
    String sentence = "红米手机怎么样呢";
    System.out.println(HanLP.segment(sentence));
  }

  private static void standardSegment() {
    String sentence = "我刚买的红米Note7性价比非常高!";
    System.out.println(StandardTokenizer.segment(sentence));
  }

  private static void nlpSegment() {
    System.out.println(NLPTokenizer.segment("我新造一个词叫幻想乡你能识别并标注正确词性吗？"));
    // 注意观察下面两个“希望”的词性、两个“晚霞”的词性
    System.out.println(NLPTokenizer.analyze("我的希望是希望张晚霞的背影被晚霞映红").translateLabels());
    System.out.println(NLPTokenizer.analyze("支援臺灣正體香港繁體：微软公司於1975年由比爾·蓋茲和保羅·艾倫創立。"));
  }

  /*
   * 索引分词
   */
  private static void indexTokenizer() {
    List<Term> list = IndexTokenizer.segment("主副食品");
    for (Term term:list) {
      System.out.println(term + "[" + term.offset + ":" +
          (term.offset+term.word.length()) + "]");
    }
  }

  /*
   * 最短路分词 & N最短路分词
   */
  private static void shortSegment() {
    Segment nShortSeg = new NShortSegment()
        .enableCustomDictionary(false)
        // 地名识别
        .enablePlaceRecognize(true)
        // 机构名识别
        .enableOrganizationRecognize(true);
    Segment shortestSeg = new DijkstraSegment()
        .enableCustomDictionary(false)
        .enablePlaceRecognize(true)
        .enableOrganizationRecognize(true);
    String[] sentences = new String[] {
        "今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。",
        "刘喜杰石国祥会见吴亚琴先进事迹报告团成员"
    };
    // 比较两种分词方式
    for (String sentence:sentences) {
      System.out.println("N-最短分词:" + nShortSeg.seg(sentence) +
          "\t最短分词:" + shortestSeg.seg(sentence));
    }
  }

  /*
   * crf 分词
   */
  private static void crfSegment() throws IOException {
    CRFLexicalAnalyzer crfLexicalAnalyzer = new CRFLexicalAnalyzer();
    String[] sentences = new String[] {
        "商品和服务",
        "上海华安工业（集团）公司董事长谭旭光和秘书胡花蕊来到美国纽约现代艺术博物馆参观",
        "微软公司於1975年由比爾·蓋茲和保羅·艾倫創立，18年啟動以智慧雲端、前端為導向的大改組。" // 支持繁体中文
    };
    for (String sentence:sentences) {
      System.out.println(crfLexicalAnalyzer.analyze(sentence));
    }
  }

  /*
   * 极速词典分词
   */
  private static void highSpeedSegment() {
    String sentence = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
    System.out.println(SpeedTokenizer.segment(sentence));
    // 循环测试分词性能
    long start = System.currentTimeMillis();
    int epochs = 1000000;
    for (int i = 0;i < epochs;i++) {
      SpeedTokenizer.segment(sentence);
    }
    double costTime = (System.currentTimeMillis() - start)/(double)1000;
    System.out.printf("分词速度:%.2f字/s\n",sentence.length()*epochs/costTime);
  }
}
