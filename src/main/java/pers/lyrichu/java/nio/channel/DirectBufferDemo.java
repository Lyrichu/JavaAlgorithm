package pers.lyrichu.java.nio.channel;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 直接缓冲区示例:直接使用缓冲区进行文件的复制
 */
public class DirectBufferDemo {

  public static void main(String[] args) throws IOException {
    ClassLoader classLoader = DirectBufferDemo.class.getClassLoader();
    String resourceFilePath = classLoader.getResource("test").getPath();
    String inFilePath = resourceFilePath + "/test_data.txt";
    String outFilePath = resourceFilePath + "/test_data_copy.txt";
    // 创建 channel
    FileChannel inChannel = FileChannel.open(Paths.get(inFilePath), StandardOpenOption.READ);
    FileChannel outChannel = FileChannel.open(Paths.get(outFilePath),
        StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
    // 内存映射文件
    MappedByteBuffer inMBB = inChannel.map(MapMode.READ_ONLY,0,inChannel.size());
    MappedByteBuffer outMBB = outChannel.map(MapMode.READ_WRITE,0,inChannel.size());
    // 直接对缓冲区进行数据的读写操作
    byte[] brr = new byte[inMBB.limit()];
    // 读数据
    inMBB.get(brr);
    // 写数据
    outMBB.put(brr);

    inChannel.close();
    outChannel.close();
  }
}
