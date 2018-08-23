package pers.lyrichu.LeetCode.TwoPointers;

import java.util.Arrays;
import java.util.HashSet;

// 翻转一个字符串中的所有元音字母
public class ReverseVowels {
    private static final HashSet<Character> vowels =
            new HashSet<Character>(Arrays.asList('a','e','i','o','u','A','E','I','O','U'));

    public static void main(String args[]){
        String s = "azfbuoif";
        String res = solution(s);
        System.out.println(
                "元音翻转之前的字符串:"+s+
                        ",元音翻转之后的字符串:"+res
        );
    }

    public static String solution(String s){
        int i = 0;
        int j = s.length()-1;
        char[] result = new char[s.length()];
        while (i<=j){
            char a = s.charAt(i);
            char b = s.charAt(j);
            if(!vowels.contains(a)){
                result[i++] = a;
            }else if(!vowels.contains(b)){
                result[j--] = b;
            }else{
                result[i++] = b;
                result[j--] = a;
            }
        }
        return new String(result);
    }
}
