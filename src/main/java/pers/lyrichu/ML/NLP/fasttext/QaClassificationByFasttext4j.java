package pers.lyrichu.ML.NLP.fasttext;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.mayabot.nlp.fasttext.FastText;
import com.mayabot.nlp.fasttext.ScoreLabelPair;
import com.mayabot.nlp.fasttext.args.InputArgs;
import com.mayabot.nlp.fasttext.loss.LossName;

/**
 * 判定一个 query 是否是问答类型
 */
public class QaClassificationByFasttext4j {

  private static final String TRAIN_FILE = "/Volumes/Samsung_T5/data_source/baidu_zhidao_question/experiment/train_model/ft_data.train.txt";
  private static final String TEST_FILE = "/Volumes/Samsung_T5/data_source/baidu_zhidao_question/experiment/train_model/ft_data.test.txt";
  private static final String SAVE_MODEL_PATH = "/Volumes/Samsung_T5/data_source/baidu_zhidao_question/experiment/train_model/qa_classification_model";
  private static final JiebaSegmenter segmenter = new JiebaSegmenter();

  public static void main(String[] args) {
    train();
    test();
    testCall();
  }

  private static void train() {
    File trainFile = new File(TRAIN_FILE);
    InputArgs inputArgs = new InputArgs();
    inputArgs.setLoss(LossName.softmax);
    inputArgs.setLr(0.1);
    inputArgs.setDim(200);
    inputArgs.setEpoch(100);

    FastText model = FastText.trainSupervised(trainFile, inputArgs);
    try {
      model.saveModel(SAVE_MODEL_PATH);
      System.out.println("save model succeed!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void test() {
    //load from java format
    FastText model = FastText.Companion.loadModel(new File(SAVE_MODEL_PATH),false);
    // print test multi labels precision & recall
    model.test(new File(TEST_FILE),5,0,true);
  }

  private static void testCall() {
    String testText = cutSentence("中东国家有哪些");
    System.out.println(testText);
    FastText model = FastText.Companion.loadModel(new File(SAVE_MODEL_PATH),false);
    List<ScoreLabelPair> result = model.predict(
        Arrays.asList(testText.split(" ")), 5,0);
    result.forEach(System.out::println);
  }

  private static String cutSentence(String sentence) {
    return String.join(" ",segmenter.sentenceProcess(sentence));
  }

}
