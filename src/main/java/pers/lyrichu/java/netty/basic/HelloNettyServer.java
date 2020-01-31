package pers.lyrichu.java.netty.basic;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * netty hello world server demo
 */
public class HelloNettyServer {

  private final int PORT = 9090;
  private final String address = "localhost";

  public static void main(String[] args) {
    HelloNettyServer server = new HelloNettyServer();
    server.startServer();
  }

  private void startServer() {

    ServerBootstrap serverBootstrap = new ServerBootstrap();
    ExecutorService bossES = Executors.newCachedThreadPool();
    ExecutorService workerES = Executors.newCachedThreadPool();

    serverBootstrap.setFactory(new NioServerSocketChannelFactory(bossES,workerES));
    // set pipeline factory
    serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        // 用于解析字符串
        pipeline.addLast("HelloEncoder",new StringEncoder());
        pipeline.addLast("HelloDecoder",new StringDecoder());
        pipeline.addLast("HelloNettyServerHandler",new HelloNettyServerHandler());
        return pipeline;
      }
    });
    // 绑定端口
    serverBootstrap.bind(new InetSocketAddress(PORT));
    System.out.println("start server...");
  }
}
