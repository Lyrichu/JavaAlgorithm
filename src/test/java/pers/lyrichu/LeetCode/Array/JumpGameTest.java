package pers.lyrichu.LeetCode.Array;

import org.junit.Test;

import static org.junit.Assert.*;

public class JumpGameTest {
    @Test
    public void jumpGameTest() {
        JumpGame jumpGame = new JumpGame();
        int[] arr1 = new int[] {2,3,1,1,4};
        int[] arr2 = new int[] {1,2,1,0,1};
        assertTrue(jumpGame.solution(arr1));
        assertFalse(jumpGame.solution(arr2));
    }
}