package pers.lyrichu.LeetCode.Number;

/*
 * 整数反转:https://leetcode-cn.com/problems/reverse-integer/
 */
public class ReverseInteger {

    public int reverseInteger(int x) {
        int flag = 1; // 数字符号
        // 统一使用正整数处理
        if(x < 0){
            flag = -1;
            x = -x;
        }
        int i,t;
        long j;
        i = x;
        j = 0;
        while(i > 0){
            t = i % 10;
            j = t + j*10;
            i /= 10;
        }
        j *= flag;
        if(j > Integer.MAX_VALUE || j < Integer.MIN_VALUE)
            return 0;
        return (int)j;
    }
}
