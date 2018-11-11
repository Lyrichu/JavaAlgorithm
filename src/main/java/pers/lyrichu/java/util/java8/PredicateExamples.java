package pers.lyrichu.java.util.java8;

import java.util.Objects;
import java.util.function.Predicate;

public class PredicateExamples {
    public static void main(String[] args) {
        Predicate<String> predicate = (s) -> s.length() > 1;
        System.out.println(predicate.test("abc")); // true
        System.out.println(predicate.test("a")); // false
        System.out.println(predicate.negate().test("a")); // true
        Predicate<Object> notNull = Objects::nonNull;
        System.out.println(notNull.test(null)); // false
        System.out.println(notNull.test("ab")); // true

        // isEmpty
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
        System.out.println(isEmpty.test("")); // true
        System.out.println(isNotEmpty.test("abf")); // true
    }
}
