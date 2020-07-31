package pers.lyrichu.java.advance.new_features.others;

import org.junit.Test;

import java.util.Optional;

public class OptionTest {

    @Test
    public void test01() {
        Optional<String> os1 = Optional.ofNullable(null);
        System.out.println(os1.isPresent());

        Optional<String> os2 = Optional.of("12");
        System.out.println(os2.isPresent());

        System.out.println(
                os1.map(s -> "aa:" + s + ":bb").orElse("error!")
        );
        System.out.println(
                os2.map(s -> "aa:" + s + ":bb").orElse("error!")
        );
    }
}
