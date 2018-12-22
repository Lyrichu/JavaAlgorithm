package pers.lyrichu.run.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


import static pers.lyrichu.run.tools.Constant.EMAIL_EXCUTE_MODE;

public class Helper {
    private static Logger LOGGER = LoggerFactory.getLogger(Helper.class);

    public static int getDaysDelta(Date date1,Date date2) {
        long msDelta = date1.getTime() -date2.getTime();
        long div = 24*3600*1000;
        return Math.abs((int) (msDelta/div));
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

    public static boolean checkReadyToExecute(EMAIL_EXCUTE_MODE mode,Date firstExecuteTime,
                                              int repeatEveryMinutes,Set<Calendar> fixTimeSet) {
        if (mode == EMAIL_EXCUTE_MODE.SAME_TIME_DELTA) {
            return timeToExecute(firstExecuteTime, repeatEveryMinutes);
        } else {
            return timeToExecute(fixTimeSet);
        }
    }

    public static Calendar getExecuteCalendar(int hour,int minute,int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        return calendar;
    }
}