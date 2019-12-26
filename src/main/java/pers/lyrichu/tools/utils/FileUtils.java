package pers.lyrichu.tools.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/*
 * IO 工具类
 */
public class FileUtils {

  public static List<String> readLines(String path) {
    List<String> lines = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(new FileReader(path));
      String line = reader.readLine();
      while (line != null) {
        lines.add(line.trim());
        line = reader.readLine();
      }
      reader.close();
    } catch (Exception e) {
      System.err.println(e);
    }
    return lines;
  }

  public static void writeLines(List<String> lines,String path) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(path));
      for (String line : lines) {
        writer.write(line);
        writer.newLine();
        writer.flush();
      }
      writer.close();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}
