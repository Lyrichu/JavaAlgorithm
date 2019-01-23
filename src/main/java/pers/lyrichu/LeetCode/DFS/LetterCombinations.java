package pers.lyrichu.LeetCode.DFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *电话号码的字母组合:https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 */
public class LetterCombinations {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        Map<Character,String> map = new HashMap<>();
        map.put('2',"abc");
        map.put('3',"def");
        map.put('4',"ghi");
        map.put('5',"jkl");
        map.put('6',"mno");
        map.put('7',"pqrs");
        map.put('8',"tuv");
        map.put('9',"wxyz");
        int n = digits.length();
        if(n == 0)
            return res;
        dfs("",digits,res,map);
        return res;
    }

    public void dfs(String s,String digits,List<String> res,Map<Character,String> map){
        if(digits.length() == 0){
            res.add(s);
            return;
        }
        char c = digits.charAt(0);
        int n = digits.length();
        String tmp = map.get(c);
        for(int i=0;i<tmp.length();i++){
            dfs(s+tmp.charAt(i),digits.substring(1,n),res,map);
        }
    }

}
