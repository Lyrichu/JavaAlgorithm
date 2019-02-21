package pers.lyrichu.LeetCode.TwoPointers;

import org.junit.Test;

import static org.junit.Assert.*;

public class TrapRainWaterTest {
    @Test
    public void trapRainWaterTest() {
        TrapRainWater trap = new TrapRainWater();
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        assertEquals(trap.solution(height),6);
        assertEquals(trap.solution(null),0);
    }
}