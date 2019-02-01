package pers.lyrichu.LeetCode.DP;

/*
 *最长回文子串:https://leetcode-cn.com/problems/longest-palindromic-substring
 */
public class LongestPalindrome {

    public String longestPalindrome(String s) {
        int n = s.length();
        if(n <= 1){
            return s;
        }
        boolean[][] dp = new boolean[n][n];
        int begin = 0,end = 0,maxLen = 0;
        for(int i = 0;i<n;i++){
            for(int j=0;j<=i;j++){
                // 遍历到最后,i,j之间是一个回文子串,更新最大回文子串长度
                if(i == j){
                    dp[j][i] = true;
                    if(maxLen < i-j+1){
                        begin = j;
                        end = i;
                        maxLen = i-j+1;
                    }
                }else{
                    // 扩展规则,当前最左侧和最右侧的字符必须相等
                    // 同时j+1,i-1之间是一个回文串,或者这是一个长度为2的字符串
                    if(s.charAt(i) == s.charAt(j) && (i-j == 1 || dp[j+1][i-1])){
                        dp[j][i] = true;
                        if(maxLen < i-j+1){
                            begin = j;
                            end = i;
                            maxLen = i-j+1;
                        }
                    }else
                        dp[j][i] = false;
                }
            }
        }
        return s.substring(begin,end+1);
    }
}
