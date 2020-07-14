package pers.lyrichu.java.jni.obj;

/**
 * 测试类,用于演示在jni中对于对象的调用
 */
public class UserData {

  private String name;
  private int age;

  String getUserData() {
    return "name:" + name + ",age:" + age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
