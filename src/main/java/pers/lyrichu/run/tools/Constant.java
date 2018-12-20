package pers.lyrichu.run.tools;

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
    public static final String DAILY_READING_SUBJECT = "每日读书";

    // ebooks path
    public static final String EBOOKS_BASE_PATH = "/home/ebooks";
    // 每次阅读的page数量
    public static final int EVERY_TIME_READING_PAGE_NUM = 10;
    // 保存历史阅读信息的文件地址(json格式保存)
    public static final String EBOOKS_READING_INFO_PATH = "/home/ebooks/readingInfo.json";
    // 发送邮件的几种模式
    public enum EMAIL_EXCUTE_MODE {
        SAME_TIME_DELTA, // 固定的时间间隔发送
        FIX_TIME // 在特定的时间点发送
    }
}