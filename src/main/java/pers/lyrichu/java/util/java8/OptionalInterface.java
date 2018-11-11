package pers.lyrichu.java.util.java8;

import java.util.Optional;

public class OptionalInterface {
    public static void main(String[] args) {
        Optional<String> ops = Optional.of("mix");
        System.out.println(ops.isPresent()); // 是否存在内容(不为null),true
        // 获取optional的内容
        System.out.println(ops.get()); // mix
        System.out.println(ops.orElse("other")); //mix
        ops.ifPresent(
                (s) -> System.out.println(s.charAt(0))
        );
    }
}
