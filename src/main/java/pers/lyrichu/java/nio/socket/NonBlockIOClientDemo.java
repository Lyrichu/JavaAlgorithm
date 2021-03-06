package pers.lyrichu.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * 非阻塞IO 实例
 */
public class NonBlockIOClientDemo {

  private static final int PORT = 9090;
  private static final String ADDRESS = "localhost";

  public static void main(String[] args) throws Exception {
    testClient();
  }


  private static void testClient() throws IOException,InterruptedException {

    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(ADDRESS,PORT));
    // 设定为非阻塞模式
    socketChannel.configureBlocking(false);
    ByteBuffer bb = ByteBuffer.allocate(1024);
    // 循环发送数据
    Scanner scanner = new Scanner(System.in);
    String s;
    while (scanner.hasNext()) {
      s = scanner.next();
      byte[] inBytes = (new Date() + ":" + s).getBytes();
      bb.put(inBytes);
      bb.flip();
      socketChannel.write(bb);
      bb.clear();
    }
    socketChannel.close();
  }

}
