package cn.edu.cqupt.nmid.headline.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leon on 2/7/15.
 */
public class TimeUtils {
  private final static long minute = 60 * 1000;// 1分钟
  private final static long hour = 60 * minute;// 1小时
  private final static long day = 24 * hour;// 1天
  private final static long month = 31 * day;// 月
  private final static long year = 12 * month;// 年

  /**
   * 返回文字描述的日期
   */
  public static String getTimeFormatDate(Date date) {
    if (date == null) {
      return null;
    }
    long diff = new Date().getTime() - date.getTime();
    long r = 0;
    if (diff > year) {
      r = (diff / year);
      return r + "年前";
    }
    if (diff > month) {
      r = (diff / month);
      return r + "个月前";
    }
    if (diff > day) {
      r = (diff / day);
      return r + "天前";
    }
    if (diff > hour) {
      r = (diff / hour);
      return r + "个小时前";
    }
    if (diff > minute) {
      r = (diff / minute);
      return r + "分钟前";
    }
    return "刚刚";
  }

  public static String getTimeFormatText(String yyyy_MM_dd_HH_mm_ss) {
    DateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return getTimeFormatDate(date.parse(yyyy_MM_dd_HH_mm_ss));
    } catch (Exception e){
      e.printStackTrace();
      return "刚刚";
    }
  }
}
