package pers.lyrichu.test;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JSON;
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

  @Test
  public void test2() {
    EOBJ e = EOBJ.SOUND;
    System.out.println(e.toString());

    JSONObject json = new JSONObject();
    json.put("a",null);
    System.out.println(json.toJSONString());
  }

  @Test
  public void test3() {
    String[] arr = new String[] {"a","b"};
    List<String> list = new ArrayList<>(Arrays.asList(arr));
    System.out.println(JSON.toJson(arr));
    System.out.println(JSON.toJson(list));
    System.out.println(JSON.toJsonTree(list).getAsJsonArray());
    JsonObject json = new JsonObject();
    list.add(null);
    json.put("a",JSON.toJsonTree(list).getAsJsonArray());
    System.out.println(JSON.toJson(json));
  }

  @Test
  public void test4() {
    for (int i = 100;i <= 500;i++) {
      int hashcode = String.valueOf(i).hashCode() % 100;
      if (hashcode >= 80 && hashcode <= 90) {
        System.out.println(i);
      }
    }
  }

}
