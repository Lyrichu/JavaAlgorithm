package pers.lyrichu.projects.Crawler.MyOwnCrawler.page;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/*
 * 根据选择器来选取元素,属性等方法
 */
public class PageParserTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageParserTool.class);

    /*
     * 通过选择器来选取页面元素
     */
    public static Elements select(Page page,String cssSelector) {
        return page.getDoc().select(cssSelector);
    }

    /*
     * 通过css选择器来得到指定index的元素
     */
    public static Element select(Page page,String cssSelector,int index) {
        Elements elements = select(page,cssSelector);
        if (index < 0) {
            index = elements.size() + index;
        }
        // 自动做了rangeCheck
        return elements.get(index);
    }

    /*
     * 抽取page 的所有超链接
     */
    public static Set<String> getLinks(Page page,String cssSelector) {
        Set<String> links = new HashSet<>();
        Elements elements = select(page,cssSelector);
        Iterator iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            // 含有超链接
            if (element.hasAttr("href")) {
                links.add(element.attr("abs:href"));
            } else if (element.hasAttr("src")) {
                links.add(element.attr("abs:src"));
            }
        }
        return links;
    }

    /*
     * 获取 Page 中含有指定属性的List
     */
    public static List<String> getAttrs(Page page,String cssSelector,String attrName) {
        List<String> result = new ArrayList<>();
        Elements elements = select(page,cssSelector);
        for (Element element:elements) {
            if (element.hasAttr(attrName)) {
                result.add(element.attr(attrName));
            }
        }
        return result;
    }
}