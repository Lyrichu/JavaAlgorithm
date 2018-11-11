package pers.lyrichu.java.util.java8;

// lambda 内部对于实例字段以及静态变量是既可读,又可写的
public class LambdaVisitVarFieldAndStaticVar {
    private static int outerStaticNum;

    interface Converter<F,T> {
        T convert(F from);
    }

    public static void main(String[] args) {
        Converter<Integer,String> converter = (from) -> {
            outerStaticNum = 10;
            return String.valueOf(from+outerStaticNum);
        };
        String converted = converter.convert(1); // 11
        System.out.println(converted);
    }
}
