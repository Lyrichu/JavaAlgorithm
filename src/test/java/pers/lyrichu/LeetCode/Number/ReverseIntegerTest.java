package pers.lyrichu.LeetCode.Number;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReverseIntegerTest {
    @Test
    public void reverseIntegerTest() {
        ReverseInteger ri = new ReverseInteger();
        assertEquals(ri.reverseInteger(123),321);
        assertEquals(ri.reverseInteger(-234),-432);
        assertEquals(ri.reverseInteger(12345678),87654321);
    }
}