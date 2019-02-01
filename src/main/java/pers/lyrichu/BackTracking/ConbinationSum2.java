package pers.lyrichu.BackTracking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/*
描述
本题是 LeetCode Online Judge 上的”Combination Sum” 的变形,和上题略有不同。
给定一个数的集合 (C) 和一个目标数 (T),找到 C 中所有不重复的组合,让这些被选出来的数加
起来等于 T。
每一个数只能被选择一次。
注意:
• 所有的数(包括目标)都是正整数
• 一个组合(a 1 , a 2 , ·, a k )中的元素必须以非递减顺序排列
• 一个组合不能与另一个组合重复
例如,给定一组数 2,3,6,7, 和目标 7,则答案是
[7]
 */
public class ConbinationSum2 {

    private static int target;// 目标值
    private static int count; // 解法总数
    private static List<Integer> list = new ArrayList<Integer>();
    private static List<Integer> tmpList = new ArrayList<Integer>();
    private static List<List<Integer>> resList = new ArrayList<List<Integer>>();

    public static void init(){
        Scanner scanner = new Scanner(System.in);
        // input target
        System.out.println("请输入一个正整数target:");
        target = scanner.nextInt();
        System.out.println("请输入一组正整数,-1结束,以空格分隔");
        int i;
        while ((i =scanner.nextInt()) != -1){
            list.add(i);
        }
        Collections.sort(list); // 递增排序
    }

    // i表示当前遍历数字位置
    // gap 表示当前距离target的大小
    public static void solution(int i,int gap){
        if(gap == 0){
            resList.add(new ArrayList<Integer>(tmpList));
            count++;
            return;
        }
        // 这里是与ConbinationSum1不同的关键
        // 这里需要在循环选取下一个取值的时候设置一个标识符
        //用于判断当前选择的数字上一次有没有选择过
        int flag = -1;
        // 遍历下一个取值所有可能的状态
        for(int j=i;j<list.size();j++){
            int cur = list.get(j);
            // 当前的值上次已经选过,直接跳过
            if(cur == flag){
                continue;
            }
            // 不满足条件,剪枝
            if(gap < cur){
                return;
            }
            tmpList.add(cur);
            flag = cur; // 记录上一次选择的值
            //递归寻找下一个值
            solution(j+1,gap-cur);
            // 回溯
            tmpList.remove(tmpList.size()-1);
        }
    }

    public static void output(){
        System.out.printf("There are %d solutions!\n",count);
        for(List<Integer> list:resList){
            for(int n:list){
                System.out.print(n+" ");
            }
            System.out.println();
        }
    }

    public static void main(String args[]){
        ConbinationSum2.init();
        ConbinationSum2.solution(0,ConbinationSum2.target);
        ConbinationSum2.output();
    }
}
