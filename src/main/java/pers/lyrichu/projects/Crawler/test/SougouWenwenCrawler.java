package pers.lyrichu.projects.Crawler.test;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import	java.util.regex.Pattern;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 抓取搜狗问问
 */
public class SougouWenwenCrawler {

  private static final Pattern pattern1 = Pattern.compile(
      "<div class=\"home-news-tit\">(.*?)</div>"
  );
  private static final Pattern pattern2 = Pattern.compile(
      "<h2>(.*?)</h2>"
  );


  public static void main(String[] args) throws Exception {
    String url = "https://wenwen.sogou.com/";
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    final Call call = client.newCall(request);
    // 同步执行
    Response response = call.execute();
    String body = response.body().string();
    List<String> titles1 = extractWenenTitles(pattern1,body);
    List<String> titles2 = extractWenenTitles(pattern2,body);
    Set<String> titles = new HashSet<>();
    titles.addAll(titles1);
    titles.addAll(titles2);
    System.out.println("titles size:" + titles.size());
    for (String title : titles) {
      System.out.println(title);
    }
  }

  private static List<String> extractWenenTitles(Pattern pattern,String body) {
    List<String> results = new ArrayList<>();
    Matcher matcher = pattern.matcher(body);
    while (matcher.find()) {
      String title = matcher.group(1);
      results.add(title.trim());
    }
    return results;
  }

}
