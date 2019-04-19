package pers.lyrichu.LeetCode.Array;

/*
 * 跳跃游戏2,https://leetcode-cn.com/problems/jump-game-ii/
 * solution reference:https://github.com/apachecn/awesome-algorithm/blob/master/docs/Leetcode_Solutions/Python/0045._Jump_Game_II.md
 */
public class JumpGame2 {
    public int solution(int[] nums) {
        int step,furthestIndex,endIndex;
        step = furthestIndex = endIndex = 0;
        for (int i = 0;i < nums.length-1;i++) {
            furthestIndex = Math.max(furthestIndex,nums[i]+i);
            if (furthestIndex >= nums.length-1) {
                return step+1;
            }
            if (endIndex == i) {
                endIndex = furthestIndex;
                step++;
            }
        }
        return step;
    }
}
