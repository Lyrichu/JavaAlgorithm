package pers.lyrichu.run.tools;

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
import pers.lyrichu.run.service.SendEmails;

import java.io.*;
import java.util.*;

import static pers.lyrichu.java.basic.io.FileUtils.listDirFiles;
import static pers.lyrichu.java.util.scripts.SendEmail.postEmail;

import static pers.lyrichu.run.tools.Constant.EMAIL_EXCUTE_MODE;
import static pers.lyrichu.run.tools.Constant.*;

public class Helper {
    private static Logger LOGGER = LoggerFactory.getLogger(Helper.class);

    /*
     * 邮件发送微博热搜榜
     */
    public static void sendWeiboHotQuery(String subject,String[] toList,EMAIL_EXCUTE_MODE mode,
                                         Date firstExecuteTime, int repeatEveryMinutes,Set<Calendar> fixTimeSet
    ) throws Exception {
        // 检查是否到达执行时间
        boolean readyToExcute = checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);

        if (readyToExcute) {
            List<WeiboHotQuery> weiboHotQueryList = WeiboHotQueryCrawler.crawlerHotQueries();
            // 发生了返回结果异常
            if (weiboHotQueryList.size() == 0) {
                String error = "抓取微博热词失败!请及时修复!";
                LOGGER.error(error);
                postEmail(EMAIL_FROM_ADDRESS,toList,
                        EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,error);
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
            postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,
                    QQ_EMAIL_HOST,subject,message.toString());
            LOGGER.info("成功发送微博热搜榜!");
        }
    }

    private static void sendToutiaoHotQuery(String subject,String[] toList,EMAIL_EXCUTE_MODE mode,
                                            Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet) throws Exception {
        boolean readyToExecute = checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);

        if (readyToExecute) {
            List<ToutiaoHotQuery> toutiaoHotQueryList = ToutiaoHotQueryCrawler.crawlerHotQueries();
            // 发生了返回结果异常
            if (toutiaoHotQueryList.size() == 0) {
                String error = "抓取头条热词失败!请及时修复!";
                LOGGER.error(error);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,
                        QQ_EMAIL_HOST,subject,error);
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
            postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,message.toString());
            LOGGER.info("成功发送头条热搜榜!");
        }
    }


    private static void sendTecentNews(String subject,String[] toList,EMAIL_EXCUTE_MODE mode,
                                       Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet
    ) throws Exception {
        boolean readyToExecute = checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
        if (readyToExecute) {
            List<TecentNews> tecentNewsList = TecentNewsCrawler.crawlerNews();
            // 发生了返回结果异常
            if (tecentNewsList.size() == 0) {
                String error = "抓取腾讯新闻失败!请及时修复!";
                LOGGER.error(error);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,error);
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
            postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,message.toString());
            LOGGER.info("成功发送腾讯新闻!");
        }
    }

    private static void sendPieceOfBooks(String subject,String[] toList,EMAIL_EXCUTE_MODE mode,
                                         Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet
    ) throws Exception {
        boolean readyToExecute = checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
        List<String> alreadyReadedBooks = new ArrayList<>();
        int alreadyReadedPages = 0;
        int alreadyReadedTimes = 0;
        int alreadyReadedDays = 0;
        int currentReadingPage = 0;

        if (readyToExecute) {
            List<String> sortedUnreadedBooks = getSortedUnreadedBooks();
            if (sortedUnreadedBooks.size() == 0) {
                String errMsg = "读取服务器书籍数目为空,请及时检查修复!";
                LOGGER.error(errMsg);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,errMsg);
                return;
            }
            if (sortedUnreadedBooks.size() == 1) {
                String warnMsg = String.format("当前服务器书库只有%d本未读的书:%s!请及时添加新书!",sortedUnreadedBooks.size(),sortedUnreadedBooks.get(0));
                LOGGER.error(warnMsg);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,warnMsg);
            }
            String currentBook = getCurrentBook();
            File readingInfoFile = new File(EBOOKS_READING_INFO_PATH);

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
                    LOGGER.error("read info from {} failed!{}",EBOOKS_READING_INFO_PATH,e);
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
                    postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,errMsg);
                    return;
                }
                currentBook = getCurrentBook();
                setCurrentReadingPage(0);
            }
            String currentBookName = currentBook.split("_")[0];
            int beginPage = currentReadingPage+1;
            int endPage = Math.min(SplitPDF.getPdfTotalPages(currentBook),currentReadingPage+EVERY_TIME_READING_PAGE_NUM);
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
    private static String getCurrentBook() {
        List<String> sortedBooks = getSortedUnreadedBooks();
        if (sortedBooks.size() == 0) {
            LOGGER.error("get current book failed!");
            return "";
        }
        return sortedBooks.get(0);
    }

    private static List<String> getSortedUnreadedBooks() {
        List<String> files = listDirFiles(ebooksPath);
        if (files.size() == 0) {
            LOGGER.error("read ebooks failed!");
            return files;
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
     * 判断是否到达定时执行任务的时间,
     * !固定时间间隔执行模式!
     * @param firstExecuteTime:首次执行时间
     * @param repeatEveryMinutes:重复多少分钟执行一次
     */
    public static boolean timeToExecute(Date firstExecuteTime,int repeatEveryMinutes) {
        int nm = 1000*60;
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

    /*
     *判断是否到达定时执行任务的时间,
     * ! 指定时间执行模式(需要指定时分秒)
     * @param executeTimeSet:要执行的时间集合
     */
    public static boolean timeToExecute(Set<Calendar> executeTimeSet) {
        for (Calendar calendar:executeTimeSet) {
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            Calendar now = Calendar.getInstance();
            int curHour = now.get(Calendar.HOUR_OF_DAY);
            int curMinute = now.get(Calendar.MINUTE);
            int curSecond = now.get(Calendar.SECOND);
            if (hour == curHour && minute == curMinute && second == curSecond) {
                return true;
            }
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

    public static boolean checkReadyToExecute(EMAIL_EXCUTE_MODE mode,Date firstExecuteTime,
                                              int repeatEveryMinutes,Set<Calendar> fixTimeSet) {
        if (mode == EMAIL_EXCUTE_MODE.SAME_TIME_DELTA) {
            return timeToExecute(firstExecuteTime, repeatEveryMinutes);
        } else {
            return timeToExecute(fixTimeSet);
        }
    }
}