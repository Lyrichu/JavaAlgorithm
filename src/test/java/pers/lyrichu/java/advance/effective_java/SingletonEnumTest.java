package pers.lyrichu.java.advance.effective_java;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonEnumTest {
    @Test
    public void singletonTest() {
        SingletonEnum s1 = SingletonEnum.SINGLE_INSTANCE;
        SingletonEnum s2 = SingletonEnum.SINGLE_INSTANCE;
        assertSame(s1,s2);
    }
}