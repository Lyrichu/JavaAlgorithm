package pers.lyrichu.LeetCode.String;
/*
 * Z 字形变换,https://leetcode-cn.com/problems/zigzag-conversion/
 * solution reference:https://github.com/apachecn/awesome-algorithm/blob/master/docs/Leetcode_Solutions/Python/0006._ZigZag_Conversion.md
 */

public class ZigZagConversion {
    public static String solution(String s, int numRows) {
        if (numRows == 1 || s.length() <= numRows) {
            return s;
        }
        String[] rows = new String[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = "";
        }
        int idx, step;
        idx = 0;
        step = 1;
        for (char c : s.toCharArray()) {
            rows[idx] += c;
            // 第一行，一直往下走
            if (idx == 0) {
                step = 1;
                // 最后一行，开始往上走
            } else if (idx == numRows - 1) {
                step = -1;
            }
            idx += step;
        }
        StringBuilder sb = new StringBuilder();
        for (String r : rows) {
            sb.append(r);
        }
        return sb.toString();
    }
}
