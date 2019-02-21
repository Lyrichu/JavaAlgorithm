package pers.lyrichu.LeetCode.TwoPointers;

/*
 * 盛有最多水的容器,https://leetcode-cn.com/problems/container-with-most-water
 */
public class ContainerWithMostWater {

    /*
     * 使用双指针法
     */
    public int solution(int[] height) {
        int mA = 0; // 最大面积
        int l = 0;
        int r = height.length - 1;
        while (l < r) {
            mA = Math.max(mA,Math.min(height[l],height[r])*(r-l));
            if (height[l] < height[r]) {
                l++;
            } else {
                r--;
            }
        }
        return mA;
    }
}
