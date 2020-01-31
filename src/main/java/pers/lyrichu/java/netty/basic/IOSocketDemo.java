package pers.lyrichu.java.netty.basic;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 传统的 socket io
 */
public class IOSocketDemo {

  private final int PORT = 9090;

  public static void main(String[] args) {
    IOSocketDemo demo = new IOSocketDemo();
    // 可以使用telnet 等作为客户端进行与服务端的连接测试
    demo.server();
  }

  /**
   *  服务端启动
   */
  private void server() {
    // 使用线程池去服务客户端也是比较消耗性能的
    ExecutorService executorService = Executors.newCachedThreadPool();
    // 创建一个 ServerSocket，监听某个端口
    try {
      ServerSocket server = new ServerSocket(PORT);
      System.out.println("Server start!");
      // 监听客户端的连接
      while (true) {
        final Socket socket = server.accept(); // 阻塞
        //从线程池分配一个线程去处理
        System.out.println("Accept a connection!");
        executorService.execute(() -> handle(socket));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 处理来自客户端的连接
   */
  private void handle(Socket socket) {
    byte[] bytes = new byte[1024];

    try {
      InputStream inputStream = socket.getInputStream();
      int len = inputStream.read(bytes); // 阻塞
      if (len > 0) {
        // 打印接收到的数据
        System.out.println("Accept data:");
        System.out.println(new String(bytes,0,len));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close();
        System.out.println("close connection!");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
