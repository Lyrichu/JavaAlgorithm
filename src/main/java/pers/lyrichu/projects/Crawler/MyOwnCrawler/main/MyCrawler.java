package pers.lyrichu.projects.Crawler.MyOwnCrawler.main;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.link.LinkFilter;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.link.Links;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.page.Page;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.page.PageParserTool;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.page.RequestAndResponseTool;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.util.FileTool;

import java.util.Set;

/*
 * 爬虫入口
 */
public class MyCrawler {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyCrawler.class);

    private static final String BASE_URL = "http://www.baidu.com";
    private static final int MAX_CRAWLER_NUM = 1000;

    /*
     * 使用种子url初始化
     */
    private void initCrawlerWithSeedUrls(String[] seedUrls) {
        for (int i = 0;i < seedUrls.length;i++) {
            Links.addUnVisitedUrlQueue(seedUrls[i]);
        }
    }

    public void crawler(String[] seedUrls) {
        initCrawlerWithSeedUrls(seedUrls);
        // 自定义过滤器,只提取以BASE_URL 开头的站内链接
        LinkFilter filter = new LinkFilter() {
            @Override
            public boolean accept(String url) {
                if (url.startsWith(BASE_URL)) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        // 循环条件:待抓取链接非空,且抓取网页总数不超过最大限制
        while (!Links.unVisitedUrlQueueIsEmpty() && Links.getVisitedUrlNum() <= MAX_CRAWLER_NUM) {
            // 从待访问的队列中提取出第一个
            String url = Links.removeHeadOfUnVisitedUrlQueue();
            if (url == null) {
                continue;
            }
            // 根据url 得到 page
            Page page = RequestAndResponseTool.sendRequestAndGetResponse(url);
            // 访问dom的某个标签(比如<a>)
            Elements elements = PageParserTool.select(page,"a");
            // 打印
            for (Element element:elements) {
                LOGGER.info(element.text());
            }
            // save
            FileTool.saveToLocal(page);
            Links.addVisitedUrlSet(url);
            // 获取新的链接
            Set<String> links = PageParserTool.getLinks(page,"img");
            for (String link:links) {
                Links.addUnVisitedUrlQueue(link);
                LOGGER.info("Add new link:{}",link);
            }
        }
    }

    public static void main(String[] args) {
        String[] seedUrls = new String[] {BASE_URL};
        MyCrawler myCrawler = new MyCrawler();
        myCrawler.crawler(seedUrls);
    }

}