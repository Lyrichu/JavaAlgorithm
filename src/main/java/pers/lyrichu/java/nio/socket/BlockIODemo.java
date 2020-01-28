package pers.lyrichu.java.nio.socket;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.Test;

/**
 * 一个简单的阻塞式IO 实例
 */
public class BlockIODemo {

  /**
   * 客户端
   */
  @Test
  public void testClient() throws IOException {
    String inFilePath = "/Users/huchengchun/Downloads/吉他谱/生日快乐_指弹_吉他.png";
    String address = "127.0.0.1";
    int port = 8282;
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(address,port));
    FileChannel fileChannel = FileChannel.open(Paths.get(inFilePath), StandardOpenOption.READ);
    ByteBuffer bb = ByteBuffer.allocate(1024);
    while ((fileChannel.read(bb)) != -1) {
      bb.flip();
      socketChannel.write(bb);
      bb.clear();
    }
    // close the channel
    socketChannel.close();
    fileChannel.close();
  }

  /**
   * 服务端
   */
  @Test
  public void testServer() throws IOException{
    String outFilePath = "/Users/huchengchun/Downloads/吉他谱/生日快乐_指弹_吉他.copy.png";
    int port = 8282;
    ServerSocketChannel serverChannel = ServerSocketChannel.open();
    //创建一个FileChannel 用于保存到本地文件
    FileChannel fileChannel = FileChannel.open(Paths.get(outFilePath),
        StandardOpenOption.WRITE,StandardOpenOption.CREATE);
    // 绑定端口
    serverChannel.bind(new InetSocketAddress(port));
    SocketChannel socketChannel = serverChannel.accept();
    // ByteBuffer
    ByteBuffer bb = ByteBuffer.allocate(1024);
    // 开始接收数据
    while ((socketChannel.read(bb)) != -1) {
      bb.flip();
      fileChannel.write(bb);
      bb.clear();
    }
    serverChannel.close();
    socketChannel.close();
  }
}
