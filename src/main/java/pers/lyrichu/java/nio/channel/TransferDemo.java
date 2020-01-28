package pers.lyrichu.java.nio.channel;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TransferDemo {

  public static void main(String[] args) throws IOException {

    ClassLoader classLoader = TransferDemo.class.getClassLoader();
    String resourceFilePath = classLoader.getResource("test").getPath();
    String inFilePath = resourceFilePath + "/test_data.txt";
    String outFilePath = resourceFilePath + "/test_data_copy.txt";

    // 创建 channel
    FileChannel inChannel = FileChannel.open(Paths.get(inFilePath), StandardOpenOption.READ);
    FileChannel outChannel = FileChannel.open(Paths.get(outFilePath),
        StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE);
    // transfer
    //inChannel.transferTo(0,inChannel.size(),outChannel);
    outChannel.transferFrom(inChannel,0,inChannel.size());

    inChannel.close();
    outChannel.close();
  }
}
