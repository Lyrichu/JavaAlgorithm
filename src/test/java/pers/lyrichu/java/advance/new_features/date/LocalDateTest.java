package pers.lyrichu.java.advance.new_features.date;

import com.google.common.base.Throwables;
import org.assertj.core.internal.cglib.core.Local;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

public class LocalDateTest {

    @Test
    public void test1() {
        LocalDate date = LocalDate.now();
        System.out.println(date);
        // 年月日构造日期
        LocalDate date1 = LocalDate.of(2019,2,12);
        System.out.printf("year:%d,month:%02d,day:%02d\n",date1.getYear(),date1.getMonth().getValue(),date1.getDayOfMonth());
        // 根据字符串进行解析
        LocalDate date2 = LocalDate.parse("2020-02-28");
        System.out.println(date2);
        // 拒绝不合法的日期
        try {
            LocalDate date3 = LocalDate.parse("2020-02-30");
            System.out.println(date3);
        } catch (DateTimeParseException e) {
            System.out.println(Throwables.getStackTraceAsString(e));
        }
        // 解析指定的格式
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date4 = LocalDate.parse("2020/02/10",dtf);
        System.out.println(date4);
    }

    @Test
    public void test2() {
        LocalDate today = LocalDate.now();
        // 取本月第一天
        System.out.printf("first day of this month:%s\n",today.with(TemporalAdjusters.firstDayOfMonth()));
        // 本月第2天
        System.out.printf("second day of this month:%s\n",today.withDayOfMonth(2));
        // 本月最后一天
        System.out.printf("last day of month：%s\n",today.with(TemporalAdjusters.lastDayOfMonth()));
        // 下一天
        System.out.printf("next day of this month:%s\n",today.plusDays(1));
        // 前一天
        System.out.printf("before day of this month:%s\n",today.minusDays(1));
        // 2019年3月第二个周二
        System.out.printf("2019年3月第二个周二:%s\n",LocalDate.parse("2019-01-01")
                .plusMonths(2)
                .with(TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.TUESDAY)));
    }

    @Test
    public void test3() {
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime);
        // 清除毫秒数
        System.out.println(nowTime.withNano(0));
        // 0点
        LocalTime zeroTime = LocalTime.of(0,0,0);
        System.out.printf("zero: %s\n",zeroTime);
        // 12点
        LocalTime midTime = LocalTime.parse("12:00:00");
        System.out.println(midTime);
    }

}
