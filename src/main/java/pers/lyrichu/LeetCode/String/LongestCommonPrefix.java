package pers.lyrichu.LeetCode.String;

/*
 *一组字符串最长公共前缀:https://leetcode-cn.com/problems/longest-common-prefix/
 */
public class LongestCommonPrefix {

    public String longestCommonPrefix(String[] strs) {
        if(strs.length == 0)
            return "";
        int n = min(strs);
        int i = 0;
        while(i < n){
            if(!check(i,strs))
                break;
            i++;
        }
        return strs[0].substring(0,i);
    }

    /*
     * 字符串数组最短字符串长度
     */
    public int min(String[] strs){
        int n = strs[0].length();
        for(int i=1;i<strs.length;i++){
            int len = strs[i].length();
            if(len < n)
                n = len;
        }
        return n;
    }

    /*
     * 检查当前所有字符串位置n字符是否相同
     */
    public boolean check(int n,String[] strs){
        char c = strs[0].charAt(n);
        for(int i=1;i<strs.length;i++){
            if(strs[i].charAt(n) != c){
                return false;
            }
        }
        return true;
    }
}
