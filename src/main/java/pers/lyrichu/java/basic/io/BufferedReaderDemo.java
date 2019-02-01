package pers.lyrichu.java.basic.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BufferedReaderDemo {
  public static void main(String[] args) {
    String filePath = "src/main/resources/test.txt";
    List<String> lines = bufferedReader(filePath);
    for (String line:lines) {
      System.out.println(line);
    }
  }

  private static List<String> bufferedReader(String filePath) {
    List<String> res = new ArrayList<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line = br.readLine();
      while (line != null) {
        res.add(line);
        line = br.readLine();
      }
    } catch (IOException e) {
      System.err.println(e.toString());
    }
    return res;
  }
}
