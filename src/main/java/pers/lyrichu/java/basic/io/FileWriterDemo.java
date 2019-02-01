package pers.lyrichu.java.basic.io;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileWriterDemo {
  public static void main(String[] args) {
    String writePath = "src/main/resources/test.txt";
    List<String> lines = Arrays.asList("????","!!!!!");
    fileWriter(lines,writePath);
  }

  private static void fileWriter(List<String> lines,String writePath) {
    try {
      FileWriter fw = new FileWriter(writePath,true); // 追加
      fw.write("\n");
      for (String line:lines) {
        fw.write(line); // 不会添加换行符,需要手动添加
        //添加换行符
        fw.write("\n");
      }
      fw.close();
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }
}
