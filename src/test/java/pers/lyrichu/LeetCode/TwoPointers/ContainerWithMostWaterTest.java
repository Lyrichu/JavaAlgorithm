package pers.lyrichu.LeetCode.TwoPointers;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerWithMostWaterTest {
    @Test
    public void containerWithMostWaterTest() {
        ContainerWithMostWater container = new ContainerWithMostWater();
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        assertEquals(container.solution(height),49);
    }
}