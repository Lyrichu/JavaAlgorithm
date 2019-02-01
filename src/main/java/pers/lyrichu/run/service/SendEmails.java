package pers.lyrichu.run.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pers.lyrichu.run.tools.EmailTools;

import java.util.HashSet;
import java.util.Set;

import static pers.lyrichu.run.tools.Constant.*;

/*
 * 在服务器上定时执行一些发送邮件的任务
 */
public class SendEmails {
    private static final String[] toList = {"919987476@qq.com"};

    private static Logger LOGGER = LoggerFactory.getLogger(SendEmails.class);

    public static void main(String[] args) {
        Thread weiboHotQueryThread = EmailTools.getEmailTaskThread(
                EMAIL_TASK.WEIBO_HOTQUERY,"weiboHotQuery",WEIBO_HOTQUERY_SUBJECT,toList,
                EMAIL_EXCUTE_MODE.FIX_TIME,null,0,WEIBO_HOTQUERY_EXECUTE_TIME_SET
        );
        Thread toutiaoHotQueryThread = EmailTools.getEmailTaskThread(
                EMAIL_TASK.TOUTIAO_HOTQUERY,"toutiaoHotQuery",TOUTIAO_HOTQUERY_SUBJECT,toList,
                EMAIL_EXCUTE_MODE.FIX_TIME,null,0,TOUTIAO_HOTQUERY_EXECUTE_TIME_SET
        );
        Thread ebooksDailyReadingThread = EmailTools.getEmailTaskThread(
                EMAIL_TASK.EBOOKS_DAILY_READING,"ebooksDailyReading",EBOOKS_DAILY_READING_SUBJECT,toList,
                EMAIL_EXCUTE_MODE.FIX_TIME,null,0,EBOOKS_DAILY_READING_EXECUTE_TIME_SET
        );

        Thread papersDailyReadingThread = EmailTools.getEmailTaskThread(
                EMAIL_TASK.PAPERS_DAILY_READING,"papersDailyReading",PAPERS_DAILY_READING_SUBJECT,toList,
                EMAIL_EXCUTE_MODE.FIX_TIME,null,0,PAPERS_DAILY_READING_EXECUTE_TIME_SET
        );

        Set<Thread> threadSet = new HashSet<Thread>(){
            {
                add(weiboHotQueryThread);
                add(toutiaoHotQueryThread);
                add(ebooksDailyReadingThread);
                add(papersDailyReadingThread);
            }
        };

        for (Thread thread:threadSet) {
            thread.start();
        }
    }
}
