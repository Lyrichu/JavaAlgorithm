package pers.lyrichu.java.nio.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.google.common.base.Strings;

/**
 * nio 分散读取与聚集写入
 */
public class ScatterReadAndGatherWriteDemo {

  public static void main(String[] args) throws IOException {
    String resourceFilePath = ScatterReadAndGatherWriteDemo.class.getClassLoader().getResource("test").getPath();
    String inFilePath = resourceFilePath + "/stopwords.txt";
    String outFilePath = resourceFilePath + "/stopwords_copy.txt";

    FileChannel inChannel = FileChannel.open(Paths.get(inFilePath), StandardOpenOption.READ);
    FileChannel outChannel = FileChannel.open(Paths.get(outFilePath),
        StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);

    // 创建两个缓冲区
    ByteBuffer[] bbs = new ByteBuffer[] {ByteBuffer.allocate(24),ByteBuffer.allocate(512)};
    // 分散读取
    inChannel.read(bbs);

    // 显示读取的结果
    for (ByteBuffer bb : bbs) {
      bb.flip();
      System.out.print(new String(bb.array(),0,bb.limit()));
      System.out.println();
      System.out.println(Strings.repeat("-",30));
    }
    // 聚集写入
    outChannel.write(bbs);

    inChannel.close();
    outChannel.close();
  }
}
