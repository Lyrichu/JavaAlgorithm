package pers.lyrichu.java.nio.channel;

import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * reference:http://tutorials.jenkov.com/java-nio/channels.html
 */
public class FileChannelDemo {

  public static void main(String[] args) throws Exception{
    String filePath = "test/test_data.txt";
    URL url = FileChannelDemo.class.getClassLoader().getResource(filePath);
    RandomAccessFile raf = new RandomAccessFile(url.getFile(),"rw");
    // get channel
    FileChannel inChannel = raf.getChannel();
    ByteBuffer bb = ByteBuffer.allocate(48);
    int byteSize = inChannel.read(bb);
    while (byteSize != -1) {
      System.out.println("read " + byteSize + " data");
      // set limit to read data
      bb.flip();
      // 遍历读取 ByteBuffer中的数据
      while (bb.hasRemaining()) {
        System.out.print((char)bb.get());
      }
      bb.clear();
      byteSize = inChannel.read(bb);
      System.out.println();
    }
    inChannel.close();
    raf.close();
  }
}
