package pers.lyrichu.LeetCode.String;

import org.junit.Test;

import static org.junit.Assert.*;

public class ZigZagConversionTest {
    @Test
    public void zigZagConversionTest() {
        assertEquals(ZigZagConversion.solution("AB",1),"AB");
        assertEquals(ZigZagConversion.solution("ABDF",4),"ABDF");
        assertEquals(ZigZagConversion.solution("LEETCODEISHIRING",3),"LCIRETOESIIGEDHN");
        assertEquals(ZigZagConversion.solution("LEETCODEISHIRING",4),"LDREOEIIECIHNTSG");
    }
}