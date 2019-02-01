package pers.lyrichu.Graph;

import java.io.IOException;
import java.util.Scanner;
/*
农民John购买了他的牧场的长宽为WxH的卫星照片(1<=W<=80,1<=H<=1000),希望可以决定他的最大的连续相连牧场的面积.
当任何两个牧场的像素可以水平或者垂直相连的时候,我们就可以认为这两个牧场是相连的.
下面使用*表示牧场区域,使用.表示非牧场区域,下面是一个10x5的卫星样图:
..*.....**
.**..*****
.*...*....
..****.***
..****.***
输入:
第一行:W H
H行W列的*或者.
输出:
最大的相邻牧场的面积
样例输入:
10 5
..*.....**
.**..*****
.*...*....
..****.***
..****.***
输出:
16
 */

public class ContiguousPasture {
    private static int W = 80;//列
    private static int H = 1000;//行
    private static char[][] matrix = new char[H+2][W+2];
    private static int max = 0;  // 最大面积
    private static int count;
    private static void init() throws IOException {
        for(int i=0;i<=H+1;i++){
            for(int j=0;j<=W+1;j++){
                matrix[i][j] = '.';
            }
        }
        Scanner scanner = new Scanner(System.in);
        W = scanner.nextInt();
        H = scanner.nextInt();
        int i = 0;
        char c;
        int row,col;
        while (i < W*H){
            if((c=(char)System.in.read()) == '.' || c == '*'){
                i++;
                row = (i-1)/W+1;
                col = i - W*(row-1);
                matrix[row][col] = c;
            }
        }
    }

    private static void outputMatrix(){
        for(int i=0;i<=H+1;i++){
            for(int j=0;j<=W+1;j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    // 从第row行,第col列开始继续搜索
    private static void dfs(int row,int col){
        if(matrix[row][col] == '.'){
            return;
        }
        // 标记防止重复访问
        matrix[row][col] = '.';
        // 访问合法
        count++;
        dfs(row-1,col);
        dfs(row+1,col);
        dfs(row,col-1);
        dfs(row,col+1);
    }

    private static void solution(){
        // 逐个遍历每个可能点
        for(int row=1;row<=H;row++){
            for(int col=1;col<=W;col++){
                // 满足条件
                if(matrix[row][col] == '*'){
                    count = 0;
                    dfs(row,col);
                }
                if(count > max){
                    max = count;
                }
            }
        }
    }

    public static void main(String args[]) throws IOException{
        ContiguousPasture.init();
//        ContiguousPasture.outputMatrix();
        ContiguousPasture.solution();
        System.out.println(ContiguousPasture.max);
    }
}
