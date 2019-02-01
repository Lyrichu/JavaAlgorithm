package pers.lyrichu.java.util.java8;

import java.util.Arrays;
import java.util.List;

// 流式过滤操作
public class StreamFilter {
    private static List<String> list = Arrays.asList("aa1","aa2","bbb1","bbb3","c4","dddd1");

    public static void main(String[] args) {
        list.stream().filter(
                (s) -> s.startsWith("a") // 只保留以a开头的字符串
        ).forEach(System.out::println);
    }
}


