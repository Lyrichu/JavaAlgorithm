package pers.lyrichu.LeetCode.Number;

/*
 * 字符串相乘,reference:https://leetcode-cn.com/problems/multiply-strings/comments/
 */
public class MultiplyStrings {

    public String solution(String num1, String num2) {
        int n1 = num1.length();
        int n2 = num2.length();
        int[] resArr = new int[n1+n2];
        for (int i = n1-1;i>=0;i--) {
            for (int j = n2-1;j>=0;j--) {
                int mul = (num1.charAt(i)-'0')*(num2.charAt(j)-'0');
                // 先加低位
                mul += resArr[i+j+1];
                resArr[i+j] += mul / 10;
                resArr[i+j+1] = mul % 10;
            }
        }
        // 去除前导0
        int i = 0;
        while(i < resArr.length-1 && resArr[i] == 0) {
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for (int k = i;k < resArr.length;k++) {
            sb.append(resArr[k]);
        }
        return sb.toString();
    }
}
