package pers.lyrichu.LeetCode.Array;

/*
 * 跳跃游戏,https://leetcode-cn.com/problems/jump-game/
 * solution reference:https://leetcode-cn.com/problems/jump-game/comments/65697
 */
public class JumpGame {
    public boolean solution(int[] arr) {
        if (arr == null) {
            return false;
        }
        // 保存当前可以跳跃的最大坐标
        int maxIndex = arr[0];
        for (int i = 0;i < arr.length;i++) {
            if (i > maxIndex) {
                return false;
            }
            // 更新当前可以跳跃的最大坐标
            if (arr[i] + i > maxIndex) {
                maxIndex = arr[i] + i;
            }
        }
        return true;
    }
}
