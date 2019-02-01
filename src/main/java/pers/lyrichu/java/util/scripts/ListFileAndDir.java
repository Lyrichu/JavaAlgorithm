package pers.lyrichu.java.util.scripts;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class ListFileAndDir {
    private static String path = "/home/lyrichu";

    public static void main(String args[]) {
        String[] files = listFiles(path);
        System.out.printf("%s files(exclude . and ..):\n",path);
        for(String file:files) {
            System.out.println(file);
        }
        File[] dirs = listDirs(path);
        System.out.printf("\n%s dirs:\n",path);
        for(File dir:dirs) {
            System.out.println(dir.toString());
        }
    }

    // 列出path下所有的文件,除去"." 以及".." 以及 "m" 开头的文件
    private static String[] listFiles(String path) {
        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return !name.startsWith(".") && !name.startsWith("m");
            }
        };
        return new File(path).list(filter);
    }

    // 列出所有的文件夹

    private static File[] listDirs(String path) {
        FileFilter filter = new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        };
        return new File(path).listFiles(filter);
    }

}
