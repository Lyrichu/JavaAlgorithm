package pers.lyrichu.java.util.java8;

import java.util.Arrays;
import java.util.List;

public class StreamMatch {
    private static List<String> list = Arrays.asList("aa1","aa2","bbb1","bbb3","c4","dddd1");

    public static void main(String[] args) {
        // list中是否有任何一个以a开头的字符串
        boolean anyStartsWithA = list.stream().anyMatch( (s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);
        // list中是否是全部以a开头
        boolean allStartsWithA = list.stream().allMatch( (s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);
        // list中是没有以z开头的字符串
        boolean nonStartsWithZ = list.stream().noneMatch( (s) -> s.startsWith("z"));
        System.out.println(nonStartsWithZ);
    }

}
