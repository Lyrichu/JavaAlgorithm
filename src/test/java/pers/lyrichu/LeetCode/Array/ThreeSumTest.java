package pers.lyrichu.LeetCode.Array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ThreeSumTest {
    @Test
    public void threeSumTest() {
        ThreeSum ts = new ThreeSum();
        int[] arr1 = new int[] {-1,0,1,2,-1,-4};
        List<int[]> res1 = Arrays.asList(
                new int[]{-1,-1,2},
                new int[]{-1,0,1}

        );
        // 对数组施加断言
        List<int[]> real1 = ts.threeSum(arr1);
        assertEquals(res1.size(),real1.size());
        for (int i = 0;i<res1.size();i++) {
            assertArrayEquals(res1.get(i),real1.get(i));
        }
        int[] arr2 = new int[] {0,-1,-2,1,2,3};
        List<int[]> res2 = new ArrayList<int[]>() {
            {
                add(new int[]{-2,-1,3});
                add(new int[]{-2,0,2});
                add(new int[]{-1,0,1});
            }
        };
        List<int[]> real2 = ts.threeSum(arr2);
        assertEquals(res2.size(),real2.size());
        for (int i = 0;i < res2.size();i++) {
            assertArrayEquals(res2.get(i),real2.get(i));
        }
    }

    public void printArr(List<int[]> list) {
        System.out.print("[\n");
        for (int[] arr:list) {
            System.out.print("[");
            for (int i =0;i<arr.length-1;i++) {
                System.out.printf("%d,",arr[i]);
            }
            System.out.printf("%d",arr[arr.length-1]);
            System.out.print("]\n");
        }
        System.out.print("]");
    }

}