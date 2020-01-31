package pers.lyrichu.java.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 使用 AsynchronousFileChannel 拷贝大文件示例
 * reference: https://stackoverflow.com/questions/19532020/how-can-asynchronousfilechannel-read-large-file
 */
public class AsynchronousCopyLargeFileDemo implements CompletionHandler<Integer,AsynchronousFileChannel> {

  private final String inputFilePath = "/Users/huchengchun/Downloads/金庸/神雕侠侣.txt";
  private final String outputFilePath = "/Users/huchengchun/Downloads/金庸/神雕侠侣.copy.txt";
  private int pos = 0;
  private ByteBuffer bb;
  private AsynchronousFileChannel inputChannel;
  private FileChannel outputChannel;

  @Override
  public void completed(Integer result, AsynchronousFileChannel attachment) {
    if (result == -1) {
      System.out.println("all done!");
      return;
    }
    pos += result;
    System.out.printf("now read %d data.\n",result);
    bb.flip();
    try {
      outputChannel.write(bb);
      bb.clear();
      attachment.read(bb,pos,inputChannel,this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void failed(Throwable exc, AsynchronousFileChannel attachment) {
    System.err.println("read data failed!");
    exc.printStackTrace();
  }

  private void run() {
    try {
      inputChannel = AsynchronousFileChannel.open(Paths.get(inputFilePath),StandardOpenOption.READ);
      outputChannel = FileChannel.open(Paths.get(outputFilePath),
          StandardOpenOption.WRITE,StandardOpenOption.CREATE);
      bb = ByteBuffer.allocate(1024);
      inputChannel.read(bb,pos,inputChannel,this);
    } catch (IOException e) {
      e.printStackTrace();
    }
    // 由于上面的操作是异步的，所以必须在操作结束之前不能让程序结束
    try {
      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        inputChannel.close();
        outputChannel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    AsynchronousCopyLargeFileDemo demo = new AsynchronousCopyLargeFileDemo();
    demo.run();
  }
}
