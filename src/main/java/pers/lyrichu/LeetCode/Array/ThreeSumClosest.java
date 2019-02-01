package pers.lyrichu.LeetCode.Array;

import java.util.Arrays;

/*
 * 最接近的三数之和:https://leetcode-cn.com/problems/3sum-closest/
 */
public class ThreeSumClosest {
    public int threeSumClosest(int[] arr,int target) throws Exception{
        if (null == arr || arr.length < 3) {
            throw new IllegalArgumentException("input arr's size must >= 3!");
        }
        // 排序
        Arrays.sort(arr);
        int res = Integer.MAX_VALUE;
        // 三数之和与target的最小绝对差
        int minGap = Integer.MAX_VALUE;
        int n = arr.length;
        for (int i =0;i<n;i++) {
            int left = i+1;
            int right = n-1;
            if (i > 0 && arr[i] == arr[i-1]) {
                continue;
            }
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                int gap = Math.abs(sum-target);
                // gap 为0最小,直接返回
                if (gap == 0) {
                    return sum;
                } else if (sum < target) {
                    left++;
                    if(gap < minGap) {
                        // update
                        minGap = gap;
                        res = sum;
                    }
                } else {
                    right--;
                    if(gap < minGap) {
                        minGap = gap;
                        res = sum;
                    }
                }
            }
        }
        return res;
    }
}
