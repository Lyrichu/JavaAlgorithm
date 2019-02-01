package pers.lyrichu.java.advance.effective_java;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonPrivateFinalTest {
    @Test
    public void singletonTest() {
        SingletonPrivateFinal s1 = SingletonPrivateFinal.SINGLE_INSTANCE;
        SingletonPrivateFinal s2 = SingletonPrivateFinal.SINGLE_INSTANCE;
        assertTrue(s1 == s2);
    }
}