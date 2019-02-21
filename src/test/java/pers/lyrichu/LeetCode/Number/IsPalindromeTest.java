package pers.lyrichu.LeetCode.Number;

import org.junit.Test;

import static org.junit.Assert.*;

public class IsPalindromeTest {
    @Test
    public void isPalindromeTest() {
        IsPalindrome palindrome = new IsPalindrome();
        assertTrue(palindrome.solution1(101));
        assertTrue(palindrome.solution2(101));
        assertFalse(palindrome.solution1(101012));
        assertFalse(palindrome.solution2(101012));
        assertFalse(palindrome.solution1(-232));
        assertFalse(palindrome.solution2(-232));
        assertTrue(palindrome.solution1(0));
        assertTrue(palindrome.solution2(0));
        assertTrue(palindrome.solution1(1324231));
        assertTrue(palindrome.solution2(1324231));
    }
}