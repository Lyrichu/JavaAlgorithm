package pers.lyrichu.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.lyrichu.projects.Crawler.tecent.news.TecentNewsCrawler;
import pers.lyrichu.projects.Crawler.toutiao.ToutiaoHotQueryCrawler;
import pers.lyrichu.projects.Crawler.util.TecentNews;
import pers.lyrichu.projects.Crawler.util.ToutiaoHotQuery;
import pers.lyrichu.projects.Crawler.util.WeiboHotQuery;
import pers.lyrichu.projects.Crawler.weibo.WeiboHotQueryCrawler;

import java.util.Date;
import java.util.List;

import static pers.lyrichu.java.util.scripts.SendEmail.postEmail;

/*
 * 在服务器上定时执行一些发送邮件的任务
 */
public class SendEmails {
    private static final String from = "lyrichu@foxmail.com";
    private static final String[] toList = {"919987476@qq.com","1721943597@qq.com"};
    private static final String fromPasswd = "bzphfvgjsgwvbbbj"; // 授权码替代原始密码
    private static final String host = "smtp.qq.com";

    private static final String weiboSubject = "微博热搜榜";
    private static final String toutiaoSubject = "头条热搜榜";
    private static final String tencentNewsSubject = "腾讯新闻榜";

    private static final int weiboRepeatEveryMinutes = 30;
    private static final int toutiaoRepeatEveryMinutes = 60;
    private static final int tecentNewsRepeatEveryMinutes = 120;

    private static long nm = 1000*60; // 用于计算相差分钟数

    private static Logger LOGGER = LoggerFactory.getLogger(SendEmails.class);

    public static void main(String[] args) throws Exception {
        Date firstExecuteTime = new Date();
        while (true) {
            sendWeiboHotQuery(weiboSubject,firstExecuteTime,weiboRepeatEveryMinutes);
            sendToutiaoHotQuery(toutiaoSubject,firstExecuteTime,toutiaoRepeatEveryMinutes);
            sendTecentNews(tencentNewsSubject,firstExecuteTime,tecentNewsRepeatEveryMinutes);
            Thread.sleep(nm);
        }
    }

    private static void sendWeiboHotQuery(String subject,Date firstExecuteTime,int repeatEveryMinutes) throws Exception {
        if (timeToExecute(firstExecuteTime,repeatEveryMinutes)) {
            List<WeiboHotQuery> weiboHotQueryList = WeiboHotQueryCrawler.crawlerHotQueries();
            // 发生了返回结果异常
            if (weiboHotQueryList.size() == 0) {
                String error = "抓取微博热词失败!请及时修复!";
                LOGGER.error(error);
                postEmail(from,toList,fromPasswd,host,subject,error);
                return;
            }
            StringBuilder message = new StringBuilder();
            for (int i = 0;i<weiboHotQueryList.size();i++) {
                WeiboHotQuery weiboHotQuery = weiboHotQueryList.get(i);
                String query = weiboHotQuery.getText();
                String url = weiboHotQuery.getUrl();
                String tag = weiboHotQuery.getTag();
                int rank = weiboHotQuery.getRank();
                int viewCount = weiboHotQuery.getViewCount();
                String html = String.format(
                        "<div>" +
                                // rank,color=blue
                                "<font color=\"blue\">%d</font>" +
                                // url and query
                                "\t<a target=\"_blank\",href=\"%s\">%s</a>" +
                                // tag
                                "\t%s" +
                                // viewCount,color=red
                                "\t<font color=\"red\">%d</font>" +
                                "</div>" +
                                // 换行符
                                "</br>",
                        rank,
                        url,
                        query,
                        tag,
                        viewCount
                );
                message.append(html);
            }
            postEmail(from,toList,fromPasswd,host,subject,message.toString());
            LOGGER.info("成功发送微博热搜榜!");
        }
    }

    private static void sendToutiaoHotQuery(String subject,Date firstExecuteTime,int repeatEveryMinutes) throws Exception {
        if (timeToExecute(firstExecuteTime,repeatEveryMinutes)) {
            List<ToutiaoHotQuery> toutiaoHotQueryList = ToutiaoHotQueryCrawler.crawlerHotQueries();
            // 发生了返回结果异常
            if (toutiaoHotQueryList.size() == 0) {
                String error = "抓取头条热词失败!请及时修复!";
                LOGGER.error(error);
                postEmail(from,toList,fromPasswd,host,subject,error);
                return;
            }
            StringBuilder message = new StringBuilder();
            for (int i = 0;i<toutiaoHotQueryList.size();i++) {
                ToutiaoHotQuery toutiaoHotQuery = toutiaoHotQueryList.get(i);
                String query = toutiaoHotQuery.getText();
                String url = toutiaoHotQuery.getUrl();
                String tag = toutiaoHotQuery.getTag();
                int rank = toutiaoHotQuery.getRank();
                String html = String.format(
                        "<div>" +
                                // rank,color=blue
                                "<font color=\"blue\">%d</font>" +
                                // url and query
                                "\t<a target=\"_blank\",href=\"%s\">%s</a>" +
                                // tag
                                "\t%s" +
                                "</div>" +
                                // 换行符
                                "</br>",
                        rank,
                        url,
                        query,
                        tag
                );
                message.append(html);
            }
            postEmail(from,toList,fromPasswd,host,subject,message.toString());
            LOGGER.info("成功发送头条热搜榜!");
        }
    }


    private static void sendTecentNews(String subject,Date firstExecuteTime,int repeatEveryMinutes) throws Exception {
        if (timeToExecute(firstExecuteTime,repeatEveryMinutes)) {
            List<TecentNews> tecentNewsList = TecentNewsCrawler.crawlerNews();
            // 发生了返回结果异常
            if (tecentNewsList.size() == 0) {
                String error = "抓取腾讯新闻失败!请及时修复!";
                LOGGER.error(error);
                postEmail(from,toList,fromPasswd,host,subject,error);
                return;
            }
            StringBuilder message = new StringBuilder();
            for (int i = 0;i<tecentNewsList.size();i++) {
                TecentNews tecentNews = tecentNewsList.get(i);
                String title = tecentNews.getTitle();
                String url = tecentNews.getUrl();
                String html = String.format(
                        "<div>" +
                                // rank,color=blue
                                "<font color=\"blue\">%d</font>" +
                                // url and query
                                "\t<a target=\"_blank\",href=\"%s\">%s</a>" +
                                "</div>" +
                                // 换行符
                                "</br>",
                        i+1,
                        url,
                        title
                );
                message.append(html);
            }
            postEmail(from,toList,fromPasswd,host,subject,message.toString());
            LOGGER.info("成功发送腾讯新闻!");
        }
    }



    /*
     * 判断是否到达定时执行任务的时间
     * @param firstExecuteTime:首次执行时间
     * @param repeatEveryMinutes:重复多少分钟执行一次
     */
    private static boolean timeToExecute(Date firstExecuteTime,int repeatEveryMinutes) {
        Date now = new Date();
        // 两个日期相差的毫秒数
        long msDelta = now.getTime() - firstExecuteTime.getTime();
        // 计算相差分钟数
        long minutesDelta = msDelta / nm;
        if (minutesDelta % repeatEveryMinutes == 0) {
            return true;
        }
        return false;
    }
}
