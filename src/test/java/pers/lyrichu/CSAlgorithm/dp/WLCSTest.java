package pers.lyrichu.CSAlgorithm.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class WLCSTest {
    @Test
    public void wlcsTest() {
        WLCS wlcs = new WLCS();
        String s1 = "abcdefg";
        String s2 = "abcdhik";
        String s3 = "ahbkcid";
        System.out.println("rougeW between s1 and s2:" + wlcs.rougeW(s1,s2));
        System.out.println("rougeW between s1 and s3:" + wlcs.rougeW(s1,s3));
    }
}