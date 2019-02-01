package pers.lyrichu.java.util.scripts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDate {
    public static void main(String[] args) {
        String str = "2018-10-28";
        String format = "yyyy-MM-dd";
        Date date = parseStringToDate(str,format);
        System.out.println("date:"+date);
    }

    private static Date parseStringToDate(String str,String format) {
        try{
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e){
            System.err.println("error:"+ e);
            return null;
        }

    }
}
