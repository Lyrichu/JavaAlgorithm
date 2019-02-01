package pers.lyrichu.java.util.scripts;

public class StringIntegerParser {
    public static void main(String[] args) {
        // Integer to String
        String a = String.valueOf(2);
        System.out.printf("a:%s\n",a);
        // String to Integer
        int i = Integer.valueOf(a);
        System.out.println("i:"+i);
    }
}
