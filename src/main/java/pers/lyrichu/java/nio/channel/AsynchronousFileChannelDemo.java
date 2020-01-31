package pers.lyrichu.java.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * FileChannel 的异步版本,由 jdk1.7 引入
 */
public class AsynchronousFileChannelDemo {

  public static void main(String[] args) {
    AsynchronousFileChannelDemo demo = new AsynchronousFileChannelDemo();
    demo.writeDemo2();
  }

  /**
   * 轮询等待读取
   */
  private void readDemo1() {
    String file = AsynchronousFileChannelDemo.class.getClassLoader().getResource("test/test_data.txt").getPath();
    Path path = Paths.get(file);
    try {
      AsynchronousFileChannel afc = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
      ByteBuffer bb = ByteBuffer.allocate(1024);
      // read 操作会立刻异步返回,无论操作是否已经完成
      Future<Integer> operation = afc.read(bb,0);
      // while true 判断是否 read 完成
      while (!operation.isDone());
      // 如果已经操作完成
      bb.flip();
      byte[] barr = new byte[bb.limit()];
      bb.get(barr);
      System.out.println(new String(barr));
      bb.clear();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 覆写 completed 方法
   */
  private void readDemo2() {
    String file = AsynchronousFileChannelDemo.class.getClassLoader().getResource("test/test_data.txt").getPath();
    Path path = Paths.get(file);
    try {
      AsynchronousFileChannel afc = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
      ByteBuffer bb = ByteBuffer.allocate(1024);
      afc.read(bb, 0, bb, new CompletionHandler<Integer, ByteBuffer>() {
        /**
         * 成功结束时调用
         * @param result 成功读取的字节数
         * @param attachment 存储数据的 ByteBuffer
         */
        @Override
        public void completed(Integer result, ByteBuffer attachment) {
          System.out.printf("read %d data succeed!\n",result);
          attachment.flip();
          byte[] bytes = new byte[attachment.limit()];
          attachment.get(bytes);
          System.out.println("data:");
          System.out.println(new String(bytes));
          System.out.println("done!");
          attachment.clear();
        }

        /**
         * 失败时调用
         * @param exc
         * @param attachment
         */
        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
          System.err.println("read data error!");
        }
      });
      // 以上是异步调用，需等待读取结束
      Thread.sleep(1000);
      afc.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  private void writeDemo1() {
    String file = AsynchronousFileChannelDemo.class.getClassLoader().getResource("test").getPath() + "/test_write1.txt";
    Path path = Paths.get(file);

    try {
      AsynchronousFileChannel channel = AsynchronousFileChannel.open(path,
          StandardOpenOption.WRITE,StandardOpenOption.CREATE);
      ByteBuffer bb = ByteBuffer.allocate(1024);
      bb.put("2020,武汉加油!".getBytes());
      bb.flip();
      Future<Integer> operation = channel.write(bb,0);
      // 阻塞等待
      while (!operation.isDone());
      // 写入了多少数据
      System.out.printf("write %d data.",operation.get());
      bb.clear();
      channel.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private void writeDemo2() {
    String file = AsynchronousFileChannelDemo.class.getClassLoader().getResource("test").getPath() + "/test_write2.txt";
    Path path = Paths.get(file);

    try {
      AsynchronousFileChannel channel = AsynchronousFileChannel.open(path,
          StandardOpenOption.WRITE,StandardOpenOption.CREATE);
      ByteBuffer bb = ByteBuffer.allocate(1024);
      bb.put("2020,武汉加油!".getBytes());
      bb.flip();
      channel.write(bb, 0, bb, new CompletionHandler<Integer, ByteBuffer>() {
        @Override
        public void completed(Integer result, ByteBuffer attachment) {
          System.out.printf("write %d data.\n",result);
          System.out.println("write data:");
          System.out.println(new String(attachment.array(),0,result));
        }

        @Override
        public void failed(Throwable exc, ByteBuffer attachment) {
          System.err.println("write data failed!");
          exc.printStackTrace();
        }
      });
      Thread.sleep(1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
