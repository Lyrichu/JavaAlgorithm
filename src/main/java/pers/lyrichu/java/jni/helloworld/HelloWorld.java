package pers.lyrichu.java.jni.helloworld;

/**
 * java jni hello world demo
 */
public class HelloWorld {

  static {
    System.loadLibrary("native");
  }

  public static void main(String[] args) {

    new HelloWorld().sayHello();
  }

  private native void sayHello();
}
