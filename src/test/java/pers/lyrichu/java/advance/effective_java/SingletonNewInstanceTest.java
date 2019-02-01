package pers.lyrichu.java.advance.effective_java;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingletonNewInstanceTest {

    @Test
    public void singletonTest() {
        SingletonNewInstance s1 = SingletonNewInstance.singleton();
        SingletonNewInstance s2 = SingletonNewInstance.singleton();
        assertSame(s1,s2);
    }
}