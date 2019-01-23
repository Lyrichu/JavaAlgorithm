package pers.lyrichu.LeetCode.String;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestCommonPrefixTest {
    @Test
    public void longestCommonPrefixTest() {
        LongestCommonPrefix lcp = new LongestCommonPrefix();
        String[] strArr1 = new String[] {"abcf","abf","abhg"};
        assertEquals(lcp.longestCommonPrefix(strArr1),"ab");
        String[] strArr2 = new String[] {"abcfgg","abcf","abcfffg"};
        assertEquals(lcp.longestCommonPrefix(strArr2),"abcf");
        String[] strArr3 = new String[] {"abcf","cdfs","hgsd"};
        assertEquals(lcp.longestCommonPrefix(strArr3),"");
    }
}