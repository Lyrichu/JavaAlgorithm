package pers.lyrichu.LeetCode.Number;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultiplyStringsTest {
    @Test
    public void multiplyStringsTest() {
        MultiplyStrings multiplyStrings = new MultiplyStrings();
        assertEquals(multiplyStrings.solution("1234","345"),String.valueOf(1234*345));
        assertEquals(multiplyStrings.solution("0012","0345"),String.valueOf(12*345));
    }
}