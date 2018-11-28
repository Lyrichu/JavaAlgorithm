package pers.lyrichu.java.basic.io;
/*
 *@ClassName FileUtils
 *@Description TODO
 *@Author lyrichu
 *@Date 18-11-27
 *@Version 1.0
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /*
     * 列举文件夹下的所有文件(非嵌套)
     */
    private static Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static void main(String[] args) {
        String oldName = "src/main/resources/test.txt";
        String newName = "src/main/resources/test1.txt";
        System.out.println(renameFile(oldName,newName));
    }

    public static List<String> listDirFiles(String path) {
        List<String> absFilesPath = new ArrayList<>();
        try {
            File dir = new File(path);
            String[] files = dir.list();
            for (int i = 0;i<files.length;i++) {
                absFilesPath.add(path + "/" + files[i]);
            }
        } catch (Exception e) {
            LOGGER.error("read {} error:{}",path,e);
        }
        return absFilesPath;
    }

    /*
     * 文件重命名
     */
    public static boolean renameFile(String oldName,String newName) {
        File oldFile = new File(oldName);
        if (oldName.equals(newName) || !oldFile.exists()) {
            return false;
        }
        File newFile = new File(newName);
        return oldFile.renameTo(newFile);
    }

    /*
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }
}
