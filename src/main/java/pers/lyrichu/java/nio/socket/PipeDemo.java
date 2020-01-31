package pers.lyrichu.java.nio.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

/**
 * nio pipe example
 */
public class PipeDemo {

  public static void main(String[] args) throws IOException {
    Pipe pipe = Pipe.open();
    // 发送数据:SinkChannel
    SinkChannel sinkChannel = pipe.sink();
    ByteBuffer bb = ByteBuffer.allocate(1024);
    bb.put("今天是个好日子!".getBytes());
    bb.flip();
    sinkChannel.write(bb);
    bb.clear();
    // 接受数据:SourceChannel
    SourceChannel sourceChannel = pipe.source();
    ByteBuffer bb1 = ByteBuffer.allocate(1024);
    int len;
    while ((len = sourceChannel.read(bb1)) != -1) {
      bb1.flip();
      System.out.println(new String(bb1.array(),0,len));
      bb1.clear();
    }
    // close the channel
    sinkChannel.close();
    sourceChannel.close();
  }
}
