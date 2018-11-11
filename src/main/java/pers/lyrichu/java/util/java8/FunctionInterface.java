package pers.lyrichu.java.util.java8;

// lambda映射到一个单方法的接口上
public class FunctionInterface {
    @FunctionalInterface
    interface Converter<F,T> {
        T convert(F from);
    }

    public static void main(String[] args) {
        // 构造一个String to Integer 的converter
        Converter<String,Integer> converter = (from) -> Integer.valueOf(from);
        Integer converted = converter.convert("123");
        System.out.println(converted);
    }
}
