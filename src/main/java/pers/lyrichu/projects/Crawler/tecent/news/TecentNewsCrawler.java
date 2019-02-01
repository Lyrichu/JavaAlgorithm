package pers.lyrichu.projects.Crawler.tecent.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pers.lyrichu.projects.Crawler.util.TecentNews;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TecentNewsCrawler {
    private static final String TECENT_NEWS_BASE_URL = "http://news.qq.com/";
    private static final int TIMEOUT = 1000; // 1000 ms timeout

    public static void main(String[] args) {
        List<TecentNews> newsList = crawlerNews();
        System.out.println("id\ttitle\turl\timgurl\tsource");
        for (int i = 0;i<newsList.size();i++) {
            TecentNews tecentNews = newsList.get(i);
            System.out.printf(
                    "%d\t%s\t%s\t%s\t%s\n",
                    i+1,
                    tecentNews.getTitle(),
                    tecentNews.getUrl(),
                    tecentNews.getImgurl(),
                    tecentNews.getSource()
            );
        }
    }

    public static List<TecentNews> crawlerNews() {
        List<TecentNews> newsList = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new URL(TECENT_NEWS_BASE_URL),TIMEOUT);
            Elements news = document.getElementsByClass("linkto");
            // news pictures
            Elements pics = document.getElementsByClass("picto");
            int size = Math.min(news.size(),pics.size());
            for (int i = 0;i<size;i++) {
                Element eleNews = news.get(i);
                Element elePics = pics.get(i);
                String title = eleNews.text();
                String url = eleNews.attr("href");
                String imgurl = elePics.attr("src");
                if (imgurl.length() > 2 && !imgurl.startsWith("https:")) {
                    imgurl = "https:" + imgurl;
                }
                TecentNews tecentNews = new TecentNews(title,url,imgurl,"tecent_news");
                newsList.add(tecentNews);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
