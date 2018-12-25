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

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

import static pers.lyrichu.java.basic.io.FileUtils.listDirFiles;
import static pers.lyrichu.java.util.scripts.SendEmail.postEmail;
import static pers.lyrichu.run.tools.Constant.*;

public class EmailTools {

    private static Logger LOGGER = LoggerFactory.getLogger(EmailTools.class);
    // 上次执行日期
    private static String lastExecuteDate = Helper.getOriDate(0);
    /*
     * 邮件发送微博热搜榜
     */
    public static void sendWeiboHotQuery(String subject, String[] toList, Constant.EMAIL_EXCUTE_MODE mode,
                                         Date firstExecuteTime, int repeatEveryMinutes, Set<Calendar> fixTimeSet
    ) throws Exception {
        // 检查是否到达执行时间
        boolean readyToExcute = Helper.checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);

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
        } else {
            Thread.sleep(DEFAUL_SLEEP_TIME_MS);
        }
    }

    private static void sendToutiaoHotQuery(String subject,String[] toList,Constant.EMAIL_EXCUTE_MODE mode,
                                            Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet) throws Exception {
        boolean readyToExecute = Helper.checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);

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
        } else {
            Thread.sleep(DEFAUL_SLEEP_TIME_MS);
        }
    }


    private static void sendTecentNews(String subject,String[] toList,Constant.EMAIL_EXCUTE_MODE mode,
                                       Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet
    ) throws Exception {
        boolean readyToExecute = Helper.checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
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
        } else {
            Thread.sleep(DEFAUL_SLEEP_TIME_MS);
        }
    }

    private static void sendPieceOfBooks(String subject,String[] toList,Constant.EMAIL_EXCUTE_MODE mode,
                                         Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet
    ) throws Exception {
        boolean readyToExecute = Helper.checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
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

                alreadyReadedBooks.add(getNameByAbsPath(currentBook));
                // 设置本书已经阅读
                if (!setReaded(currentBook)) {
                    String errMsg = String.format("设置%s 为已阅读出错!请及时检查处理!",currentBook);
                    LOGGER.error(errMsg);
                    postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,errMsg);
                    return;
                }
                currentBook = getCurrentBook();
                currentReadingPage = 0;
            }
            String currentBookName = currentBook.split("_")[0];
            int beginPage = currentReadingPage+1;
            int endPage = Math.min(SplitPDF.getPdfTotalPages(currentBook),currentReadingPage+EVERY_TIME_READING_PAGE_NUM);
            String splitPdfPath = String.format("%s_%d_%d.pdf",currentBookName,beginPage,endPage);
            SplitPDF.splitPDF(currentBook,splitPdfPath,beginPage,endPage);
            // 如果是第一天阅读
            if (alreadyReadedDays == 0) {
                alreadyReadedDays = 1;
            } else {
                // 如果上次执行日期和当前日期不一致,则alreadyReadedDays递增1
                String currentExecuteDate = Helper.getOriDate(0);
                if (!lastExecuteDate.equals(currentExecuteDate)) {
                    alreadyReadedDays++;
                    lastExecuteDate = currentExecuteDate;
                }
            }
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
            postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,msg,files);
            // 删除split pdf
            if (!FileUtils.deleteFile(splitPdfPath)) {
                String errInfo = String.format("删除%s失败!请及时检查修复!",splitPdfPath);
                LOGGER.error(errInfo);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,errInfo);
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(EBOOKS_READING_INFO_PATH));
            writer.write(saveJson.toJSONString());
            writer.newLine();
            writer.close();
        } else {
            Thread.sleep(DEFAUL_SLEEP_TIME_MS);
        }
    }

    private static void sendPapers(String subject,String[] toList,Constant.EMAIL_EXCUTE_MODE mode,
                                         Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet
    ) throws Exception {
        boolean readyToExecute = Helper.checkReadyToExecute(mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
        int alreadyReadedPapersNum = 0;
        int alreadyReadedDays = 0;
        List<String> alreadyReadedPapers = new ArrayList<>();
        if (readyToExecute) {
            List<String> sortedUnreadedPapers = getSortedUnreadedPapers();
            if (sortedUnreadedPapers.size() == 0) {
                String errMsg = "读取服务器paper数目为空,请及时检查修复!";
                LOGGER.error(errMsg);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,errMsg);
                return;
            }
            if (sortedUnreadedPapers.size() == 1) {
                String warnMsg = String.format("当前服务器paper库存只有%d篇未读的paper:%s!请及时添加new paper!",sortedUnreadedPapers.size(),sortedUnreadedPapers.get(0));
                LOGGER.error(warnMsg);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,warnMsg);
            }

            String currentPaper = getCurrentPaper();
            File readingInfoFile = new File(PAPERS_READING_INFO_PATH);
            if (readingInfoFile.exists()) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(readingInfoFile));
                    String line = reader.readLine();
                    JSONObject json = JSON.parseObject(line);
                    alreadyReadedDays = json.getIntValue("alreadyReadedDays");
                    alreadyReadedPapersNum = json.getIntValue("alreadyReadedPapersNum");
                    JSONArray array = json.getJSONArray("alreadyReadedPapers");
                    if (array == null || array.size() == 0) {
                        alreadyReadedPapers = new ArrayList<>();
                    } else {
                        alreadyReadedPapers = new ArrayList<>();
                        for (int i = 0; i < array.size(); i++) {
                            alreadyReadedPapers.add(array.getString(i));
                        }
                    }
                    reader.close();
                } catch (Exception e) {
                    LOGGER.error("read info from {} failed!{}",PAPERS_READING_INFO_PATH,e);
                }
            }

            // 如果是第一天阅读
            if (alreadyReadedDays == 0) {
                alreadyReadedDays = 1;
            } else {
                // 如果上次执行日期和当前日期不一致,则alreadyReadedDays递增1
                String currentExecuteDate = Helper.getOriDate(0);
                if (!lastExecuteDate.equals(currentExecuteDate)) {
                    alreadyReadedDays++;
                    lastExecuteDate = currentExecuteDate;
                }
            }
            alreadyReadedPapersNum++;

            // 已经阅读的情况
            StringBuilder readedInfo = new StringBuilder("你尚未读完一篇paper,快来坚持阅读吧!");
            if (alreadyReadedPapers.size() > 0) {
                readedInfo = new StringBuilder(String.format("太棒了!你已经阅读了<font color=\"red\">%d</font>篇paper!<br>",alreadyReadedPapers.size()));
                readedInfo.append("以下是你已经读过的paper清单:<br>");
                // paper 都是以 | 划分subject和title的
                // 列出已读清单需要按照subject来进行划分
                // 按照subject 进行排序
                Map<String,List<String>> papersMap = new TreeMap<>(new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
                for (int i = 0;i<alreadyReadedPapers.size();i++) {
                    String subjectAndTitle = alreadyReadedPapers.get(i);
                    String[] splits = subjectAndTitle.split("\\|");
                    String paperSubject = splits[0];
                    String paperTitle = splits[1];
                    if (papersMap.containsKey(paperSubject)) {
                        papersMap.get(paperSubject).add(paperTitle);
                    } else {
                        papersMap.put(paperSubject,
                                new ArrayList<String>()
                                {
                                    {
                                        add(paperTitle);
                                    }
                                }
                            );
                    }
                }
                int subIndex,index;
                subIndex = index = 0;
                // key是paper subject
                for (String key:papersMap.keySet()) {
                    List<String> subPapersList = papersMap.get(key);
                    readedInfo.append(String.format("%d.<font color=\"red\">%s</font><br>",subIndex+1,key));
                    for (String title:subPapersList) {
                        readedInfo.append(String.format("&nbsp;&nbsp;&nbsp;&nbsp;%d.%d:%s<br>",subIndex+1,index+1,title));
                        index++;
                    }
                    subIndex++;
                }

            }
            String msg = String.format("<b>Congratulations!</b><br>" +
                            "这是你第<font color=\"red\">%d</font>天阅读paper!<br>" +
                            // 已经阅读的书籍情况
                            "%s<br>" +
                            "你即将阅读第<font color=\"red\">%d</font>篇paper!太了不起了!<br>" +
                            "继续加油!祝你阅读paper愉快!",
                    alreadyReadedDays,
                    readedInfo.toString(),
                    alreadyReadedPapersNum

            );
            // 发送当前正在阅读的paper
            String[] files = {currentPaper};
            postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,subject,msg,files);
            // 设置current paper 为 readed
            if (!setReaded(currentPaper)) {
                String errMsg = String.format("set %s readed failed!Please check it out!",currentPaper);
                LOGGER.error(errMsg);
                postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,"email error",errMsg);
            }
            // 当前paper 加入已阅读列表
            alreadyReadedPapers.add(getNameByAbsPath(currentPaper));
            // 将已经阅读的信息保存到磁盘
            JSONObject saveJson = new JSONObject();
            JSONArray saveArr = new JSONArray();
            saveArr.addAll(alreadyReadedPapers);
            saveJson.put("alreadyReadedPapersNum",alreadyReadedPapersNum);
            saveJson.put("alreadyReadedDays",alreadyReadedDays);
            saveJson.put("alreadyReadedPapers",saveArr);
            BufferedWriter writer = new BufferedWriter(new FileWriter(PAPERS_READING_INFO_PATH));
            writer.write(saveJson.toJSONString());
            writer.newLine();
            writer.close();

        } else {
            Thread.sleep(DEFAUL_SLEEP_TIME_MS);
        }
    }

    private static boolean setReaded(String readingPath) {
        String readedName = readingPath.replace("unreaded","readed");
        return FileUtils.renameFile(readingPath,readedName);
    }

    /*
     * 通过绝对路径得到 name
     */
    private static String getNameByAbsPath(String absPath) {
        String[] splits = absPath.split("/");
        return splits[splits.length-1].split("_")[0];
    }
    /*
     * 得到当前正在阅读的路径
     */
    private static String getCurrentReading(String path) {
        List<String> sortedUnreaded = getSortedUnreaded(path);
        if (sortedUnreaded.size() == 0) {
            LOGGER.error("get current {} failed!",path);
            return "";
        }
        return sortedUnreaded.get(0);
    }

    private static String getCurrentBook() {
        return getCurrentReading(EBOOKS_BASE_PATH);
    }

    private static String getCurrentPaper() {
        return getCurrentReading(PAPERS_BASE_PATH);
    }

    private static List<String> getSortedUnreaded(String path) {
        List<String> files = listDirFiles(path);
        if (files.size() == 0) {
            LOGGER.error("read {} failed!",path);
            return files;
        }
        // readedBooks like:Effective Java 中文第二版-1.readed.pdf
        // unreadedBooks like:Effective Java 中文第二版-1.unreaded.pdf
        List<String> unreaded = new ArrayList<>();
        for (String file:files) {
            if (file.contains("unreaded")) {
                unreaded.add(file);
            }
        }
        // 对于unreaded ,按照数字标号进行排序
        Collections.sort(unreaded, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int n1 = Integer.parseInt(o1.split("\\.")[0].split("_")[1]);
                int n2 = Integer.parseInt(o2.split("\\.")[0].split("_")[1]);
                return Integer.compare(n1,n2);
            }
        });
        return unreaded;
    }

    private static List<String> getSortedUnreadedBooks() {
        return getSortedUnreaded(EBOOKS_BASE_PATH);
    }

    private static List<String> getSortedUnreadedPapers() {
        return getSortedUnreaded(PAPERS_BASE_PATH);
    }

    public static Thread getEmailTaskThread(EMAIL_TASK task,String threadName,String subject,String[] toList,EMAIL_EXCUTE_MODE mode,
                                            Date firstExecuteTime,int repeatEveryMinutes,Set<Calendar> fixTimeSet) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (task == EMAIL_TASK.WEIBO_HOTQUERY) {
                            sendWeiboHotQuery(subject,toList,mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
                        } else if (task == EMAIL_TASK.TOUTIAO_HOTQUERY) {
                            sendToutiaoHotQuery(subject,toList,mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
                        } else if (task == EMAIL_TASK.TECENT_NEWS) {
                            sendTecentNews(subject,toList,mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
                        } else if (task == EMAIL_TASK.PAPERS_DAILY_READING) {
                            sendPapers(subject,toList,mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
                        }
                        else {
                            sendPieceOfBooks(subject,toList,mode,firstExecuteTime,repeatEveryMinutes,fixTimeSet);
                        }
                    }
                } catch (Exception e) {
                    try {
                        postEmail(EMAIL_FROM_ADDRESS,toList,EMAIL_FROM_PASSWD,QQ_EMAIL_HOST,"email error!",e.toString());
                    } catch (GeneralSecurityException ge) {
                        LOGGER.error("post email error:{}",ge);
                    }
                }
            }
        };
        Thread thread = new Thread(run,threadName);
        return thread;
    }

}