package pers.lyrichu.test;
import	java.util.ArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import pers.lyrichu.tools.utils.FileUtils;

public class FastJsonTest {

  @Test
  public void test() {
    String filePath = "/Users/huchengchun/Downloads/qa_merge_all.txt";
    List<String> lines = FileUtils.readLines(filePath);
    for (String line : lines) {
      String[] splits = line.split(",",4);
      JSONObject json = JSON.parseObject(splits[3]);
      System.out.println(json);
    }
  }

  @Test
  public void test1() {
    String sJson = "{\"a\":[\"1\",\"m\"]}";
    JSONObject json = JSON.parseObject(sJson);
    String v = json.getString("a");
    String[] splits = v
        .replace("[","")
        .replace("]","")
        .split(",");
    List<String> values = JSON.parseArray(v,String.class);
    values.forEach(System.out::println);
    System.out.println(v);
    System.out.println(splits[0]);
  }

  @Test
  public void test2() {
    org.json.JSONObject json = new org.json.JSONObject();
    updateTest2(json);
    System.out.println(json);
  }

  private void updateTest2(org.json.JSONObject jsonObject) {
    org.json.JSONObject json = new org.json.JSONObject();
    json.put("a",1);
//    for (Object key : json.keySet()) {
//      jsonObject.put((String)key,json.get((String) key));
//    }
    jsonObject = json;
  }


  @Test
  public void test3() {
    List<String> l1 = new ArrayList<>();
    updateTest3(l1);
    System.out.println(l1.size());
  }

  private void updateTest3(List<String> list) {
    List<String> list1 = new ArrayList<>(Arrays.asList("a","b"));
    list = list1;
  }

  @Test
  public void test4() {
    List<Double> l1 = new ArrayList<>(Arrays.asList(1.1,2.2,3.3,0.4));
    Collections.sort(l1, new Comparator<Double>() {
      @Override
      public int compare(Double o1, Double o2) {
        return o2.compareTo(o1);
      }
    });
    l1.forEach(System.out::println);
  }


  @Test
  public void test5() {
    String js = "{'a':[1,2,3]}";
    org.json.JSONObject json = new org.json.JSONObject(js);
    org.json.JSONArray ja = json.optJSONArray("a");
    for (int i = 0;i < ja.length();i++) {
      System.out.println(ja.getInt(i));
    }
  }

  @Test
  public void test6() {
    List<Demo> demos = new ArrayList<>();
    for (int i = 0;i < 10;i++) {
      demos.add(new Demo(String.valueOf(i)));
    }
    List<Demo> demos1 = new ArrayList<>(demos);

    Iterator<Demo> it = demos.iterator();
    while (it.hasNext()) {
      Demo d = it.next();
      if (Integer.valueOf(d.id) % 2 == 0) {
        it.remove();
      }
    }
    System.out.println(demos.size());
    System.out.println(demos1.size());
    demos.stream().map(v -> v.index).forEach(System.out::println);
  }


  class Demo {

    public String id;
    public int index;

    public Demo(String id) {
      this.id = id;
    }
  }



  @Test
  public void test7() throws Exception {
    org.json.JSONObject json = new org.json.JSONObject();

    json.put("a","abc");
    json.put("b" ,1.2);

    JSONArray arr = new JSONArray();
    for (int i = 0;i < 3;i++) {
      arr.add(i);
    }
    json.put("arr",arr);

    System.out.println(json);

    org.json.JSONObject json1 = new org.json.JSONObject();

    for (String k : json.keySet()) {
      json1.put(k,json.get(k));
    }

    System.out.println(json1);
  }


}
