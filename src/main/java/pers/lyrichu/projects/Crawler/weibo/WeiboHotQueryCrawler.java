package pers.lyrichu.projects.Crawler.weibo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pers.lyrichu.projects.Crawler.util.WeiboHotQuery;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// 抓取微博热搜榜
public class WeiboHotQueryCrawler {
    private static final String WEIBO_BASE_URL = "https://s.weibo.com";
    private static final String WEIBO_REALTIME_HOTQUERY_URL = "http://s.weibo.com/top/summary?cate=realtimehot";
    private static final int TIMEOUT = 1000; // 超时1000ms

    private static List<WeiboHotQuery> crawlerHotquries() {
        List<WeiboHotQuery> hotQueryList = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new URL(WEIBO_REALTIME_HOTQUERY_URL),TIMEOUT);
            Elements quries = document.getElementsByClass("td-02"); // 热词
            Elements tags = document.getElementsByClass("td-03"); // 标签
            int queriesCount = quries.size()-1;
            // 把首条置顶的热词去除
            for (int i = 1;i<=queriesCount;i++) {
                Element queryElement = quries.get(i);
                String text = queryElement.getElementsByTag("a").text();
                int viewCount = Integer.parseInt(queryElement.getElementsByTag("span").text().trim());
                // query url link
                String queryUrl = WEIBO_BASE_URL + queryElement.getElementsByTag("a").attr("href");
                String tag = tags.get(i).text();
                WeiboHotQuery hotQuery = new WeiboHotQuery(text,queryUrl,tag,viewCount,i);
                hotQueryList.add(hotQuery);
            }
        } catch (Exception e) {
            System.out.println("error:"+e);
        }
        return hotQueryList;
    }

    public static void main(String[] args) {
        List<WeiboHotQuery> hotQueryList = crawlerHotquries();
        System.out.println("text\turl\ttag\tviewCount\trank");
        for (WeiboHotQuery hotQuery:hotQueryList) {
            System.out.println(
                    String.format(
                            "%s\t%s\t%s\t%d\t%d",
                            hotQuery.getText(),
                            hotQuery.getUrl(),
                            hotQuery.getTag(),
                            hotQuery.getViewCount(),
                            hotQuery.getRank()
                            )
            );
        }
    }
}


