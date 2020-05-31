package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeUtil {

  public static final String STANDARD_FORMART = "yyyy-MM-dd HH:mm:ss";

  public static Date strToDate(String dateTimeStr, String formatStr) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
    DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
    return dateTime.toDate();
  }

  public static String dateToStr(Date date, String formatStr) {
    if (date == null) {
      return StringUtils.EMPTY;
    }
    DateTime dateTime = new DateTime(date);
    return dateTime.toString(formatStr);
  }

  public static Date strToDate(String dateTimeStr) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMART);
    DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr);
    return dateTime.toDate();
  }

  public static String dateToStr(Date date) {
    if (date == null) {
      return StringUtils.EMPTY;
    }
    DateTime dateTime = new DateTime(date);
    return dateTime.toString(STANDARD_FORMART);
  }

  public static void main(String[] args) {
    System.out.println(strToDate("21/05/21 12:00:56", "dd/MM/yy HH:mm:ss"));
    System.out.println(dateToStr(new Date(), "yy/MM/dd HH:mm"));
    System.out.println(strToDate("2020-05-21 14:32:24"));
    System.out.println(dateToStr(new Date()));
  }
}
