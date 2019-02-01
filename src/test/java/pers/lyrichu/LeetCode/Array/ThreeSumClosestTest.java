package pers.lyrichu.LeetCode.Array;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreeSumClosestTest {
    @Test
    public void threeSumClosestTest() throws Exception{
        ThreeSumClosest tsc = new ThreeSumClosest();
        int[] arr1 = new int[]{-1,2,1,-4};
        int target1 = 2;
        int target2 = 1;
        assertEquals(tsc.threeSumClosest(arr1,target1),2);
        assertEquals(tsc.threeSumClosest(arr1,target2),2);
    }
}