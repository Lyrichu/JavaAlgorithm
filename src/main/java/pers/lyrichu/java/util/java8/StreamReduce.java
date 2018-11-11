package pers.lyrichu.java.util.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReduce {
    private static List<Integer> list = Arrays.asList(1,2,3,4,5,6);

    public static void main(String[] args) {
        // 使用reduce求出list所有元素的和
        Optional<Integer> sum = list.stream().reduce( (a, b) -> a+b );
        sum.ifPresent(System.out::println);
    }
}
