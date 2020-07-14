package pers.lyrichu.java.jni.obj;

/**
 * jni 读取 UserData类
 */
public class GetUserData {

  static {
    System.loadLibrary("natives");
  }

  public static void main(String[] args) {
    GetUserData data = new GetUserData();
    UserData ud = data.getUser("jack",10);
    data.printUserData(ud);
  }

  private native UserData getUser(String name,int age);

  private native String printUserData(UserData user);

}
