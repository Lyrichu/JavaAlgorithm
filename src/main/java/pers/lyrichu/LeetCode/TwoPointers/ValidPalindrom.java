package pers.lyrichu.LeetCode.TwoPointers;

// 可以删除字符串中的一个字符，判断是否可以构成回文串
public class ValidPalindrom {
    public static void main(String args[]){
        String s = "abcdeba";
        System.out.println(s+" 删除一个字符"+(solution(s) ?" 是":" 不是")+"回文串!");
    }

    public static boolean solution(String s){
        int i = -1;
        int j = s.length();
        while (++i < --j){
            if(s.charAt(i) != s.charAt(j)){
                return isValidPalindrom(s,i,j-1) || isValidPalindrom(s,i+1,j);
            }
        }
        return true;
    }

    // 判断s,index i到index j之间的字符串是否是回文串
    public static boolean isValidPalindrom(String s,int i,int j){
        while (i<j){
            if(s.charAt(i++) != s.charAt(j--)){
                return false;
            }
        }
        return true;
    }
}
