package pers.lyrichu.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoTest {

  public static void test() {
    A a = new A();
    String s1 = a.s;
    System.out.println(s1);
    s1 = "B";
    System.out.println(s1);
    System.out.println(a.s);
    List<String> list = new ArrayList<>(
            Arrays.asList("a","b","c")
    );
    removeListFirstElement(list);
    System.out.println(list.size());
  }

  public static void main(String[] args) {
    DemoTest.test();
  }

  private static void removeListFirstElement(List<String> list) {
    if (list == null || list.size() == 0) {
      return;
    }
    list.remove(0);
  }

}

class A {
  public String s = "A";
}
