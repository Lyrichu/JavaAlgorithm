package pers.lyrichu.LeetCode.TwoPointers;

/*
 * 接雨水,https://leetcode-cn.com/problems/trapping-rain-water
 * solution reference:https://www.cnblogs.com/felixfang/p/3713197.html
 */
public class TrapRainWater {

    // 使用双指针法
    public int solution(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int maxWater = 0;
        // 先找到最大高度以及位置
        int maxHeight,maxIndex;
        maxHeight = height[0];
        maxIndex = 0;
        for (int i = 1;i < height.length;i++) {
            if (height[i] > maxHeight) {
                maxHeight = height[i];
                maxIndex = i;
            }
        }
        int maxLeft,maxRight;
        // 从最左到最高遍历
        maxLeft = height[0];
        for (int i = 1;i < maxIndex;i++) {
            if (maxLeft < height[i]) {
                maxLeft = height[i];
            } else {
                // 补全，使得从左到最高满足高度递增状态
                maxWater += maxLeft - height[i];
            }
        }
        // 从最右到最高遍历
        maxRight = height[height.length-1];
        for (int i = height.length-2;i > maxIndex;i--) {
            if (maxRight < height[i]) {
                maxRight = height[i];
            } else {
                maxWater += maxRight - height[i];
            }
        }
        return maxWater;
    }
}
