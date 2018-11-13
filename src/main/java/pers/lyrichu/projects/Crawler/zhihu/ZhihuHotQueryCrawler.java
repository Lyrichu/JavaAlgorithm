package pers.lyrichu.projects.Crawler.zhihu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pers.lyrichu.projects.Crawler.util.ZhihuHotQuery;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ZhihuHotQueryCrawler {
    private static final String ZHIHU_BASE_URL = "https://www.zhihu.com";
    private static final String ZHIHU_HOTQUERY_URL = ZHIHU_BASE_URL + "/hot";
    private static final int TIMEOUT = 1000;

    private static List<ZhihuHotQuery> crawlerHotQueries() {
        try {
            URL url = new URL(ZHIHU_HOTQUERY_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader bfr = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = (bfr.readLine())) != null){
                sb.append(line);
            }
            String result = sb.toString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        crawlerHotQueries();
    }
}

