package pers.lyrichu.java.netty.basic;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * hello netty client demo
 */
public class HelloNettyClient {

  private final int PORT = 9090;
  private final String ADDRESS = "127.0.0.1";

  public static void main(String[] args) {
    HelloNettyClient client = new HelloNettyClient();
    client.runClient();
  }

  private void runClient() {
    ClientBootstrap clientBootstrap = new ClientBootstrap();
    // boss/worker 线程池
    ExecutorService bossES = Executors.newCachedThreadPool();
    ExecutorService workerES = Executors.newCachedThreadPool();

    clientBootstrap.setFactory(new NioClientSocketChannelFactory(bossES,workerES));
    clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("clientEncoder",new StringEncoder());
        pipeline.addLast("clientDecoder",new StringDecoder());
        // add handler]
        pipeline.addLast("clientHandler",new HelloNettyClientHandler());
        return pipeline;
      }
    });
    // 建立连接
    ChannelFuture channelFuture = clientBootstrap.connect(new InetSocketAddress(ADDRESS,PORT));
    // get channel
    Channel channel = channelFuture.getChannel();
    System.out.println("start client...");
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      String message = scanner.next();
      // send message
      channel.write(message);
    }
  }

}
