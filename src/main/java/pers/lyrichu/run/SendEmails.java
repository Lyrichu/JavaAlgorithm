package pers.lyrichu.run;


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
    private static final String from = "lyrichu@foxmail.com";
    private static final String[] toList = {"919987476@qq.com"};
    private static final String fromPasswd = "bzphfvgjsgwvbbbj"; // 授权码替代原始密码
    private static final String host = "smtp.qq.com";

    private static final String weiboSubject = "微博热搜榜";
    private static final String toutiaoSubject = "头条热搜榜";
    private static final String tencentNewsSubject = "腾讯新闻榜";
    private static final String ebooksSubject = "每日读书";

    private static final int weiboRepeatEveryMinutes = 30;
    private static final int toutiaoRepeatEveryMinutes = 60;
    private static final int tecentNewsRepeatEveryMinutes = 120;
    private static final int ebooksRepeatEveryMinutes = 360;

    private static long nm = 1000*60; // 用于计算相差分钟数
    // books path
    private static final String ebooksPath = "/home/ebooks";
    // 当前正在阅读的book的起始页码
    private static int currentReadingPage = 0;
    // 每次阅读的page数量
    private static final int everyTimeReadingPage = 10;
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
    // 保存阅读信息的文件地址(json格式保存)
    private static final String readingInfoPath = "/home/ebooks/readingInfo.json";

    private static Logger LOGGER = LoggerFactory.getLogger(SendEmails.class);

    public static void main(String[] args) throws Exception {
        Date firstExecuteTime = new Date();
        while (true) {
            sendWeiboHotQuery(weiboSubject,firstExecuteTime,weiboRepeatEveryMinutes);
            sendToutiaoHotQuery(toutiaoSubject,firstExecuteTime,toutiaoRepeatEveryMinutes);
            sendTecentNews(tencentNewsSubject,firstExecuteTime,tecentNewsRepeatEveryMinutes);
            sendPieceOfBooks(ebooksSubject,firstExecuteTime,ebooksRepeatEveryMinutes);
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

    private static void sendPieceOfBooks(String subject,Date firstExecuteTime,int repeatEveryMinutes) throws Exception {
        if (timeToExecute(firstExecuteTime,repeatEveryMinutes)) {
            List<String> sortedUnreadedBooks = getSortedUnreadedBooks();
            if (sortedUnreadedBooks.size() == 0) {
                String errMsg = "读取服务器书籍数目为空,请及时检查修复!";
                LOGGER.error(errMsg);
                postEmail(from,toList,fromPasswd,host,subject,errMsg);
                return;
            }
            if (sortedUnreadedBooks.size() == 1) {
                String warnMsg = String.format("当前服务器书库只有%d本未读的书:%s!请及时添加新书!",sortedUnreadedBooks.size(),sortedUnreadedBooks.get(0));
                LOGGER.error(warnMsg);
                postEmail(from,toList,fromPasswd,host,subject,warnMsg);
            }
            String currentBook = getCurrentBook();
            File readingInfoFile = new File(readingInfoPath);
            if (readingInfoFile.exists()) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(readingInfoFile));
                    String line = reader.readLine();
                    JSONObject json = JSON.parseObject(line);
                    alreadyReadedPages = json.getIntValue("alreadyReadedPages");
                    alreadyReadedTimes = json.getIntValue("alreadyReadedTimes");
                    alreadyReadedDays = json.getIntValue("alreadyReadedDays");
                    currentReadingPage = json.getIntValue("currentReadingPage");
                    JSONArray array = json.getJSONArray("alreadyReadedBooks");
                    if (array == null || array.size() == 0) {
                        alreadyReadedBooks = new ArrayList<>();
                    } else {
                        alreadyReadedBooks = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            alreadyReadedBooks.add(array.getString(i));
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                    LOGGER.error("read info from {} failed!{}",readingInfoPath,e);
                }
            }
            int totalPages = SplitPDF.getPdfTotalPages(currentBook);
            // 如果当前已经读到本书的最后一页
            // 将当前的书改名为readed
            // 继续读取下一本书
            if (currentReadingPage >= totalPages) {
                alreadyReadedBooks.add(getBookNameByAbsPath(currentBook));
                // 设置本书已经阅读
                if (!setBookReaded(currentBook)) {
                    String errMsg = String.format("设置%s 为已阅读出错!请及时检查处理!",currentBook);
                    LOGGER.error(errMsg);
                    postEmail(from,toList,fromPasswd,host,subject,errMsg);
                    return;
                }
                currentBook = getCurrentBook();
                setCurrentReadingPage(0);
            }
            String currentBookName = currentBook.split("_")[0];
            int beginPage = currentReadingPage+1;
            int endPage = Math.min(SplitPDF.getPdfTotalPages(currentBook),currentReadingPage+everyTimeReadingPage);
            String splitPdfPath = String.format("%s_%d_%d.pdf",currentBookName,beginPage,endPage);
            SplitPDF.splitPDF(currentBook,splitPdfPath,beginPage,endPage);
            alreadyReadedDays = getDaysDelta(firstReadingDate,new Date()) + 1;
            alreadyReadedTimes++;
            alreadyReadedPages += endPage-beginPage+1;
            // 已经阅读的情况
            StringBuilder readedInfo = new StringBuilder("你尚未读完一本书,快来坚持阅读吧!");
            if (alreadyReadedBooks.size() > 0) {
                readedInfo = new StringBuilder(String.format("太棒了!你已经阅读了<font color=\"red\">%d</font>本书!<br>",alreadyReadedBooks.size()));
                readedInfo.append("以下是你已经读过的书:<br>");
                for (int i = 0;i<alreadyReadedBooks.size();i++) {
                    readedInfo.append(String.format("%d.%s<br>",i+1,alreadyReadedBooks.get(i)));
                }

            }
            String msg = String.format("<b>Congratulations!</b><br>" +
                          "这是你第<font color=\"red\">%d</font>天,第<font color=\"red\">%d</font>次阅读!<br>" +
                            // 已经阅读的书籍情况
                          "%s<br>" +
                          "你已经阅读了<font color=\"red\">%d</font>页书!太了不起了!<br>" +
                            "继续加油!祝你阅读愉快!",
                            alreadyReadedDays,
                            alreadyReadedTimes,
                            readedInfo.toString(),
                            alreadyReadedPages

            );
            String[] files = {splitPdfPath};
            postEmail(from,toList,fromPasswd,host,subject,msg,files);
            // 删除split pdf
            if (!FileUtils.deleteFile(splitPdfPath)) {
                String errInfo = String.format("删除%s失败!请及时检查修复!",splitPdfPath);
                LOGGER.error(errInfo);
                postEmail(from,toList,fromPasswd,host,subject,errInfo);
            }
            currentReadingPage = endPage;
            // 将已经阅读的信息保存到磁盘
            JSONObject saveJson = new JSONObject();
            JSONArray saveArr = new JSONArray();
            saveArr.addAll(alreadyReadedBooks);
            saveJson.put("alreadyReadedPages",alreadyReadedPages);
            saveJson.put("alreadyReadedTimes",alreadyReadedTimes);
            saveJson.put("alreadyReadedDays",alreadyReadedDays);
            saveJson.put("currentReadingPage",currentReadingPage);
            saveJson.put("alreadyReadedBooks",saveArr);
            BufferedWriter writer = new BufferedWriter(new FileWriter(readingInfoPath));
            writer.write(saveJson.toJSONString());
            writer.newLine();
            writer.close();
        }
    }

    private static int getDaysDelta(Date date1,Date date2) {
        long msDelta = date1.getTime() -date2.getTime();
        long div = 24*3600*1000;
        return Math.abs((int) (msDelta/div));
    }

    /*
     * 通过绝对路径得到book name
     */
    private static String getBookNameByAbsPath(String absPath) {
        String[] splits = absPath.split("/");
        return splits[splits.length-1].split("_")[0];
    }
    /*
     * 得到当前正在阅读的book的路径
     */
    private static String getCurrentBook() throws Exception{
       return getSortedUnreadedBooks().get(0);
    }

    private static List<String> getSortedUnreadedBooks() throws Exception{
        List<String> files = listDirFiles(ebooksPath);
        if (files.size() == 0) {
            LOGGER.error("read ebooks failed!");
            throw new Exception("read ebooks failed!");
        }
        // readedBooks like:Effective Java 中文第二版-1.readed.pdf
        // unreadedBooks like:Effective Java 中文第二版-1.unreaded.pdf
        List<String> unreadedBooks = new ArrayList<>();
        for (String file:files) {
            if (file.contains("unreaded")) {
                unreadedBooks.add(file);
            }
        }
        // 对于unreaded books,按照数字标号进行排序
        Collections.sort(unreadedBooks, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int n1 = Integer.parseInt(o1.split("\\.")[0].split("_")[1]);
                int n2 = Integer.parseInt(o2.split("\\.")[0].split("_")[1]);
                return Integer.compare(n1,n2);
            }
        });
        return unreadedBooks;
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

    private static int getCurrentReadingPage() {
        return currentReadingPage;
    }

    private static void setCurrentReadingPage(int currentReadingPage) {
        SendEmails.currentReadingPage = currentReadingPage;
    }

    private static boolean setBookReaded(String bookPath) {
        String readedName = bookPath.replace("unreaded","readed");
        return FileUtils.renameFile(bookPath,readedName);
    }
}
