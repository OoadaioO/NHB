package com.fast.library.utils;

import android.os.SystemClock;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 说明：日期工具类
 * <p/>
 * 作者：fanly
 * <p/>
 * 时间：2015/10/28 21:19
 * <p/>
 * 版本：verson 1.0
 */

public final class DateUtils {

    /**
     * 禁止实例化
     */
    private DateUtils() {
    }

    /**
     * 格式：【yyyy】
     */
    public static final String FORMAT_YYYY_1 = "yyyy";

    /**
     * 格式：【yyyy年】
     */
    public static final String FORMAT_YYYY_2 = "yyyy年";

    /**
     * 格式：【MM】
     */
    public static final String FORMAT_MM_1 = "MM";

    /**
     * 格式：【MM月】
     */
    public static final String FORMAT_MM_2 = "MM月";

    /**
     * 格式：【dd】
     */
    public static final String FORMAT_DD_1 = "dd";

    /**
     * 格式：【dd日】
     */
    public static final String FORMAT_DD_2 = "dd日";

    /**
     * 格式：【HH】
     */
    public static final String FORMAT_HH_1 = "HH";

    /**
     * 格式：【HH时】
     */
    public static final String FORMAT_HH_2 = "HH时";

    /**
     * 格式：【mm】
     */
    public static final String FORMAT_MM_3 = "mm";

    /**
     * 格式：【mm分】
     */
    public static final String FORMAT_MM_4 = "mm分";

    /**
     * 格式：【ss】
     */
    public static final String FORMAT_SS_1 = "ss";

    /**
     * 格式：【ss秒】
     */
    public static final String FORMAT_SS_2 = "ss秒";

    /**
     * 格式：【HH:mm】
     */
    public static final String FORMAT_HH_MM_1 = "HH:mm";

    /**
     * 格式：【HH时mm分】
     */
    public static final String FORMAT_HH_MM_2 = "HH时mm分";

    /**
     * 格式：【HH:mm:ss】
     */
    public static final String FORMAT_HH_MM_SS_1 = "HH:mm:ss";

    /**
     * 格式：【HH时mm分ss秒】
     */
    public static final String FORMAT_HH_MM_SS_2 = "HH时mm分ss秒";

    /**
     * 格式：【yyyy-MM-dd】
     */
    public static final String FORMAT_YYYY_MM_DD_1 = "yyyy-MM-dd";

    /**
     * 格式：【yyyy/MM/dd】
     */
    public static final String FORMAT_YYYY_MM_DD_2 = "yyyy/MM/dd";

    /**
     * 格式：【yyyy年MM月dd日】
     */
    public static final String FORMAT_YYYY_MM_DD_3 = "yyyy年MM月dd日";

    /**
     * 格式：【yyyy-MM-dd HH:mm:ss】
     */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_1 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式：【yyyy/MM/dd HH:mm:ss】
     */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_2 = "yyyy/MM/dd HH:mm:ss";

    /**
     * 格式：【yyyy年MM月dd日 HH时mm分ss秒】
     */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_3 = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     * 格式：【yyyyMMddHHmmss】
     */
    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS_4 = "yyyyMMddHHmmss";

    /**
     * 格式：【yyyyMMddHHmmss】
     */
    public static final String[] WEEKDAYS = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    private static final SimpleDateFormat getSDF(String format) {
        return new SimpleDateFormat(format, Locale.getDefault());
    }

    private static final Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 说明：当前时间转数字
     *
     * @param format 时间格式
     * @return
     */
    public static final long getNowTimeNum(String format) {
        try {
            return Long.parseLong(getNowTime(format));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 说明：获取Date对象
     *
     * @return Date
     */
    public static final Date getNowTimeDate() {
        return new Date();
    }

    /**
     * 说明：获取当前时间（格式毫秒）
     *
     * @return
     */
    public static final long getMillisecond() {
        return getNowTimeDate().getTime();
    }

    /**
     * 说明：现在时间格式化后的形式
     *
     * @param format 时间格式
     * @return 格式化后的时间
     */
    public static final String getNowTime(String format) {
        SimpleDateFormat sdf = getSDF(format);
        return sdf.format(new Date());
    }

    /**
     * 说明：获取Unix时间戳（10位）1970年1月1日-当前时间 经过的秒数
     *
     * @return
     */
    public static final long getUnixTime() {
        return SystemClock.uptimeMillis() / 1000;
    }

    /**
     * 说明：字符串转Date
     *
     * @param oldDate 要转的时间
     * @param format  时间格式
     * @return
     */
    public static final Date getStrToDate(String oldDate, String format) {
        SimpleDateFormat sdf = getSDF(format);
        Date date = null;
        try {
            date = sdf.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 说明：一种格式时间转另一种格式时间
     *
     * @param oldDate   要转的时间
     * @param oldFormat 旧时间格式
     * @param newFormat 新时间格式
     * @return
     */
    public static final String getStrToStr(String oldDate, String oldFormat,
                                           String newFormat) {
        Date date = getStrToDate(oldDate, oldFormat);
        SimpleDateFormat sdf = getSDF(newFormat);
        return sdf.format(date);
    }

    /**
     * 说明：将字符串时间转Long类型
     *
     * @param date   要转的时间
     * @param format 时间格式
     * @return
     */
    public static final long getStrToLong(String date, String format) {
        return getStrToDate(date, format).getTime();
    }

    /**
     * 说明：获取当前时间，默认时间格式【yyyy-MM-dd HH:mm:ss】
     *
     * @return
     */
    public static final String getNowTime() {
        return getNowTime(FORMAT_YYYY_MM_DD_HH_MM_SS_1);
    }

    /**
     * 说明：获取星期【星期一】
     *
     * @return
     */
    public static int getWeekDay() {
        Calendar calendar = getCalendar();
        calendar.setTime(getNowTimeDate());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 说明：获取年份
     *
     * @return 例如【2015】
     */
    public static final String getYear() {
        return getNowTime(FORMAT_YYYY_1);
    }

    /**
     * 说明：获取月份
     *
     * @return 例如【02】
     */
    public static final String getMonth() {
        return getNowTime(FORMAT_MM_1);
    }

    /**
     * 说明：获取第几日
     *
     * @return 例如【11】
     */
    public static final String getDay() {
        return getNowTime(FORMAT_DD_1);
    }

    /**
     * 说明：获取两个时间间隔(单位：天)
     *
     * @param firstDate  第一个时间
     * @param secondDate 第一个时间
     * @param format     对应时间格式
     * @return 返回时间间隔的天数
     */
    public static final long getDaySpace(String firstDate, String secondDate,
                                         String format) {
        return getSecondSpace(firstDate, secondDate, format) / (24 * 60 * 60);
    }

    /**
     * 说明：获取两个时间间隔(单位：小时)
     *
     * @param firstDate  第一个时间
     * @param secondDate 第一个时间
     * @param format     对应时间格式
     * @return 返回时间间隔的小时数
     */
    public static final long getHourSpace(String firstDate, String secondDate,
                                          String format) {
        return getSecondSpace(firstDate, secondDate, format) / (60 * 60);
    }

    /**
     * 说明：获取两个时间间隔（单位：秒）
     *
     * @param firstDate  第一个时间
     * @param secondDate 第一个时间
     * @param format     对应时间格式
     * @return 返回时间间隔的秒数
     */
    public static final long getSecondSpace(String firstDate,
                                            String secondDate, String format) {
        SimpleDateFormat sdf = getSDF(format);
        long day = 0;
        try {
            Date date1 = sdf.parse(firstDate);
            Date date2 = sdf.parse(secondDate);
            day = (date2.getTime() - date1.getTime()) / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 说明：判断年份是否为闰年
     *
     * @param year
     * @return
     */
    public static final boolean isLeapYear(int year) {
        boolean isLeapYear = false;
        if (year <= 0) {
            isLeapYear = false;
        } else {
            isLeapYear = (!(year % 100 == 0) && (year % 4 == 0))
                    || ((year % 100 == 0) && (year % 400 == 0));
        }
        return isLeapYear;
    }

    /**
     * 说明：将long类型转为时间字符串
     *
     * @param l      类型时间
     * @param format 时间格式
     * @return
     */
    public static final String getLongToStr(long l, String format) {
        Date date = new Date(l);
        SimpleDateFormat sdf = getSDF(format);
        return sdf.format(date);
    }

    public static final String currentTimeMillis() {
        return System.currentTimeMillis() + "";
    }

    /**
     * 说明：根据不同时区，转换时间 2015年7月31日
     */
    public static final Date transformTime(Date date, TimeZone oldZone,
                                           TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    /**
     * 说明：判断用户的设备时区是否为东八区（中国） 2015年7月31日
     *
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        if (TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08"))
            defaultVaule = true;
        else
            defaultVaule = false;
        return defaultVaule;
    }

    /**
     * 说明： 根据日历的规则，为给定的日历字段添加或减去指定的时间量
     *
     * @param date   指定时间
     * @param field  日历字段（年或月或日）
     * @param amount 时间量
     * @param format 返回时间格式
     * @return 返回指定的时间格式的字符串
     */
    public static String add(Date date, int field, int amount, String format) {
        Calendar calendar = getCalendar();
        SimpleDateFormat sdf = getSDF(format);
        calendar.setTime(date);
        calendar.add(field, amount);
        return sdf.format(calendar.getTime());
    }

    /**
     * 说明：判断src时间是否在start-end区间
     *
     * @param src    指定时间
     * @param start  前范围
     * @param end    后范围
     * @param format 时间格式要统一
     * @return【true：在区间，false：不在区间】
     */
    public static boolean between(String src, String start, String end, String format) {
        Calendar srcCal = getCalendar();
        Calendar startCal = getCalendar();
        Calendar endCal = getCalendar();
        srcCal.setTime(getStrToDate(src, format));
        startCal.setTime(getStrToDate(start, format));
        endCal.setTime(getStrToDate(end, format));
        if (srcCal.after(startCal) && srcCal.before(endCal)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 说明：d1与d2比较
     *
     * @param d1     时间1
     * @param d2     时间2
     * @param format 时间格式
     * @return 【当d1小于d2 返回小于0，当d2大于d1 返回大于0，当d1等于d2 返回0】
     */
    public static int compareTo(String d1, String d2, String format) {
        Calendar d1Cal = getCalendar();
        Calendar d2Cal = getCalendar();
        d1Cal.setTime(getStrToDate(d1, format));
        d2Cal.setTime(getStrToDate(d2, format));
        return d1Cal.compareTo(d2Cal);
    }

    public final static int oneD = 24 * 3600;
    public final static int oneH = 3600;
    public final static int oneM = 60;
    public final static String timeFormatStr = "yyyy年MM月dd日";
    public final static String timeFormatMsg = "yyyy-MM-dd HH:mm";
    public final static String timeFormat = "yyyy-MM-dd HH:mm:ss";

    public static String getTime() {
        return SimpleDateFormat.getInstance().format(new Date());
    }

    public static String getTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    public static String dateToTimeStamp(String date) {
        long time = 0;
        try {
            time = new SimpleDateFormat(timeFormatStr).parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(time / 1000);
    }

    public static String timeStampToDate(String timeStamp) {
        String date = new SimpleDateFormat(timeFormatStr).format(new Date(Long.valueOf(timeStamp) * 1000));
        return date;
    }

    public static String getDataTime(String dataStr) {
        if (TextUtils.isEmpty(dataStr)) {
            return "";
        }
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(timeFormat);
            Date date = sdf1.parse(dataStr);
            return new SimpleDateFormat(timeFormatMsg).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 格式化时间
     *
     * @param timeMillis 时间毫秒值
     * @param format     时间格式
     * @return
     */
    public static String formatTime(long timeMillis, String format) {
        return new SimpleDateFormat(format).format(new Date(timeMillis));
    }

    /**
     * add by zhangmao 用于首页倒计时格式化
     *
     * @param durationInSeconds 时长（秒）
     * @return 格式："-天 --：--：--"
     */
    public static String formatDuration(int durationInSeconds) {
        int d = durationInSeconds / oneD;
        int h = (durationInSeconds - d * oneD) / oneH;
        int m = (durationInSeconds - d * oneD - h * oneH) / oneM;
        int s = (durationInSeconds - d * oneD - h * oneH - m * oneM);
        StringBuilder builder = new StringBuilder();
        builder.append(d == 0 ? "" : d + "天 ");
        builder.append(h < 10 ? "0" + h : "" + h);
        builder.append(":");
        builder.append(m < 10 ? "0" + m : "" + m);
        builder.append(":");
        builder.append(s < 10 ? "0" + s : "" + s);
        return builder.toString();
    }

    /**
     * add by zhangmao 用于美购首页秒杀倒计时
     *
     * @param durationInSeconds 时长（秒）
     * @return 格式："--：--：--"
     */
    public static int[] getCountdownTime(int durationInSeconds) {
        int h = durationInSeconds / oneH;
        int m = (durationInSeconds - h * oneH) / oneM;
        int s = (durationInSeconds - h * oneH - m * oneM);
        // LogUtil.d("h = " + h + ", m = " + m + ", s = " + s);

        int[] timeData = new int[6];
        if (h >= 100) {// 小时超出2位数时的容错处理
            LogUtils.e("hour is bigger than 100!");
            h %= 100;
        }
        timeData[0] = h / 10;
        timeData[1] = h % 10;
        timeData[2] = m / 10;
        timeData[3] = m % 10;
        timeData[4] = s / 10;
        timeData[5] = s % 10;
        // LogUtil.d("timeData = " + timeData[0] + timeData[1] + ":" + timeData[2] + timeData[3] + ":" + timeData[4] + timeData[5]);

        return timeData;
    }

    /**
     * 获取一天开始的时间（即凌晨）
     */
    public static long getTheDayBegin(long timeMillis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(timeMillis);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 两个时间是不是相隔在一天之内
     *
     * @param currentTime
     * @param preTime
     * @return
     */
    public static boolean isOneDayApartOfMillis(String currentTime, String preTime) {
        if (TextUtils.isEmpty(currentTime)) {
            currentTime = "0";
        }
        if (TextUtils.isEmpty(preTime)) {
            preTime = "0";
        }
        long currentTimeLong = Long.valueOf(currentTime);
        long preTimeLong = Long.valueOf(preTime);
        final long interval = currentTimeLong - preTimeLong;
        return interval < MILLIS_IN_DAY;
    }
}
