package pers.lyrichu.java.util.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LambdaExpressionSort {
    public static void main(String[] args) {
        List<String> lists = Arrays.asList("peter","mary","kafka","mike","jack");
        // 原来的sort方法
        // new Comparator
        Collections.sort(lists, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        System.out.println("sort result 1:");
        for (String s:lists) {
            System.out.print(s+" ");
        }
        System.out.println();
        // 随机打乱
        Collections.shuffle(lists);
        // 新的匿名表达式排序
        Collections.sort(lists,(String o1,String o2) -> o2.compareTo(o1));
        System.out.println("sort result 2:");
        for (String s:lists) {
            System.out.print(s+" ");
        }
        System.out.println();
    }
}
