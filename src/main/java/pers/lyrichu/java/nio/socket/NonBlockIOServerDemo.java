package pers.lyrichu.java.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NonBlockIOServerDemo {
  private static final int PORT = 9090;

  public static void main(String[] args) throws IOException {
    testServer();
  }

  private static void testServer() throws IOException {

    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    // 设定为非阻塞模式
    serverSocketChannel.configureBlocking(false);
    serverSocketChannel.bind(new InetSocketAddress(PORT));
    Selector selector = Selector.open();
    // 将 serverSocketChannel 注册到 selector 上,并设定监听模式 为 accept
    serverSocketChannel.register(selector,serverSocketChannel.validOps(),null);
    // 轮询获取选择器上已经准备就绪的事件
    while (true) {
      selector.select();
      Iterator<SelectionKey> it = selector.selectedKeys().iterator();
      while (it.hasNext()) {
        SelectionKey key = it.next();
        if (key.isAcceptable()) {
          // accept
          SocketChannel sc = serverSocketChannel.accept();
          // 切换到非阻塞模式
          sc.configureBlocking(false);
          // 监听 模式更新为 read
          sc.register(selector,SelectionKey.OP_READ);
        } else if (key.isReadable()) {
          SocketChannel channel = (SocketChannel) key.channel();
          ByteBuffer bb = ByteBuffer.allocate(1024);
          int len;
          while ((len = channel.read(bb)) != -1) {
            bb.flip();
            // 直接打印读取到的数据
            System.out.println(new String(bb.array(),0,len));
            bb.clear();
          }
        }
        it.remove();
      }
    }
  }

}
