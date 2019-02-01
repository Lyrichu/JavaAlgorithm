package pers.lyrichu.java.util.scripts;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppender {
    private static String filePath = "src/main/resources/test.txt";

    public static void main(String[] args) {
        // 向文件末尾添加内容
        BufferedWriter writer = null;
        try {
            // true means append
            writer = new BufferedWriter(new FileWriter(filePath,true));
            writer.write("\nDo what you like!");
            writer.flush();
            writer.close();
            System.out.println("append to " + filePath + " successfully!");
        } catch (IOException e) {
            System.err.println("io error:"+e);
        }
    }
}
