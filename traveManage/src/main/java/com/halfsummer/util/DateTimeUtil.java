package com.halfsummer.util;


import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * 日期 工具类
 *
 * 一个日期时间工具类，提供了一些方法用于将日期和字符串之间进行转换。使用了Joda-Time库进行日期时间的处理。主要包括以下方法：
 *
 * strToDate(String dateTimeStr,String formStr)方法：将给定的字符串日期时间格式化为指定格式的日期类型，并返回该日期。
 * dateToStr(Date date,String formatStr)方法：将给定的日期类型格式化为指定格式的字符串日期，并返回该字符串。
 * strToDate(String dateTimeStr)方法：将给定的字符串日期时间格式化为标准格式的日期类型，并返回该日期。
 * dateToStr(Date date)方法：将给定的日期类型格式化为标准格式的字符串日期，并返回该字符串。
 * 其中，STANDARD_FORMAT是一个常量，用于表示标准的日期时间格式。还提供了一个main方法用于测试。
 */
public class DateTimeUtil {

    //joda-time

    //str->Date
    //Date->str

    public  static final  String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date strToDate(String dateTimeStr,String formStr){
        DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern(formStr);
        DateTime dateTime=dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static  String dateToStr(Date date,String formatStr){
        if (date==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString();
    }

    public static Date strToDate(String dateTimeStr){
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }

    public static void main(String[] args) {
        System.out.println(DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateTimeUtil.strToDate("2010-01-01 11:11:11","yyyy-MM-dd HH:mm:ss"));

    }
}
