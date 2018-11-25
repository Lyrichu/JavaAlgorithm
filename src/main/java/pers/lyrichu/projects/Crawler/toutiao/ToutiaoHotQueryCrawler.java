package pers.lyrichu.projects.Crawler.toutiao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pers.lyrichu.projects.Crawler.util.ToutiaoHotQuery;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 抓取头条热搜榜
 */
public class ToutiaoHotQueryCrawler {
    private static final String TOUTIAO_BASE_URL = "https://m.toutiao.com";
    private static final String TOUTIAO_HOTQUERY_URL = "https://www.toutiao.com/2/wap/search/extra/hot_word_list/";
    private static final int TIMEOUT = 1000; // 超时1000ms
    public static void main(String[] args) {
        List<ToutiaoHotQuery> toutiaoHotQueryList = crawlerHotQueries();
        System.out.println("text\turl\ttag\trank");
        for (ToutiaoHotQuery hotQuery:toutiaoHotQueryList) {
            System.out.println(
                    String.format(
                            "%s\t%s\t%s\t%d",
                            hotQuery.getText(),
                            hotQuery.getUrl(),
                            hotQuery.getTag(),
                            hotQuery.getRank()
                    )
            );
        }
    }

    public static List<ToutiaoHotQuery> crawlerHotQueries() {
        List<ToutiaoHotQuery> hotQueryList = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new URL(TOUTIAO_HOTQUERY_URL),TIMEOUT);
            String html = document.html();
            // 使用正则表达式提取json
            String pattern = ".*JSON.parse\\((.+)\\).*";
            Pattern r = Pattern.compile(pattern);
            Matcher matcher = r.matcher(html);
            // 如果匹配
            if (matcher.find()) {
                String json = matcher.group(1);
                json = json.replace("\\","");
                json = json.substring(1,json.length()-1);
                JSONObject jsonObject = JSONObject.parseObject(json);
                JSONArray searchWords = jsonObject.getJSONArray("search_words");
                // 遍历每一个json of search words
                for (int i = 0;i<searchWords.size();i++) {
                    JSONObject words = searchWords.getJSONObject(i);
                    // query
                    String query = words.getString("q");
                    // query url
                    String url = TOUTIAO_BASE_URL + "/search/?keyword=" + query + "&from=gs_hotlist";
                    int type = words.getIntValue("t");
                    String tag = ""; // 默认标签
                    if (type == 2) {
                        tag = "hot";
                    }
                    if (type == 1) {
                        tag = "new";
                    }
                    ToutiaoHotQuery hotQuery = new ToutiaoHotQuery(query,url,tag,i+1);
                    hotQueryList.add(hotQuery);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotQueryList;
    }
}
