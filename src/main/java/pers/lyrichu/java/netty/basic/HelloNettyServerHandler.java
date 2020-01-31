package pers.lyrichu.java.netty.basic;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HelloNettyServerHandler extends SimpleChannelHandler {

  /**
   * 接受消息
   */
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    System.out.println("server messageReceived");
    // 打印消息
    System.out.println((String) e.getMessage());
    // 回写消息
    ctx.getChannel().write("copy that");
    super.messageReceived(ctx, e);
  }

  /**
   * 异常捕获
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    System.out.println("server exceptionCaught");
    super.exceptionCaught(ctx, e);
  }

  /**
   * 客户端连接
   */
  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("server channelConnected");
    super.channelConnected(ctx, e);
  }

  /**
   * 客户端断开连接
   */
  @Override
  public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("server channelDisconnected");
    super.channelDisconnected(ctx, e);
  }

  /**
   * 关闭客户端
   */
  @Override
  public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("server channelClosed");
    super.channelClosed(ctx, e);
  }
}
