package com.test;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Describe:
 * @Author: HAPPY
 * @Date: 2022-11-09 10:17 星期三
 **/
public class TestDate {
    public static void main(String[] args) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String nowDate = sdf.format(new Date());
        //System.out.println(nowDate);
        //String xMonthLastDate = getXMonthLastDate(nowDate, -24);
        //System.out.println(xMonthLastDate);
        //
        //String xMonthFirstDate = getXMonthFirstDate(nowDate, -24);
        //System.out.println(xMonthFirstDate);
    
        //testStrDateSort();
    
        String lastDayOfMonth = getLastDayOfMonth("yyyy-MM-dd HH:mm:ss");
        System.out.println(lastDayOfMonth);
    }
    @Data
    static
    class Datetest{
        private String strDate;
        private String name;
    }
    
    public static void testStrDateSort2(){
        List<String> dateStrList = new ArrayList<>();
        Datetest datetest1 = new Datetest();
        Datetest datetest2 = new Datetest();
        Datetest datetest3 = new Datetest();
        Datetest datetest4 = new Datetest();
        datetest1.setStrDate("2022-01-02 11:33:33");
        datetest1.setName("张三");
        datetest2.setStrDate("2022-01-02 11:33:33");
        datetest2.setName("历史");
        datetest3.setStrDate("2022-01-03 11:33:34");
        datetest3.setName("wangwu");
        datetest4.setStrDate("2022-01-04 11:33:35");
        datetest4.setName("zhaoliu");
        List<Datetest> datetests = new ArrayList<>();
        datetests.add(datetest1);
        datetests.add(datetest2);
        datetests.add(datetest3);
        datetests.add(datetest4);
    
        List<Datetest> collect = datetests.stream().sorted(Comparator.comparing(o -> o.getStrDate())).collect(Collectors.toList());
        System.out.println(collect);
    }
    
    public static void testStrDateSort() {
       
        List<String> strDateList = new ArrayList<>();
        strDateList.add("2019-02-21");
        strDateList.add("2020-02-21");
        strDateList.add("2018-02-21");
        strDateList.add("2017-02-21");
        
        
        //自定义list排序，集合数据(月份)按升序排序;
        SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Collections.sort(strDateList, new Comparator<String>() {
            public int compare(String o1, String o2) {
                try {
                    Date date1 = sdft.parse(o1);
                    Date date2 = sdft.parse(o2);
                    if (date1.getTime() < date2.getTime()) {
                        return -1;//调整顺序,-1为不需要调整顺序;
                    }
                    if (o1.equals(o2)) {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 1;
            }
        });
        System.out.println(strDateList);
    }
    
    
    //将日期格式yyyy-MM-dd HH:mm:ss 格式向前推X个月或者向后推X个月后取最后一天的方法(向后+month 向前-month)
    public static String getXMonthLastDate(String date, int month) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //将传过来的日期设置给calendar
            calendar.setTime(sdf.parse(date));
            //将传过来的月份减去X个月或者加上X个月
            calendar.add(Calendar.MONTH, month);
            //获取月的最后一天日期
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());
    }
    
    //将日期格式yyyy-MM-dd HH:mm:ss 格式向前推X个月或者向后推X个月后取第一天的方法(向后+month 向前-month)
    public static String getXMonthFirstDate(String date, int month) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            //将传过来的日期设置给calendar
            calendar.setTime(sdf.parse(date));
            //将传过来的月份减去X个月或者加上X个月
            calendar.add(Calendar.MONTH, month);
            //获取月的最后一天日期
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());
    }
    
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateToString(cal.getTime(), format);
    }
    
    /**
     * 把日期转换为字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // logger.error ( e );
        }
        return result;
    }
}
