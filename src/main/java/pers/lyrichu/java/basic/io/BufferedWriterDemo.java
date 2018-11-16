package pers.lyrichu.java.basic.io;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class BufferedWriterDemo {
  public static void main(String[] args) {
    String writePath = "src/main/resources/test.txt";
    List<String> lines = Arrays.asList("hahaha","This is a good day!");
    bufferedWriter(lines,writePath);
  }

  private static void bufferedWriter(List<String> lines,String writePath) {
    try {
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writePath,true))); // 追加
      bw.newLine();
      for (String line:lines) {
        bw.write(line); // 追加
        // BufferedWriter 默认不会写入换行,需要手动添加newLine 方法
        bw.newLine();
      }
      bw.close(); // 不要忘记close
    } catch (IOException e) {
      System.err.println("error:"+e);
    }
  }
}
