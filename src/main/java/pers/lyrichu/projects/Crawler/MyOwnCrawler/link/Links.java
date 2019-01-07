package pers.lyrichu.projects.Crawler.MyOwnCrawler.link;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/*
 * 1.存储已经访问过的url 路径,和待访问的url路径
 */
public class Links {
    // 已经访问过的Set<url>
    private static Set<String> visitedUrlSet = new HashSet<>();
    /* 待访问的url 集合
     * 1.规定待访问次序
     * 2.保证提供不重复的待访问地址
     */
    private static List<String> unVisitedUrlQueue = new LinkedList<>();

    /*
     * 获得已访问的url数目
     */
    public static int getVisitedUrlNum() {
        return visitedUrlSet.size();
    }

    /*
     * url 添加到　已访问url set
     */
    public static void addVisitedUrlSet(String url) {
        visitedUrlSet.add(url);
    }

    /*
     * 移除访问过的url
     */
    public static void removeVisitedUrlSet(String url) {
        visitedUrlSet.remove(url);
    }

    /*
     * 获得待访问的url队列
     */
    public static List<String> getUnVisitedUrlQueue() {
        return unVisitedUrlQueue;
    }

    /*
     * 添加url到待访问的队列中,确保每个url只被访问一次
     */
    public static void addUnVisitedUrlQueue(String url) {
        if (url != null && !url.trim().equals("")
                && !visitedUrlSet.contains(url)
                && !unVisitedUrlQueue.contains(url)) {
            unVisitedUrlQueue.add(url);
        }
    }

    /*
     * 删除队列头部的url
     */
    public static String removeHeadOfUnVisitedUrlQueue() {
        if (!unVisitedUrlQueueIsEmpty()) {
            return unVisitedUrlQueue.remove(unVisitedUrlQueue.size()-1);
        }
        return null;
    }

    /*
     * 判断 待访问url 队列是否为空
     */
    public static boolean unVisitedUrlQueueIsEmpty() {
        return unVisitedUrlQueue.isEmpty();
    }
}