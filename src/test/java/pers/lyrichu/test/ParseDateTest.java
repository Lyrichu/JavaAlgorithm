package pers.lyrichu.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseDateTest {

  public static void main(String[] args) {
    System.out.println(parseQaCreateTime("2020/1/20"));
  }

  private static String parseQaCreateTime(String createTime) {
    SimpleDateFormat oriDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    try {
      Date date = oriDateFormat.parse(createTime);
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
      return dateFormat.format(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return "2020-03-01 00:00:00";
  }


}
