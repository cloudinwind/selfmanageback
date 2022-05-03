package com.cloudinwind.selfmanage.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.*;

public class DateUtils {

    // 获取过去几天的日期 intervals=7 表示获取过去7天的日期
    public static List<String> getDays(int intervals) {
        List<String> pastDaysList = new ArrayList<>();
        for (int i = intervals - 1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期  0表示当天
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;

    }


    public static String dateToStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    // 当前周第一天 周一日期
    public static String getFirstDayWeek(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(SECOND, 0);
        calendar.set(MINUTE, 0);
        calendar.set(MILLISECOND, 0);
        // 当前周第一天 周一
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(calendar.getTime());
    }

    // 当前周最后一天 周日日期
    public static String getLastDayWeek(){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(SECOND, 0);
        calendar.set(MINUTE, 0);
        calendar.set(MILLISECOND, 0);
        // 当前周第一天 周一
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(SECOND, 59);
        calendar.set(MINUTE, 59);
        calendar.set(MILLISECOND, 999);
        // 当前周最后一天 周末
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    // 当前月第一天日期
    public static String getFirstDayMonth(){
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        //当前月份第一天
        start.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH ),
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return dateToStr(start.getTime());

    }

    // 当前月最后一天日期
    public static String getLastDayMonth(){
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        //当前月份最后一天
        end.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH ),
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);

        return dateToStr(end.getTime());

    }



}
