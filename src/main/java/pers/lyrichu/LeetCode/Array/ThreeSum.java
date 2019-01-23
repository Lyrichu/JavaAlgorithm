package pers.lyrichu.LeetCode.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 三数之和:https://leetcode-cn.com/problems/3sum/
 */
public class ThreeSum {
    public List<int[]> threeSum(int[] arr) {
        List<int[]> res = new ArrayList<>();
        if (arr == null || arr.length < 3) {
            return res;
        }
        // 首先对数组进行排序
        Arrays.sort(arr);
        for (int i = 0;i<arr.length;i++) {
            if (i > 0 && arr[i] == arr[i-1]) {
                continue;
            }
            int left = i+1;
            int right = arr.length - 1;
            while (left < right) {
                int sum = arr[i] + arr[left] + arr[right];
                if (sum == 0) {
                    // 添加一个可行解
                    res.add(new int[]{arr[i],arr[left],arr[right]});
                    // 左边前进
                    left++;
                    // 右边后退
                    right--;
                    // 遇见相同的数字则直接跳过
                    while (left < right && arr[left] == arr[left-1]) {
                        left++;
                    }
                    while (left < right && arr[right] == arr[right+1]) {
                        right--;
                    }
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return res;
    }
}
