package pers.lyrichu.LeetCode.Number;

import org.junit.Test;

import static org.junit.Assert.*;

public class FirstMissingPositiveTest {
    @Test
    public void firstMissingPositiveTest() {
        FirstMissingPositive firstMissingPositive = new FirstMissingPositive();
        int[] arr1 = new int[] {0,2,3,4};
        int[] arr2 = new int[] {1,2,0};
        int[] arr3 = new int[] {3,4,-1,1};
        int[] arr4 = new int[] {7,8,9,11,12};
        assertEquals(firstMissingPositive.solution(arr1),1);
        assertEquals(firstMissingPositive.solution(arr2),3);
        assertEquals(firstMissingPositive.solution(arr3),2);
        assertEquals(firstMissingPositive.solution(arr4),1);
    }
}