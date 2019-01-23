package pers.lyrichu.LeetCode.DFS;

import org.junit.Test;

import static org.junit.Assert.*;

public class LetterCombinationsTest {
    @Test
    public void letterCombinationsTest() {
        LetterCombinations lc = new LetterCombinations();
        assertArrayEquals(
                lc.letterCombinations("23").toArray(),
                new String[] {
                        "ad","ae","af",
                        "bd","be","bf",
                        "cd","ce","cf"
                }
        );
    }
}