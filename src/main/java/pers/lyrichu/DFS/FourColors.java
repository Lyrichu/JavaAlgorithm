package pers.lyrichu.DFS;

import java.util.Scanner;

/*
四色问题
描述
给定 N (N ≤ 8) 个点的地图,以及地图上各点的相邻关系,请输出用 4 种颜色将地图涂色的所
有方案数(要求相邻两点不能涂成相同的颜色)
。
数据中 0 代表不相邻,1 代表相邻。
输入
第一行一个整数 N ,代表地图上有 N 个点。
接下来 N 行,每行 N 个整数,每个整数是 0 或者 1。第 i 行第 j 列的值代表了第 i 个点和第 j 个
点之间是相邻的还是不相邻,相邻就是 1,不相邻就是 0。我们保证 a[i][j] = a[j][i]。
输出
染色的方案数
样例输入
8
0 0 0 1 0 0 1 0
0 0 0 0 0 1 0 1
0 0 0 0 0 0 1 0
1 0 0 0 0 0 0 0
0 0 0 0 0 0 0 0
0 1 0 0 0 0 0 0
1 0 1 0 0 0 0 0
0 1 0 0 0 0 0 0
样例输出
15552
 */
// 使用深度有限搜索
public class FourColors {
    private static int MAXN = 8;
    // 用于保存每个点的颜色
    private static int[] history = new int[MAXN];

    private static int count = 0;
    private static int num;
    private static int[][] arr = new int[MAXN][MAXN];

    public static void solution(int i){
        if(i == num){
            count++;
            return;
        }
        // 用1,2,3,4表示四种颜色,0表示没有染色
        for(int c=1;c<=4;c++){
            boolean ok = true; // 用于判断点染色是否合法
            for(int j=0;j<i;j++) {
                if (arr[i][j] == 1 && history[j] == c){
                    // 相邻且颜色相同
                    ok = false;
                }
            }
            if(ok){
                history[i] = c;
                solution(i+1);
            }
        }
    }

    public static void initArr(){
        Scanner scanner = new Scanner(System.in);
        num = scanner.nextInt();
        for(int i=0;i<num;i++){
            for(int j=0;j<num;j++){
                arr[i][j] = scanner.nextInt();
            }
        }
    }

    public static int getCount() {
        return count;
    }

    public static void main(String args[]){
        FourColors.initArr();
        FourColors.solution(0);
        System.out.println(FourColors.getCount());
    }
}
