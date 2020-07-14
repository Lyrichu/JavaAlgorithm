package pers.lyrichu.java.http.okhttp;
import java.io.IOException;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Test;

/**
 * okhttp client test demo
 */
public class OKHttpClientDemo {

  @Test
  public void testGet() throws Exception {

    String url = "http://10.115.19.99/global/v5.5/global/hotquery";
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    // call:execute 是同步执行;enqueue 是异步执行
    final Call call = client.newCall(request);
    // 异步执行
    call.enqueue(new com.squareup.okhttp.Callback() {
      @Override
      public void onFailure(Request request, IOException e) {
        System.err.println(e.getMessage());
      }
      @Override
      public void onResponse(Response response) throws IOException {
        System.out.println("enqueue execute");
        System.out.println(response.body().string());
      }
    });
    System.out.println("after enqueue");
    // sleep 等待异步执行结束
    Thread.sleep(1000);
    // 同步执行
    // Response response = call.execute();
    // System.out.println("execute response:" + response.body().string());
  }
}
