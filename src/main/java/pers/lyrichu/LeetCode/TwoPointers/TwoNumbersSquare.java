package pers.lyrichu.LeetCode.TwoPointers;

// 判断一个非负整数是否是两个非负整数的平方和
public class TwoNumbersSquare {
    public static void main(String args[]){
        int target = 4;
        boolean res = solution(target);
        System.out.println((res ? "存在":"不存在")+"两个非负整数的和为"+target);
    }

    public static boolean solution(int target){
        if(target < 0){
            return false;
        }
        int i = 0;
        int j = (int)Math.sqrt(target);
        while (i <= j){
            int square = i*i+j*j;
            if(square == target){
                return true;
            }else if(square < target){
                i++;
            }else{
                j--;
            }
        }
        return false;
    }
}
