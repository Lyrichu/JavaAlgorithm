package pers.lyrichu.run.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.lyrichu.java.basic.io.FileUtils;
import pers.lyrichu.java.util.scripts.SplitPDF;
import pers.lyrichu.projects.Crawler.tecent.news.TecentNewsCrawler;
import pers.lyrichu.projects.Crawler.toutiao.ToutiaoHotQueryCrawler;
import pers.lyrichu.projects.Crawler.util.TecentNews;
import pers.lyrichu.projects.Crawler.util.ToutiaoHotQuery;
import pers.lyrichu.projects.Crawler.util.WeiboHotQuery;
import pers.lyrichu.projects.Crawler.weibo.WeiboHotQueryCrawler;

import java.io.*;
import java.util.*;

import static pers.lyrichu.java.basic.io.FileUtils.listDirFiles;
import static pers.lyrichu.java.util.scripts.SendEmail.postEmail;

/*
 * 在服务器上定时执行一些发送邮件的任务
 */
public class SendEmails {
    private static final String[] toList = {"919987476@qq.com"};

    // 当前正在阅读的book的起始页码
    private static int currentReadingPage = 0;
    // 已经阅读的总页数
    private static int alreadyReadedPages = 0;
    // 已经阅读的总天数
    private static int alreadyReadedDays = 0;
    // 已经阅读的总次数
    private static int alreadyReadedTimes = 0;
    // 已经阅读的书籍列表
    private static List<String> alreadyReadedBooks = new ArrayList<>();
    // 首次阅读的日期
    private static final Date firstReadingDate = new Date();

    private static Logger LOGGER = LoggerFactory.getLogger(SendEmails.class);

    public static void main(String[] args) {
        Date firstExecuteTime = new Date();
        while (true) {
            try {
                sendWeiboHotQuery(weiboSubject,firstExecuteTime,weiboRepeatEveryMinutes);
                sendToutiaoHotQuery(toutiaoSubject,firstExecuteTime,toutiaoRepeatEveryMinutes);
                sendTecentNews(tencentNewsSubject,firstExecuteTime,tecentNewsRepeatEveryMinutes);
                sendPieceOfBooks(ebooksSubject,firstExecuteTime,ebooksRepeatEveryMinutes);
                Thread.sleep(nm);
            } catch (Exception e) {
                LOGGER.error("Send emails error:{}",e);
                // 发生异常时退出循环
                break;
            }
        }
    }
}
