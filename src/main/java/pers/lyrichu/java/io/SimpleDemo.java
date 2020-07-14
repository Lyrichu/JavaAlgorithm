package pers.lyrichu.java.io;
import	java.io.FileOutputStream;
import	java.io.OutputStream;
import  java.io.FileInputStream;
import	java.io.InputStream;

import org.junit.Test;

/**
 * java io 使用实例
 */
public class SimpleDemo {

  private static final String SRC_PATH = "/Users/huchengchun/Downloads/test1.txt";
  private static final String DEST_PATH = "/Users/huchengchun/Downloads/test1.copy.txt";

  /**
   * 一次拷贝一个字节(效率较低)
   */
  @Test
  public void testFileInputOutputStreamByOneByte() throws Exception{
    InputStream in = new FileInputStream(SRC_PATH);
    OutputStream out = new FileOutputStream(DEST_PATH);
    // 读写数据
    int data = in.read();
    while (data != -1) {
      out.write(data);
      data = in.read();
    }
    // 关闭流
    in.close();
    out.close();
  }

  /**
   * 先拷贝到数组，再写入文件
   */
  @Test
  public void testFileInputOutputStreamByByteArray() throws Exception {
    InputStream in = new FileInputStream(SRC_PATH);
    OutputStream out = new FileOutputStream(DEST_PATH);
    byte[] data = new byte[1024];
    int len = in.read(data);
    while (len != -1) {
      out.write(data,0,len);
      len = in.read(data);
    }
    in.close();
    out.close();
  }


}
