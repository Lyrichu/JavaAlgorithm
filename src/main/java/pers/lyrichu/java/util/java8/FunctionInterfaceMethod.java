package pers.lyrichu.java.util.java8;

import java.util.function.Function;

public class FunctionInterfaceMethod {
    public static void main(String[] args) {
        // String to Integer
        Function<String,Integer> toInteger = Integer::valueOf;
        // Integer backto String
        Function<String,String> backToString = toInteger.andThen(String::valueOf);
        System.out.println(toInteger.apply("123") + 10); // 133
        System.out.println(backToString.apply("123")); // 123
    }
}
