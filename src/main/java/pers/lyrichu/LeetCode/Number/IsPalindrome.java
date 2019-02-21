package pers.lyrichu.LeetCode.Number;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 判断一个数字是否是回文数,https://leetcode-cn.com/problems/palindrome-number
 */
public class IsPalindrome {
    /*
     * 比较直观，但是效率很低的办法,将整数转化为List进行比较
     */
    public boolean solution1(int x) {
        if (x < 0) {
            return false;
        }
        List<Integer> res = intToList(x);
        List<Integer> res1 = new ArrayList<>(res);
        Collections.reverse(res1);
        return res.equals(res1);
    }
    /*
     * 边反转，边比较
     */
    public boolean solution2(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        // 反转一半
        int reversed = 0;
        while (x > reversed) {
            reversed = reversed* 10 + x % 10;
            x /= 10;
        }
        // 如果原来的数字有偶数位，则直接比较x和reversed
        // 否则reversed需要去除最后一位(即原来数字的中间位),然后再与x比较
        return reversed == x || (reversed / 10) == x;
    }

    private List<Integer> intToList(int x) {
        List<Integer> res = new ArrayList<>();
        int i;
        i = x % 10;
        res.add(i);
        x /= 10;
        while (x != 0) {
            i = x % 10;
            res.add(i);
            x /= 10;
        }
        Collections.reverse(res);
        return res;
    }
}
