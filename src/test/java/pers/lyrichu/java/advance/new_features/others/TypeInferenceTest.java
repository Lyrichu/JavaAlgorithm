package pers.lyrichu.java.advance.new_features.others;

import org.junit.Test;

public class TypeInferenceTest {

    @Test
    public void test1() {
        Value<String> value = new Value<>();
        System.out.println(value.getOrDefault("22",Value.defaultValue()));
    }

    static class Value<T> {
        public static<T> T defaultValue() {
            return null;
        }

        public T getOrDefault(T value,T defaultValue) {
            return value != null ? value : defaultValue;
        }
    }
}
