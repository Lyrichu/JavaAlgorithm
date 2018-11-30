package pers.lyrichu.java.basic.util.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {
  public static void main(String[] args) {
    Map<Integer,String> map = new HashMap<>();
    map.put(1,"a");
    System.out.println(map.get(1));
    // 访问一个不存在的key,默认会返回null
    System.out.println(map.get(0));
  }
}
