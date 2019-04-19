package pers.lyrichu.LeetCode.Number;
/*
 * 第一个缺失的正数,https://leetcode-cn.com/problems/first-missing-positive/
 * solution reference:https://github.com/apachecn/awesome-algorithm/blob/master/docs/Leetcode_Solutions/Python/0041._First_Missing_Positive.md
 */

public class FirstMissingPositive {
    public int solution(int[] arr) {
        int oldMissing,missing;
        oldMissing = 0;
        missing = 1;
        while (oldMissing != missing) {
            oldMissing = missing;
            for (int i:arr) {
                if (i == oldMissing) {
                    missing = oldMissing+1;
                }
            }
        }
        return oldMissing;
    }
}
