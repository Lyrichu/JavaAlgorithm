package pers.lyrichu.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TmpTest {

  @Test
  public void test1() {
    Map<String,Integer> map = new HashMap<>();
    map.put("a",1);
    map.put("b",0);

    for (String key : map.keySet()) {
      map.put(key,map.get(key) + 1);
      System.out.println(map.get(key));
    }
  }

}
