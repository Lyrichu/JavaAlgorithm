package pers.lyrichu.java.util.java8;

public class LambdaVisitLocalVariable {
    interface Converter<F,T> {
        T convert(F from);
    }

    public static void main(String[] args) {
        // 可以在lambda表达式中直接访问外部的局部变量
        // 但是外部的局部变量隐式具有final的语义,即是不可以修改的
        int num = 1; // final 可以省略,但是仍然是final的语义
        Converter<Integer,String> converter = (from) -> String.valueOf(from+num);
        String converted = converter.convert(2); // 2 + 1 = 3
        System.out.println(converted);
    }
}
