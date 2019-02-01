package pers.lyrichu.demo;

public class DemoTest {

  public static void test() {
    A a = new A();
    String s1 = a.s;
    System.out.println(s1);
    s1 = "B";
    System.out.println(s1);
    System.out.println(a.s);
  }

  public static void main(String[] args) {
    DemoTest.test();
  }
}

class A {
  public String s = "A";
}
