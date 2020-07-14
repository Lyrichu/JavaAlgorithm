package pers.lyrichu.test;

import com.xiaomi.search.global.content.algorithm.PornClassifier;

/**
 * 色情识别测试
 */
public class PornTestClassifier {

  public static void main(String[] args) {
    PornClassifier classifier = new PornClassifier();
    String text = "在线看片免费人成视频";
    Double pornProb = classifier.predictPorn(text);
    System.out.println("prob:" + pornProb + ", " + text);
  }
}
