package pers.lyrichu.java.util.scripts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class NIOFastFileCopy {
    // 使用NIO进行文件的快速拷贝
    private static String inputPath = "src/main/resources/stopwords.txt";
    private static String outputPath = "src/main/resources/stopwords_copy.txt";

    public static void main(String[] args) {
        try{
            File in = new File(inputPath);
            File out = new File(outputPath);
            fileCopy(in,out);
            System.out.printf("copy from %s to %s successfully!",inputPath,outputPath);
        } catch (IOException e){
            System.err.println("error:"+e);
        }

    }

    private static void fileCopy(File in,File out) throws IOException {
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        // magic number for windows(64Mb-32kb)
        int maxCount = (64*1024*1024) -32*1024;
        long size = inChannel.size();
        long position = 0;
        while(position < size){
            position += inChannel.transferTo(position,maxCount,outChannel);
        }
    }
}
