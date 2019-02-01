package pers.lyrichu.java.util.java8;

import java.util.Arrays;
import java.util.List;

public class StreamSorted {
    private static List<String> list = Arrays.asList("aa1","aa2","bbb1","bbb3","c4","dddd1");

    public static void main(String[] args) {
        list.stream().sorted()
                .filter(
                (s) -> s.startsWith("a") // 只保留以a开头的字符串
        ).forEach(System.out::println);
        // sorted方法不会改变原有的list
        for(String s:list) {
            System.out.println(s+" ");
        }
        System.out.println();
    }
}
