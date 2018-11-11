package pers.lyrichu.java.util.java8;

import java.util.Arrays;
import java.util.List;

public class StreamMap {
    private static List<String> list = Arrays.asList("aa1","aa2","bbb1","bbb3","c4","dddd1");

    public static void main(String[] args) {
        list.stream()
                .map(String::toUpperCase) // 将字符串变为大写
                .sorted(
                        (a,b) -> b.compareTo(a)  // 按照逆序排列
                )
                .forEach(System.out::println);
    }
}
