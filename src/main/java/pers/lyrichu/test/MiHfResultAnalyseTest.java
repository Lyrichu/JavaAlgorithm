package pers.lyrichu.test;
import	java.util.Arrays;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import	java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import pers.lyrichu.tools.utils.FileUtils;

public class MiHfResultAnalyseTest {
  private static final String HF_URL = "http://global.search.xiaomi.srv/homefeed/v1/search?q=";
  private static final String GS_URL = "http://global.search.xiaomi.net/global/v5.5/sug";
  private static final Set<String> QA_TYPE_SET = Sets.newHashSet("qa","jingyan");
  private static final String QA_CANDIDATES_PATH = "/Users/huchengchun/Downloads/qa_data/qa_goods_data/qa_online.v3.tsv";

  @Test
  public  void testGetQaCountByGsResult() {
    String query = "鼻塞怎么快速通气";
    JSONObject json = requestGsQuery(query);
    if (json != null) {
      int qaCount = getQaCountByGsResult(json);
      System.out.println("qa count:" + qaCount);
    }
  }

  @Test
  public void testGetQaCategoryByHfResult() {
    String query = "鼻塞怎么快速通气";
    JSONObject json = requestHfQuery(query);
    if (json != null) {
      String category = getQaCategoryByHfResult(json);
      System.out.println(query + " category:" + category);
    }
  }

  @Test
  public void testHfHasQaResult() {
    String query = "本科生如何写个人简历";
    JSONObject json = requestHfQuery(query);
    if (json != null) {
      boolean hasResult = hfHasQaResult(json);
      System.out.println("query=" + query + " has hf result:" + hasResult);
    }
  }

  @Test
  public void testAiRequest() throws Exception {

    String url = "http://nlp-preview.ai.srv/internal/2.0/answer?app_id=262522317551175680&token=&timestamp=1588047094084&queries=" + URLEncoder
        .encode("[{\"query\":\"小米新闻\",\"confidence\":0.8}]","utf-8") + "&device_id=robot_C814D000AE43FCD90AB1D8E0EC400F0B";
    String baseUrl = "http://nlp-preview.ai.srv/internal/2.0/answer?";
    String appId = "262522317551175680";
    String query = "播放热门新闻";
    List<String> queries = Arrays.asList(
        "新闻",
        "热门新闻",
        "播新闻",
        "今天的新闻",
        "今日新闻",
        "听新闻"
    );
    String deviceId = "robot_C814D000AE43FCD90AB1D8E0EC400F0B";
    String uid = "135";
    List<String> uids = Arrays.asList(
        "195711622",
        "1242158693",
        "6227995",
        "272901742",
        "7008758",
        "1310606402",
        "1516140268",
        "135",
        "1234",
        "914786926",
        "2328070808",
        "1185251745",
        "975122767",
        "826730952",
        "1275157137"
    );
    List<String> appIds = Arrays.asList(
        "527449393826104320", // x08c
        "2882303761517406012" // s12
    );
    int index = 0;
    for (String app_id : appIds) {
      for (String q : queries) {
        for (String id : uids) {
          String fullUrl = baseUrl
              + "app_id=" + app_id
              + "&timestamp=" + System.currentTimeMillis()
              + "&queries=" + URLEncoder.encode(String.format("[{\"query\":\"%s\",\"confidence\":1.0}]",q),"utf-8")
              + "&device_id=" + deviceId
              + "&user_info=" + URLEncoder.encode(String.format("{\"id\":\"%s\",\"id_type\":\"xiaomi_id\",\"service_id\":\"2882303761517406012\",\"auth_token\":\"5621740649012\"}",id),"utf-8");
          System.out.printf("index = %d,fullUlr = %s\n",index,fullUrl);
          CloseableHttpClient httpClient = HttpClientBuilder.create().build();
          HttpGet httpGet = new HttpGet(fullUrl);
          CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
          String result = EntityUtils.toString(httpResponse.getEntity());
          JSONObject resultJson = JSON.parseObject(result);
          System.out.printf("json index = %d,result = %s\n",index,resultJson.toJSONString());
          index++;
        }
      }
    }
    System.out.println("all done,total = " + index);
  }




  @Test
  public void testGenerateQaTags() {
    generateQaTags();
  }

  @Test
  public void testGenerateQaTagsForIncrMerge() {
    generateQaTagsForIncrMerge();
  }

  @Test
  public void testQueryHitRelevenceHfResult() {
    String[] posQuries = {
        "刘备的真实军事水平如何",
        "如何在生活中保持快乐",
        "蚊子会被撑死或者饿死吗",
        "特朗普是不是真的疯了",
        "那些复读的人后来都怎么样了",
        "你是怎么变自律的"
    };
    for (String query : posQuries) {
      JSONObject json = requestHfQuery(query);
      boolean hasReResult = isQueryHitRelevenceHfResult(query,json);
      System.out.printf("query = %s has hf re result:%s\n",query,hasReResult);
      System.out.println("-----------------------");
    }
  }




  private static JSONObject requestHfQuery(String query) {
    try {
      String url = HF_URL + query + "&imei=test&fev=20200202";
      CloseableHttpClient httpClient = HttpClientBuilder.create().build();
      HttpGet httpGet = new HttpGet(url);
      CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
      String result = EntityUtils.toString(httpResponse.getEntity());
      JSONObject resultJson = JSON.parseObject(result);
      return resultJson;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static JSONObject requestGsQuery(String query) {
    try {
      CloseableHttpClient httpClient = HttpClientBuilder.create().build();
      JSONObject param = new JSONObject();
      param.put("q",query);
      param.put("hl","zh-CN");
      param.put("imei","test");
      HttpPost httpPost = new HttpPost(GS_URL);
      httpPost.addHeader("Content-type","application/json;charset=UTF-8");
      StringEntity entity = new StringEntity(param.toJSONString(),"UTF-8");
      entity.setContentEncoding("UTF-8");
      httpPost.setEntity(entity);
      CloseableHttpResponse response = httpClient.execute(httpPost);
      String result = EntityUtils.toString(response.getEntity());
      JSONObject resultJson = JSON.parseObject(result);
      return resultJson;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static int getQaCountByGsResult(JSONObject resultJson) {
    int qaCount = 0;
    try {
      JSONArray resultArray = resultJson.getJSONArray("result");
      for (int i = 0;i < resultArray.size();i++) {
        JSONObject dataJson = resultArray.getJSONObject(i);
        if (dataJson.containsKey("data")) {
          JSONArray dataArray = dataJson.getJSONArray("data");
          for (int j = 0;j < dataArray.size();j++) {
            JSONObject json = dataArray.getJSONObject(j);
            String type = json.getString("type");
            if (QA_TYPE_SET.contains(type)) {
              qaCount++;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return qaCount;
  }

  /**
   * query 在 hf 中是否有高度相关的结果
   */
  private static boolean isQueryHitRelevenceHfResult(String query,JSONObject resultJson) {
    if (resultJson == null) {
      return false;
    }
    JSONArray resultArray = resultJson.getJSONArray("result");
    int topN = 4;
    int minReCount = 2;
    int reCount = 0;
    for (int i = 0;i < Math.min(resultArray.size(),topN);i++) {
      JSONObject dataJson = resultArray.getJSONObject(i);
      if (dataJson.containsKey("data")) {
        JSONArray dataArray = dataJson.getJSONArray("data");
        if (dataArray.size() > 0) {
          JSONObject firstDataJson = dataArray.getJSONObject(0);
          String title = firstDataJson.getString("title");
          if (title.contains(query)) {
            reCount++;
            System.out.printf("tilte = %s hit query = %s\n",title,query);
          }
        }
      }
    }
    return reCount >= minReCount;
  }


  private static String getQaCategoryByHfResult(JSONObject resultJson) {
    String category = "unknown";
    Map<String,Integer> categoryCountMap = new HashMap<>();
    try {
      JSONArray resultArray = resultJson.getJSONArray("result");
      for (int i = 0;i < resultArray.size();i++) {
        JSONObject dataJson = resultArray.getJSONObject(i);
        String type = dataJson.getString("type");
        if (dataJson.containsKey("data") && "news".equals(type)) {
          JSONArray dataArray = dataJson.getJSONArray("data");
          for (int j = 0;j < dataArray.size();j++) {
            JSONObject json = dataArray.getJSONObject(j);
            List<String> tags = json.getObject("tags", List.class);
            for (String tag : tags) {
              if (categoryCountMap.containsKey(tag)) {
                categoryCountMap.put(tag,categoryCountMap.get(tag) + 1);
              } else {
                categoryCountMap.put(tag,1);
              }
            }
          }
        }
      }
      // get maxcount category
      if (!categoryCountMap.isEmpty()) {
        int maxCount = 0;
        for (String key : categoryCountMap.keySet()) {
          int count = categoryCountMap.get(key);
          System.out.println(key + ":" + count);
          if (count > maxCount) {
            maxCount = count;
            category = key;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return category;
  }


  private static boolean hfHasQaResult(JSONObject resultJson) {
    try {
      JSONArray resultArray = resultJson.getJSONArray("result");
      for (int i = 0;i < resultArray.size();i++) {
        JSONObject dataJson = resultArray.getJSONObject(i);
        String type = dataJson.getString("type");
        if (dataJson.containsKey("data") && "news".equals(type)) {
          JSONArray dataArray = dataJson.getJSONArray("data");
          for (int j = 0;j < dataArray.size();j++) {
            JSONObject json = dataArray.getJSONObject(j);
            double gsRankScore = json.getDoubleValue("gs_rank_score");
            if (gsRankScore >= 0.7) {
              System.out.println("gs_rank_score:" + gsRankScore);
              return true;
            }
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }


  private static void generateQaTags() {
    String savePath = "/Users/huchengchun/Downloads/qa_online.v2.newcategory.tsv";
    List<String> lines = FileUtils.readLines("/Users/huchengchun/Downloads/qa_online.v2.tsv");
    List<String> newLines = new ArrayList<>(lines.size());
    List<String[]> qaSplits = new ArrayList<>(lines.size());
    for (String line : lines) {
      String[] splits = line.trim().split("\t");
      qaSplits.add(splits);
    }
    for (int i = 0;i < qaSplits.size();i++) {
      String[] splits = qaSplits.get(i);
      String query = splits[0];
      String oriCategory = splits[1];
      String category;
      if ("小米商品问答类".equals(oriCategory)) {
        category = "小米商品问答";
      } else {
        JSONObject hfJson = requestHfQuery(query);
        category = getQaCategoryByHfResult(hfJson);
      }
      // 替换 query category
      splits[1] = category;
      String newLine = String.join("\t",splits);
      newLines.add(newLine);
      System.out.printf("process %d line!",i+1);
    }
    FileUtils.writeLines(newLines,savePath);
  }


  private static void generateQaTagsForIncrMerge() {
    String savePath = "/Users/huchengchun/Downloads/date=20200321/qa_merge_all.new.txt";
    List<String> lines = FileUtils.readLines("/Users/huchengchun/Downloads/date=20200321/qa_merge_all.txt");
    List<String> newLines = new ArrayList<>(lines.size());
    List<String[]> qaSplits = new ArrayList<>(lines.size());
    for (String line : lines) {
      String[] splits = line.trim().split(",",4);
      qaSplits.add(splits);
    }
    for (int i = 0;i < qaSplits.size();i++) {
      String[] splits = qaSplits.get(i);
      JSONObject json = JSON.parseObject(splits[3]);
      String query = json.getString("query");
      JSONObject hfJson = requestHfQuery(query);
      String category = getQaCategoryByHfResult(hfJson);
      // 替换 query category
      splits[2] = category;
      String newLine = String.join(",",splits);
      newLines.add(newLine);
      System.out.printf("process %d line!",i+1);
    }
    FileUtils.writeLines(newLines,savePath);
  }

}
