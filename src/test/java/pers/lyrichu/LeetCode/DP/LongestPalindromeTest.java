package pers.lyrichu.LeetCode.DP;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestPalindromeTest {
    @Test
    public void longestPalindromeTest() {
        LongestPalindrome lp = new LongestPalindrome();
        assertEquals(lp.longestPalindrome("babab"),"babab");
        assertEquals(lp.longestPalindrome("cbbd"),"bb");
        assertEquals(lp.longestPalindrome("abdfdcc"),"dfd");
    }
}