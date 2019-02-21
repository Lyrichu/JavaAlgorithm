package pers.lyrichu.CSAlgorithm.dp;
/*
 * 加权最长公共子序列(weighted longest common subsequence),
 * 自动文摘评估方法:ROUGE-W使用到了wlcs的方法，详见:
 * ROUGE: A Package for Automatic Evaluation of Summaries:
 * http://www.aclweb.org/anthology/W04-1013
 */
public class WLCS {
    private double beta = 1000;

    public double wlcs(String s1,String s2) {
        if (s1 == null || s2 == null
            || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        double[][] dp = new double[s1.length()+1][s2.length()+1];
        double[][] w = new double[s1.length()+1][s2.length()+1];
        for (int i = 0;i <=s1.length();i++) {
            for (int j = 0;j <= s2.length();j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                    w[i][j] = 0;
                } else {
                    if (s1.charAt(i-1) == s2.charAt(j-1)) {
                        double k = w[i-1][j-1];
                        dp[i][j] = dp[i-1][j-1] + func(k+1) - func(k);
                        w[i][j] = k + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                        w[i][j] = 0;
                    }
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    private double func(double n) {
        return n*n;
    }

    private double invFunc(double n) {
        return Math.sqrt(n);
    }

    public double rougeW(String s1,String s2) {
        if (s1 == null || s2 == null
             || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        double wlcs = wlcs(s1,s2);
        double r = invFunc(wlcs/func(s1.length()));
        double p = invFunc(wlcs/func(s2.length()));
        return ((1+beta*beta)*r*p)/(r+beta*p);
    }

}
