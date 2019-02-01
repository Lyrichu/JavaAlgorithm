package pers.lyrichu.projects.Crawler.MyOwnCrawler.link;

/*
 * 过滤接口
 */
public interface LinkFilter {
    public boolean accept(String url);
}
