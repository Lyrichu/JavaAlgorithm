package pers.lyrichu.run.tools;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Constant {
    // 发邮件地址
    public static final String EMAIL_FROM_ADDRESS = "lyrichu@foxmail.com";
    // 授权码替代原始密码
    public static final String EMAIL_FROM_PASSWD = "bzphfvgjsgwvbbbj";
    // QQ email host
    public static final String QQ_EMAIL_HOST = "smtp.qq.com";

    public static final String WEIBO_HOTQUERY_SUBJECT = "微博热搜榜";
    public static final String TOUTIAO_HOTQUERY_SUBJECT = "头条热搜榜";
    public static final String TECENT_NEWS_SUBJECT = "腾讯新闻榜";
    public static final String EBOOKS_DAILY_READING_SUBJECT = "每日读书";
    public static final String PAPERS_DAILY_READING_SUBJECT = "每日paper";

    // ebooks path
    public static final String EBOOKS_BASE_PATH = "/home/ebooks";
    // paper path
    public static final String PAPERS_BASE_PATH = "/home/papers";
    // 每次阅读的page数量
    public static final int EVERY_TIME_READING_PAGE_NUM = 10;
    // 保存历史阅读信息的文件地址(json格式保存)
    public static final String EBOOKS_READING_INFO_PATH = "/home/ebooks/ebooksReadingInfo.json";
    // 保存paper阅读信息的地址
    public static final String PAPERS_READING_INFO_PATH = "/home/papers/papersReadingInfo.json";
    // 默认休眠ms数
    public static final int DEFAUL_SLEEP_TIME_MS = 100;

    // 发送邮件的几种模式
    public enum EMAIL_EXCUTE_MODE {
        SAME_TIME_DELTA, // 固定的时间间隔发送
        FIX_TIME // 在特定的时间点发送
    }

    public enum EMAIL_TASK {
        WEIBO_HOTQUERY,
        TECENT_NEWS,
        TOUTIAO_HOTQUERY,
        EBOOKS_DAILY_READING,
        PAPERS_DAILY_READING
    }

    public static Set<Calendar> WEIBO_HOTQUERY_EXECUTE_TIME_SET = new HashSet<Calendar>(){
        {
            add(Helper.getExecuteCalendar(9,30,0));
            add(Helper.getExecuteCalendar(12,30,0));
            add(Helper.getExecuteCalendar(16,0,0));
            add(Helper.getExecuteCalendar(19,0,0));
        }
    };

    public static Set<Calendar> TOUTIAO_HOTQUERY_EXECUTE_TIME_SET = new HashSet<Calendar>(){
        {
            add(Helper.getExecuteCalendar(9,30,15));
            add(Helper.getExecuteCalendar(12,30,15));
            add(Helper.getExecuteCalendar(16,0,15));
            add(Helper.getExecuteCalendar(19,0,15));
        }
    };

    public static Set<Calendar> TECENT_NEWS_EXECUTE_TIME_SET = new HashSet<Calendar>(){
        {
            add(Helper.getExecuteCalendar(9,30,30));
            add(Helper.getExecuteCalendar(12,30,30));
            add(Helper.getExecuteCalendar(16,0,30));
            add(Helper.getExecuteCalendar(19,0,30));
        }
    };

    public static Set<Calendar> EBOOKS_DAILY_READING_EXECUTE_TIME_SET = new HashSet<Calendar>(){
        {
            add(Helper.getExecuteCalendar(9,40,0));
            add(Helper.getExecuteCalendar(12,40,0));
            add(Helper.getExecuteCalendar(18,30,0));
        }
    };

    public static Set<Calendar> PAPERS_DAILY_READING_EXECUTE_TIME_SET = new HashSet<Calendar>(){
        {
            add(Helper.getExecuteCalendar(9,20,0));
        }
    };


}