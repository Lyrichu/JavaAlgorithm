package pers.lyrichu.CSAlgorithm.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LCSTest {
    @Test
    public void lcsTest() {
        LCS lcs = new LCS();
        String s1 = "abcf";
        String s2 = "adfg";
        String s3 = "acfb";
        String s4 = "";
        assertEquals(lcs.lcs(s1,s2),2);
        assertEquals(lcs.lcs(s1,s4),0);
        assertEquals(lcs.lcs(s2,null),0);
        assertEquals(lcs.lcs(s2,s3),2);
    }
}