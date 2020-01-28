package pers.lyrichu.java.util.java8;

import java.util.Arrays;
import java.util.List;

// count是一个最终操作
// 返回stream中元素的个数
public class StreamCount {
    private static List<Integer> list = Arrays.asList(1,3,4,6,7,10,15,20);
    public static void main(String[] args) {
        // list中比5大的元素个数
        long count = list.stream().filter( s -> s > 5).count();
        System.out.println(count);
    }
}
