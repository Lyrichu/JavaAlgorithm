package pers.lyrichu.java.http.httpclient;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * http client 使用 demo
 */
public class HttpClientDemo {

  @Test
  public void testGet() throws Exception {
    String url = "https://m.toutiao.com/search/suggest/initial_page/";
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
    System.out.println("http get response:");
    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
  }

  @Test
  public void testPost() throws Exception {
    // 一个用于测试的 post api
    String url = "https://jsonplaceholder.typicode.com/posts";
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    JSONObject param = new JSONObject();
    param.put("title","test_title");
    param.put("body","test_body");
    param.put("userId",1);
    HttpPost httpPost = new HttpPost(url);
    httpPost.addHeader("Content-type","application/json;charset=UTF-8");
    StringEntity entity = new StringEntity(param.toJSONString(),"UTF-8");
    entity.setContentEncoding("UTF-8");
    httpPost.setEntity(entity);

    System.out.println("execute http post request:" + httpPost.getRequestLine());
    // 创建一个 response handler,用于解析 http post 请求的结果
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
      @Override
      public String handleResponse(HttpResponse httpResponse)
          throws ClientProtocolException, IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode == 200) {
          HttpEntity entity = httpResponse.getEntity();
          return entity != null ? EntityUtils.toString(entity) : null;
        } else {
          throw new ClientProtocolException("Unexpected response statusCode:" + statusCode);
        }
      }
    };

    String result = httpClient.execute(httpPost,responseHandler);
    System.out.println("http post result:" + result);
  }

}
