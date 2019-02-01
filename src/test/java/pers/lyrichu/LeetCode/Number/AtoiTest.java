package pers.lyrichu.LeetCode.Number;

import org.junit.Test;

import static org.junit.Assert.*;

public class AtoiTest {
    @Test
    public void atoiTest() {
        Atoi a = new Atoi();
        assertEquals(a.atoi("123"),123);
        assertEquals(a.atoi("  1234"),1234);
        assertEquals(a.atoi("  -235  "),-235);
        assertEquals(a.atoi("   +12 "),12);
        assertEquals(a.atoi("12345678901"),Integer.MAX_VALUE);
    }
}