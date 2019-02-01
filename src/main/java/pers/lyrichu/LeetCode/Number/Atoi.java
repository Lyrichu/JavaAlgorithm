package pers.lyrichu.LeetCode.Number;

/*
 * 字符串转整数:https://leetcode-cn.com/problems/string-to-integer-atoi/
 */
public class Atoi {
    public int atoi(String str) {
        if (str == null || str.length() < 1)
            return 0;
        // 去除两侧的空白符
        String s= str.trim();
        if (s.length() < 1)
            return 0;
        int i=0;
        char flag = '+';
        double res=0;
        if (s.charAt(i) == '-') {
            flag = '-';
            i++;
        } else if (s.charAt(i) == '+')
            i++;
        while (i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9'){
            res = s.charAt(i)-'0'+res*10;
            i++;
        }
        if (flag == '-')
            res= -res;
        if ( res >= Integer.MAX_VALUE)
            return Integer.MAX_VALUE;
        if (res <= Integer.MIN_VALUE)
            return Integer.MIN_VALUE;
        return (int)res;
    }
}
