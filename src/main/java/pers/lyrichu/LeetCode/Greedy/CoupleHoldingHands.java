package pers.lyrichu.LeetCode.Greedy;

import java.util.HashMap;
import java.util.Map;

/*
 *情侣牵手,https://leetcode-cn.com/problems/couples-holding-hands/
 * solution reference:https://github.com/apachecn/awesome-algorithm/blob/master/docs/Leetcode_Solutions/Python/0765._Couples_Holding_Hands.md
 */
public class CoupleHoldingHands {
    public int solution(int[] row) {
        // 首先构造一个loopup map
        Map<Integer,Integer> lookup = new HashMap<>();
        for (int i = 0;i < row.length;i++) {
            lookup.put(row[i],i);
        }
        int count = 0;
        int p1,p2;
        for (int i = 0;i < row.length;i += 2) {
            p1 = row[i]; // 第一个人
            // 配对的第2个人
            if (p1 % 2 == 0) {
                p2 = p1 + 1;
            } else {
                p2 = p1 - 1;
            }
            // 如果p1和p2不是处于相邻的位置
            if (row[i+1] != p2) {
                // 交换
                int s = row[i+1];
                lookup.put(s,lookup.get(p2));
                lookup.put(p2,i+1);
                row[i+1] = p2;
                row[lookup.get(s)] = s;
                count++;
            }
        }
        return count;
    }
}
