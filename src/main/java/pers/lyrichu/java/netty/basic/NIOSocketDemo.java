package pers.lyrichu.java.netty.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * nio 服务 demo
 */
public class NIOSocketDemo {

  private final int PORT = 9000;

  public static void main(String[] args) {
    NIOSocketDemo demo = new NIOSocketDemo();
    demo.server();
  }

  private void server() {
    try {
      ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
      // 设定为非阻塞模式
      serverSocketChannel.configureBlocking(false);
      // 绑定端口
      serverSocketChannel.bind(new InetSocketAddress(PORT));
      Selector selector = Selector.open();
      // channel 注册到 selector
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      ByteBuffer bb = ByteBuffer.allocate(1024);
      while (true) {
        try {
          selector.select();
          Iterator<SelectionKey> it = selector.selectedKeys().iterator();
          while (it.hasNext()) {
            SelectionKey key = it.next();
            it.remove();
            SocketChannel channel;
            if (key.isAcceptable()) {
              System.out.println("Accept client connection.");
              channel = serverSocketChannel.accept();
              channel.configureBlocking(false);
              channel.register(selector,SelectionKey.OP_READ);
            } else if (key.isReadable()) {
              // 打印消息
              System.out.println("recieve client message:");
              channel = (SocketChannel) key.channel();
              int len = channel.read(bb);
              bb.flip();
              if (len > 0) {
                System.out.println(new String(bb.array(),0,len));
                bb.clear();
                bb.put("server recieve message.".getBytes());
                // 回写给客户端消息
                channel.write(bb);
              } else {
                System.out.println("client close.");
                bb.clear();
                key.channel();
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
