package pers.lyrichu.test;

import java.net.URLEncoder;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * hq cube web 接口测试
 */
public class TestCubeWeb {

  @Test
  public void test1() throws Exception {
    String url = "http://docview.pt.xiaomi.srv/doc?app=hqnews&doc=user&docId=69ddafd388617d5ea320d975b05181cf";
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    System.out.println("http get response:");
    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
  }

  @Test
  public void test2() throws Exception {
    String baseUrl = "http://nlp-staging.ai.srv/internal/2.0/answer?";
    String appId = "2882303761517406018";
    String query = "习近平今天早间新闻";
    String deviceId = "robot_C814D000AE43FCD90AB1D8E0EC400F0B";
    String uid = "135";
    String url = buildAiRobotNewsRequestUrl(baseUrl,appId,deviceId,uid,query);
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    System.out.println("http get response:");
    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
  }



  /**
   * robot 音箱新闻接口
   */
  public static String buildAiRobotNewsRequestUrl(String baseUrl,String appId,String deviceId,String uid,String query) {
    String fullUrl = null;
    try {
      fullUrl = baseUrl
          + "app_id=" + appId
          + "&timestamp=" + System.currentTimeMillis()
          + "&queries=" + URLEncoder
          .encode(String.format("[{\"query\":\"%s\",\"confidence\":1.0}]",query),"utf-8")
          + "&device_id=" + deviceId
          + "&user_info=" + URLEncoder.encode(String.format("{\"id\":\"%s\",\"id_type\":\"xiaomi_id\",\"service_id\":\"2882303761517406012\",\"auth_token\":\"5621740649012\"}",uid),"utf-8")
      ;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return fullUrl;
  }


}
