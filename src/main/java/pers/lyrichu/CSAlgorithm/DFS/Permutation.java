package pers.lyrichu.CSAlgorithm.DFS;

import java.util.Scanner;
/*
描述
给出一个正整数 n, 请输出 n 的所有全排列
输入
一个整数 n(1 ≤ n ≤ 10)
输出
一共 n! 行,每行 n 个用空格隔开的数,表示 n 的一个全排列。并且按全排列的字典序输出。
样例输入
3
样例输出
1 2 3
1 3 2
2 1 3
2 3 1
3 1 2
3 2 1
 */

// 使用深度优先搜索,输出n的全排列
public class Permutation {
    private static int MAXN = 10;
    private static int[] history = new int[MAXN];
    private static int n;

    public static void solution(int i){
        if(i == n){
            for(int j=0;j<n;j++){
                System.out.print(history[j]+" ");
            }
            System.out.println();
            return;
        }
        // 遍历逐个尝试第i个位置可以放置的数字
        for(int j=1;j<=n;j++){
            boolean ok = true;
            for(int k=0;k<i;k++){
                // 之前已经使用过该数字
                if(history[k] == j){
                    ok = false;
                    break;
                }
            }
            // 之前没有使用过该数字
            if(ok){
                history[i] = j;
                solution(i+1);
            }
        }
    }

    public static void init(){
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
    }

    public static void main(String args[]){
        Permutation.init();
        Permutation.solution(0);
    }
}
