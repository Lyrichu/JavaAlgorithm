package pers.lyrichu.projects.Crawler.MyOwnCrawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.page.Page;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * 下载已经访问过的文件
 */
public class FileTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTool.class);
    private static final String SAVE_DIR = "/tmp/my_own_crawler";

    /*
     * 根据url 获取 文件名
     */
    private static String getFileNameByUrl(String url,String contentType) {
        // 去除http://
        url = url.substring("http://".length());
        // text/html 类型
        if (contentType.contains("html")) {
            return url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
        } else {
            // application/pdf 等类型
            return url.replaceAll("[\\?/:*|<>\"]", "_") + "." +
                    contentType.substring(contentType.lastIndexOf("/") + 1);
        }
    }

    /*
     * 将网页保存到本地文件
     */
    public static void saveToLocal(Page page) {
        File saveDir = new File(SAVE_DIR);
        if (!saveDir.exists()) {
            if (!saveDir.mkdirs()) {
                LOGGER.error("mkdir {} failed!",SAVE_DIR);
                return;
            }
        }
        String saveFileName = getFileNameByUrl(page.getUrl(),page.getContentType());
        String saveFile = SAVE_DIR + "/" + saveFileName;
        byte[] content = page.getContent();
        try {
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(new File(saveFile)));
            for (int i = 0;i<content.length;i++) {
                outputStream.write(content[i]);
            }
            outputStream.flush();
            outputStream.close();
            LOGGER.info("Save {} succeed!",saveFile);
        } catch (IOException e) {
            LOGGER.error("saveToLocal error:{}",e);
        }

    }


}