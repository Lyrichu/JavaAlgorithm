package pers.lyrichu.java.jni.func;

/**
 * 带有参数的函数 jni 调用实例
 * ref:https://www.baeldung.com/jni
 */
public class SimpleFuncWithParams {

  static {
    System.loadLibrary("native");
  }

  public static void main(String[] args) {
    SimpleFuncWithParams sf = new SimpleFuncWithParams();
    int sum = sf.integerSum(1,10);
    System.out.println("sum:" + sum);
    String s = sf.sayHelloToMe("lyrichu",false);
    System.out.println(s);
  }

  private native int integerSum(int first,int second);

  private native String sayHelloToMe(String name,boolean isFemale);
}
