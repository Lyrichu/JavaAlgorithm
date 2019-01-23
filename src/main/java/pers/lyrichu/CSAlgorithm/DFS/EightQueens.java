package pers.lyrichu.CSAlgorithm.DFS;
/*
八皇后问题
在 8×8 的棋盘上,放置 8 个皇后,使得她们互不攻击,每个皇后的攻击范围是同行、同列和同
对角线,要求找出所有解。
使用深度优先搜索
 */

public class EightQueens {
    private static int N = 8; // 皇后个数
    private static int[] history = new int[N]; // 第i行皇后所在的列
    private static int count = 0; // 解法个数

    // 检查第row行,第col列是否可以放置皇后
    // 需要检查行,列,对角线
    //其中(r1,c1)和(r2,c2)在同一条对角线的条件是:
    // r1-c1 = r2-c2 or r1+c1 = r2+c2
    public static boolean check(int row,int col){
        // 需要逐行检查当前行和之前的行是否有冲突
        boolean ok = true;
        for(int i=0;i<row;i++){
            if(col == history[i] || i+history[i] == row+col || i-history[i] == row -col){
                ok = false;
                break;
            }
        }
        return ok;
    }

    public static void solution(int i){
        // 遍历到第N行,得到一个解法
        if(i == N){
            count++;
            output();
        }
        // 逐个遍历当前行所有的列,找到每行所有的可行列
        for(int j=0;j<N;j++){
            boolean ok = check(i,j);
            if(ok){
                history[i] = j;
                solution(i+1);
            }
        }
    }

    public static void output(){
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(history[i] == j){
                    System.out.print("1 ");
                }else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        EightQueens.solution(0);
        System.out.println("There are "+EightQueens.count+" solutions!");
    }
}
