package pers.lyrichu.LeetCode.Greedy;

import org.junit.Test;

import static org.junit.Assert.*;

public class CoupleHoldingHandsTest {
    @Test
    public void coupleHoldingHandsTest() {
        CoupleHoldingHands coupleHoldingHands = new CoupleHoldingHands();
        int[] row1 = new int[] {1,2,0,3};
        int[] row2 = new int[] {2,3,1,0};
        int[] row3 = new int[] {1,4,5,2,0,3};
        assertEquals(coupleHoldingHands.solution(row1),1);
        assertEquals(coupleHoldingHands.solution(row2),0);
        assertEquals(coupleHoldingHands.solution(row3),2);
    }
}