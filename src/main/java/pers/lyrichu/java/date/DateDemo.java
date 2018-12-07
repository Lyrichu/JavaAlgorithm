package pers.lyrichu.java.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDemo {
  public static void main(String[] args) {
    String format1 = "YYYY-MM-dd HH:mm:ss:SSS"; // hh 12小时制,HH 24小时制
    String format2 = "HH";
    Date now = new Date();
    System.out.println("now:"+getFormatDateStr(now,format2));
  }

  public static String getFormatDateStr(Date date, String format) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    return simpleDateFormat.format(date);
  }
}
