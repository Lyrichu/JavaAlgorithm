package pers.lyrichu.projects.Crawler.MyOwnCrawler.page;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.lyrichu.projects.Crawler.MyOwnCrawler.util.CharsetDetector;

import java.io.UnsupportedEncodingException;

/*
 * 保存响应的相关内容,提供对外访问方法
 */
public class Page {
    private static final Logger LOGGER = LoggerFactory.getLogger(Page.class);

    private byte[] content; // http response content
    private String html; // html source string
    private Document doc;
    private String charset; // 字符编码
    private String url;
    private String contentType; // 内容类型

    public Page(byte[] content,String url,String contentType) {
        this.content = content;
        this.url = url;
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public byte[] getContent() {
        return content;
    }

    public String getCharset() {
        return charset;
    }

    public String getContentType() {
        return contentType;
    }

    /*
     * 得到文档
     */
    public Document getDoc() {
        if (doc != null) {
            return doc;
        }
        try {
            this.doc = Jsoup.parse(getHtml(),url);
            return doc;
        } catch (Exception e) {
            LOGGER.error("getDoc error:{}",e);
        }
        return null;
    }

    /*
     * 返回网页的源码字符串
     */
    public String getHtml() {
        if (html != null) {
            return html;
        }
        if (content == null) {
            return null;
        }
        if (charset == null) {
            // 根据内容来猜测字符编码
            charset = CharsetDetector.guessEncoding(content);
        }
        try {
            this.html = new String(content,charset);
            return html;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("unsupportedEncoding error:{}",e);
        }
        return null;
    }
}