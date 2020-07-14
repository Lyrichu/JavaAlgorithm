package pers.lyrichu.projects.Crawler.test;
import	java.util.ArrayList;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 悟空问答爬虫
 */
public class WukongWendaCrawler {

  public static void main(String[] args) {
    String query = "韩寒认养两头河马";
    String response = getResponse(query);
    List<String> resList = parseResponse(response);
    resList.forEach(System.out::println);
  }

  private static String getResponse(String query) {
    String url =
        "https://www.wukong.com/wenda/wapshare/search/brow/?search_text=" + query + "&offset=0&count=10";
    String response = "";
    try {
      CloseableHttpClient httpClient = HttpClientBuilder.create().build();
      HttpGet httpGet = new HttpGet(url);
      CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
      response = EntityUtils.toString(httpResponse.getEntity());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return response;
  }

  private static List<String> parseResponse(String response) {
    List<String> resList = new ArrayList<>();
    try {
      JSONObject json = JSON.parseObject(response);
      JSONObject dataJson = json.getJSONObject("data");
      JSONArray feedQArr = dataJson.getJSONArray("feed_question");
      for (int i = 0;i < feedQArr.size();i++) {
        JSONObject qJson = feedQArr.getJSONObject(i);
        JSONObject questionJson = qJson.getJSONObject("question");
        String title = questionJson.getString("title");
        int followCount = questionJson.getIntValue("follow_count");
        int ansCount = questionJson.getIntValue("nice_ans_count");
        String createTimeStamp = questionJson.getString("create_time");
        String resData = String.format("%d\t%s\t%d\t%d\t%s",
            i+1,title,ansCount,followCount,createTimeStamp);
        resList.add(resData);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return resList;
  }

}
