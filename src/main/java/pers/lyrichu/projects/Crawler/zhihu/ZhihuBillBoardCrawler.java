package pers.lyrichu.projects.Crawler.zhihu;
import java.util.ArrayList;
import	java.util.regex.Matcher;

import java.util.List;
import java.util.regex.Pattern;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 知乎热搜榜抓取
 */
public class ZhihuBillBoardCrawler {
  private static final Pattern pattern = Pattern.compile(
      "<div class=\"HotList-itemTitle\">(.*?)</div>"
  );

  public static void main(String[] args) throws Exception {

    String url = "https://www.zhihu.com/billboard";
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    // call:execute 是同步执行;enqueue 是异步执行
    final Call call = client.newCall(request);
    // 同步执行
    Response response = call.execute();
    String body = response.body().string();
    List<String> titles = extractZhihuTitles(body);
    for (String title : titles) {
      System.out.println(title);
    }
  }

  private static List<String> extractZhihuTitles(String body) {
    List<String> results = new ArrayList<>();
    Matcher matcher = pattern.matcher(body);
    while (matcher.find()) {
      String title = matcher.group(1);
      results.add(title.trim());
    }
    System.out.println("match size: " + results.size());
    return results;
  }
}
