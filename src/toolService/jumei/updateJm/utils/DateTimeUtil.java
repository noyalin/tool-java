package toolService.jumei.updateJm.utils;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by simin on 2017/10/25.
 */
public final class DateTimeUtil {
    private static Log log = LogFactory.getLog(DateTimeUtil.class);

    /**
     * 缺省的日期显示格式： yyyy-MM-dd
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 缺省时间显示格式：HH:mm:ss
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 缺省的日期显示格式： MM/dd/yyyy
     */
    public static final String US_DATE_FORMAT = "MM/dd/yyyy HH:mm:ss";

    /**
     * 缺省的日期显示格式： MM/dd/yyyy
     */
    public static final String EN_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    /**
     * 缺省的日期显示格式：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 缺省的日期时间显示格式：yyyyMMddHHmmss
     */
    public static final String DATE_TIME_FORMAT_2 = "yyyyMMddHHmmss";

    /**
     * 缺省的日期时间显示格式：yyyyMMdd
     */
    public static final String DATE_TIME_FORMAT_3 = "yyyyMMdd";

    /**
     * 缺省的日期时间显示格式：HHmmss
     */
    public static final String DATE_TIME_FORMAT_4 = "HHmmss";

    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd'T'HH:mm:ss（参照例：2014-11-11T14:00:00）
     */
    public static final String DATE_TIME_FORMAT_5 = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * 缺省的日期时间显示格式：yyyy-MM-dd'T'HH:mm:ssZ（参照例：2014-11-11T14:00:00+0800）
     */
    public static final String DATE_TIME_FORMAT_6 = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * 缺省的日期时间显示格式：MMddyyyy
     */
    public static final String DATE_TIME_FORMAT_7 = "MMddyyyy";

    /**
     * 缺省的日期时间显示格式：MMdd
     */
    public static final String DATE_TIME_FORMAT_8 = "MMdd";

    /**
     * 缺省的日期时间显示格式：MMddyy
     */
    public static final String DATE_TIME_FORMAT_9 = "MMddyy";
    /**
     * 缺省的日期时间显示格式：MMddyy
     */
    public static final String DATE_TIME_FORMAT_10 = "yyMMdd";
    /**
     * 私有构造方法，禁止对该类进行实例化
     */
    private DateTimeUtil() {
    }

    /**
     * 得到系统当前日期时间
     *
     * @return 当前日期时间
     */
    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    /**
     * 得到用缺省方式格式化的当前日期及时间
     *
     * @return 当前日期及时间
     */
    public static String getNow() {
        return getNow(DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 得到系统当前日期及时间，并用指定的方式格式化
     *
     * @param pattern 显示格式
     * @return 当前日期及时间
     */
    public static String getNow(String pattern) {
        return format(getDate(), pattern);
    }

    /**
     * 得到用指定方式格式化的日期
     *
     * @param date    需要进行格式化的日期
     * @param pattern 显示格式
     * @return 日期时间字符串
     */
    public static String format(Date date, String pattern) {
        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 将一个字符串用给定的格式转换为字符串类型。 <br>
     * 注意：如果返回null，则表示解析失败
     *
     * @param dateSting 需要解析的日期字符串
     * @param pattern   日期字符串的格式，默认为“yyyy-MM-dd”的形式
     * @return 解析后的日期
     */
    public static String parseStr(String dateSting, String pattern) {
        if (dateSting == null || dateSting.trim().length() == 0) {
            return null;
        }

        if (null == pattern || "".equals(pattern)) {
            pattern = DEFAULT_DATE_FORMAT;
        }

        if (dateSting.length() == 10) {
            dateSting = dateSting + " 00:00:00";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(parse(dateSting, DEFAULT_DATETIME_FORMAT));
    }

    /**
     * 得到当前年份
     *
     * @return 当前年份
     */
    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 得到当前月份
     *
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        // 用get得到的月份数比实际的小1，需要加上
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 得到当前日
     *
     * @return 当前日
     */
    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * 得到当前分钟
     *
     * @return 当前分钟
     */
    public static int getCurrentMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    /**
     * 得到当前小时
     *
     * @return 当前小时
     */
    public static int getCurrentHour() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

    }

    /**
     * 得到当前秒
     *
     * @return 当前秒
     */
    public static int getCurrentSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);

    }

    /**
     * 得到指定日期年份
     *
     * @return 年份
     */
    public static int getDateYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到指定日期月份
     *
     * @return 月份
     */
    public static int getDateMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 得到指定日期日
     *
     * @return 日
     */
    public static int getDateDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 得到指定分钟
     *
     * @return 指定分钟
     */
    public static int getDateMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 得到指定小时
     *
     * @return 指定小时
     */
    public static int getDateHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);

    }

    /**
     * 得到指定秒
     *
     * @return 指定秒
     */
    public static int getDateSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);

    }

    /**
     * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。 例如要得到上星期同一天的日期，参数则为-7
     *
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(int days) {
        return add(getDate(), days, Calendar.DATE);
    }

    /**
     * 取得指定日期以后若干天的日期。如果要得到以前的日期，参数用负数。
     *
     * @param date 基准日期
     * @param days 增加的日期数
     * @return 增加以后的日期
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * 取得当前日期以后某月的日期。如果要得到以前月份的日期，参数用负数。
     *
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(int months) {
        return add(getDate(), months, Calendar.MONTH);
    }

    /**
     * 取得指定日期以后某月的日期。如果要得到以前月份的日期，参数用负数。 注意，可能不是同一日子，例如2003-1-31加上一个月是2003-2-28
     *
     * @param date   基准日期
     * @param months 增加的月份数
     * @return 增加以后的日期
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }

    /**
     * 取得当前日期以后某年的日期。如果要得到以前月份的日期，参数用负数。
     */
    public static Date addYears(int years) {
        return add(getDate(), years, Calendar.YEAR);
    }

    /**
     * 取得指定日期以后某年的日期。如果要得到以前月份的日期，参数用负数。
     *
     * @param date  基准日期
     * @param years 增加的月份数
     */
    public static Date addYears(Date date, int years) {
        return add(date, years, Calendar.YEAR);
    }

    /**
     * 内部方法。为指定日期增加相应的天数或月数
     *
     * @param date   基准日期
     * @param amount 增加的数量
     * @param field  增加的单位，年，月或者日
     * @return 增加以后的日期
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(field, amount);

        return calendar.getTime();
    }

    /**
     * 计算两个日期相差天数。 用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差天数
     */
    public static long diffDays(Date one, Date two) {
        return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
    }

    /**
     * 计算两个日期相差月份数 如果前一个日期小于后一个日期，则返回负数
     *
     * @param one 第一个日期数，作为基准
     * @param two 第二个日期数，作为比较
     * @return 两个日期相差月份数
     */
    public static int diffMonths(Date one, Date two) {

        Calendar calendar = Calendar.getInstance();

        // 得到第一个日期的年分和月份数
        calendar.setTime(one);
        int yearOne = calendar.get(Calendar.YEAR);
        int monthOne = calendar.get(Calendar.MONDAY);

        // 得到第二个日期的年份和月份
        calendar.setTime(two);
        int yearTwo = calendar.get(Calendar.YEAR);
        int monthTwo = calendar.get(Calendar.MONDAY);

        return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
    }

    /**
     * 计算两个日期的小时差
     * @param one
     * @param two
     * @return 两个日期相差的小时数
     */
    public static long diffHours(Date one, Date two){
        return (one.getTime() - two.getTime()) / (60 * 60 * 1000);
    }

    /**
     * 计算两个日期的小时差
     * @param one
     * @param two
     * @return 两个日期相差的小时数
     */
    public static long diffMinutes(Date one, Date two){
        return (one.getTime() - two.getTime()) / (60 * 1000);
    }

    /**
     * 将一个字符串用默认为“yyyy-MM-dd HH:mm:ss”的形式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     *
     * @param dateStr 需要解析的日期字符串
     * @return 解析后的日期
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     *
     * @param dateStr 需要解析的日期字符串
     * @param pattern 日期字符串的格式
     * @return 解析后的日期
     */
    public static Date parse(String dateStr, String pattern) {
        Date date = null;

        if (dateStr == null || dateStr.trim().length() == 0) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.debug("日期转换错误", e);
        }

        return date;
    }

    /**
     * 将一个字符串用给定的格式转换为日期类型。 <br>
     * 注意：如果返回null，则表示解析失败
     *
     * @param dateStr 需要解析的日期字符串
     * @param pattern 日期字符串的格式
     * @return 解析后的日期
     */
    public static Date parseToGmt(String dateStr, String pattern) {
        Date date = null;

        if (dateStr == null || dateStr.trim().length() == 0) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            log.debug("日期转换错误", e);
        }

        return date;
    }

    /**
     * 返回本月的最后一天
     *
     * @return 本月最后一天的日期
     */
    public static Date getMonthLastDay() {
        return getMonthLastDay(getDate());
    }

    /**
     * 返回给定日期中的月份中的最后一天
     *
     * @param date 基准日期
     * @return 该月最后一天的日期
     */
    public static Date getMonthLastDay(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 将日期设置为下一月第一天
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);

        // 减去1天，得到的即本月的最后一天
        calendar.add(Calendar.DATE, -1);

        return calendar.getTime();
    }

    /**
     * 格式化java.sql.Timestamp 对象成字符串
     */
    public static String parseSqlTimeStamp(Object obj) {
        if (obj instanceof java.sql.Date) {
            String result = obj.toString().trim();
            if (result.length() == 10) {
                result = result + " 00:00:00";
            }
            obj = Timestamp.valueOf(result);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        return dateFormat.format((Timestamp) obj);
    }

    /**
     * 格式化java.sql.Time 对象成字符串
     */
    public static String parseSqlTime(Object obj) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.DEFAULT_TIME_FORMAT);
        return dateFormat.format((java.sql.Time) obj);
    }

    /**
     * 格式化java.sql.Date对象成字符串
     */
    public static String parseSqlDate(Object obj) {
        if (obj instanceof Timestamp) {
            String result = obj.toString().trim();
            if (result.length() > 10) {
                result = result.substring(0, 10);
            }
            obj = java.sql.Date.valueOf(result);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATE_FORMAT);
        return dateFormat.format((java.sql.Date) obj);
    }

    /**
     * 格式化 日期串
     * <p/>
     * type ==1 时 “2005年10月1日”
     * <p/>
     * type ==2 时 "二〇〇五年十月一日"
     */
    public static String format(String dateStr, int type) throws Exception {

        if (dateStr == null || dateStr.trim().length() == 0) {
            return "";
        }

        Date date = DateTimeUtil.parse(dateStr.trim().substring(0, 10), "yyyy-MM-dd");
        if (type == 1) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
            return df.format(date);

        } else if (type == 2) {

            String year = String.valueOf(DateTimeUtil.getDateYear(date));
            String month = String.valueOf(DateTimeUtil.getDateMonth(date));
            String day = String.valueOf(DateTimeUtil.getDateDay(date));

            StringBuilder str = new StringBuilder("");
            str.append(DateTimeUtil.getUpStr(Integer.parseInt(year.substring(0, 1))));
            str.append(DateTimeUtil.getUpStr(Integer.parseInt(year.substring(1, 2))));
            str.append(DateTimeUtil.getUpStr(Integer.parseInt(year.substring(2, 3))));
            str.append(DateTimeUtil.getUpStr(Integer.parseInt(year.substring(3, 4))));
            str.append("年");
            if (month.length() > 1) {
                if (Integer.parseInt(month) < 20) {
                    str.append("十");
                } else {
                    str.append(DateTimeUtil.getUpStr(Integer.parseInt(month.substring(0, 1))));
                }
                str.append(DateTimeUtil.getUpStr(Integer.parseInt(month.substring(1, 2))));
            } else {
                str.append(DateTimeUtil.getUpStr(Integer.parseInt(month.substring(0, 1))));
            }

            str.append("月");
            if (day.length() > 1) {
                if (Integer.parseInt(day) < 20) {
                    str.append("十");
                } else {
                    str.append(DateTimeUtil.getUpStr(Integer.parseInt(day.substring(0, 1))));
                }
                str.append(DateTimeUtil.getUpStr(Integer.parseInt(day.substring(1, 2))));
            } else {
                str.append(DateTimeUtil.getUpStr(Integer.parseInt(day.substring(0, 1))));
            }

            str.append("日");
            return str.toString();
        }

        return "";
    }

    /**
     * 获取当前月上月的最后一天
     */
    public static Date getPreMonthLastDay() {
        return DateTimeUtil.getMonthLastDay(DateTimeUtil.addMonths(-1));
    }

    /**
     * 得到当前为星期几,1-星期日,2-星期一....,7-星期六;
     *
     * @return int 得到当前为星期几,1-星期日,2-星期一....,7-星期六;
     */
    public static int getCurrentWeek() {
        Date objDate = new Date();
        Calendar objCalendarDate = Calendar.getInstance();
        objCalendarDate.setTime(objDate);
        return objCalendarDate.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据日期返回是星期几
     *
     * @param day 1-星期日,2-星期一....,7-星期六;
     * @return 星期日, 星期一...., 星期六;
     */
    public static String getWeekDay(int day) {
        String week;
        switch (day) {
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
            default:
                week = "星期日";
                break;
        }
        return week;
    }

    /**
     * 根据数字返回大写 文本
     */
    public static String getUpStr(int int_num) {
        String up_str;
        switch (int_num) {
            case 0:
                up_str = "〇";
                break;
            case 1:
                up_str = "一";
                break;
            case 2:
                up_str = "二";
                break;
            case 3:
                up_str = "三";
                break;
            case 4:
                up_str = "四";
                break;
            case 5:
                up_str = "五";
                break;
            case 6:
                up_str = "六";
                break;
            case 7:
                up_str = "七";
                break;
            case 8:
                up_str = "八";
                break;
            case 9:
                up_str = "九";
                break;
            default:
                up_str = "〇";
                break;
        }
        return up_str;
    }

    /**
     * 判断时间date1是否在时间date2之前,时间格式 2005-4-21 16:16:34
     */
    public static boolean isDateBefore(String date1, String date2) {
        try {
            DateFormat df = DateFormat.getDateTimeInstance();
            return df.parse(date1).before(df.parse(date2));
        } catch (ParseException e) {
            System.out.print("[SYS] " + e.getMessage());
            return false;
        }
    }

    /**
     * 日期文字列有效性检查
     */
    public static boolean isDate(String date, String pattern) {
        boolean ret = false;
        Date tmp = null;
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);//这个的功能是不把1996-13-3 转换为1997-1-3
        try
        {
            tmp  =  df.parse(date);
            ret = true;
        } catch(Exception  e) {

        }

        return ret;
    }


    /**
     * 取得指定日期以后某年的日期。如果要得到以前月份的日期，参数用负数。
     *
     * @param date  基准日期
     * @param hours 增加的时间数
     */
    public static Date addHours(Date date, int hours) {
        return add(date, hours, Calendar.HOUR_OF_DAY);
    }

    /**
     * 日期字段串转化为 Calendar
     */
    public static Calendar parseDateString(String datePara) {
        Calendar c = Calendar.getInstance();
        try {
            DateFormat df = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
            Date date = df.parse(datePara);
            c.setTime(date);

            return c;
        } catch (ParseException e) {
            log.error("DateTimeUtil.parseDateString ", e);
            return null;
        }
    }

    public static String parseDateStr(String before, String beforeFormat, String afterFormat) {

        SimpleDateFormat format = new SimpleDateFormat(beforeFormat);
        Date date;
        try {
            date = format.parse(before);
        } catch (ParseException e) {
            log.error("DateTimeUtil.parseDateString ", e);
            return null;
        }

        format = new SimpleDateFormat(afterFormat);
        return format.format(date);
    }

    /**
     * 根据时区，返回基于GMT时间的本地时间，并格式化
     *
     * @param date     GMT时间
     * @param timezone 时区
     * @return yyyy-MM-dd HH:mm:ss 格式的本地时间
     */
    public static String getLocalTime(String date, int timezone) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getLocalTime :date format error");
        }
    }

    /**
     * 根据时区，返回基于GMT时间的本地时间，并格式化
     *
     * @param date     GMT时间
     * @param timezone 时区
     * @return yyyy-MM-dd HH:mm:ss 格式的本地时间
     */
    public static String getLocalTime(Date date, int timezone) {
        return getLocalTime(date, timezone, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 根据时区，返回基于GMT时间的本地时间，并格式化
     *
     * @param date     GMT时间
     * @param timezone 时区
     * @param format   时间日期格式
     * @return 格式化后的本地时间
     */
    public static String getLocalTime(Date date, int timezone, String format) {
        Date toDate = DateTimeUtil.addHours(date, timezone);
        return format(toDate, format);
    }

    /**
     * 根据时区，返回本地时间，并格式化
     *
     * @param timezone 时区
     * @return yyyy-MM-dd HH:mm:ss 格式的本地时间
     */
    public static String getLocalTime(int timezone) {
        return getLocalTime(timezone, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 根据时区，返回基于GMT时间的本地时间，并格式化
     *
     * @param timezone 时区
     * @param format   时间日期格式
     * @return 格式化后的本地时间
     */
    public static String getLocalTime(int timezone, String format) {
        try {
            String date = getGMTTime();
            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone);

            return format(toDate, format);
        } catch (ParseException e) {
            return ("getLocalTime :date format error");
        }
    }

    /**
     * 根据时区，返回基于本地时间的GMT时间，并格式化
     *
     * @param date     本地时间
     * @param timezone 时区
     * @return yyyy-MM-dd HH:mm:ss 格式的GMT时间
     */
    public static String getGMTTime(String date, int timezone) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, (timezone * -1));

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTime :date format error");
        }
    }

    /**
     * 根据时区，返回基于本地时间的GMT时间，并格式化
     *
     * @param date     本地时间
     * @param timezone 时区
     * @return yyyy-MM-dd HH:mm:ss 格式的GMT时间
     */
    public static String getGMTTime(Date date, int timezone) {
        return getGMTTime(date, timezone, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 根据时区，返回基于本地时间的GMT时间，并格式化
     *
     * @param date     本地时间
     * @param timezone 时区
     * @param format   时间日期格式
     * @return 格式化后的GMT时间
     */
    public static String getGMTTime(Date date, int timezone, String format) {
        Date toDate = DateTimeUtil.addHours(date, (timezone * -1));
        return format(toDate, format);
    }

    /**
     * 返回基于系统时间的GMT时间，并格式化
     *
     * @return yyyy-MM-dd HH:mm:ss 格式的GMT时间
     */
    public static String getGMTTime() {
        return getGMTTime(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 返回基于系统时间的GMT时间，并格式化
     *
     * @param format   时间日期格式
     * @return 格式化后的GMT时间
     */
    public static String getGMTTime(String format) {

        // 取得系统当前时间
        Calendar now = Calendar.getInstance();
        // 取得系统时间和格林威治时间之间的偏移值
        int diffsecond = now.getTimeZone().getRawOffset();
        // 换算成格林威治时间
        now.add(Calendar.SECOND, diffsecond / 1000 * -1);
        Date dateTo = now.getTime();

        return format(dateTo, format);
    }

    /**
     * 返回基于系统时间的GMT时间
     * @return Calendar GMT的Calendar
     */
    public static Calendar getCustomCalendar(int timezone){
        // 取得系统当前时间
        Calendar now = Calendar.getInstance();
        // 取得系统时间和格林威治时间之间的偏移值
        int diffsecond = now.getTimeZone().getRawOffset();
        // 换算成格林威治时间
        now.add(Calendar.SECOND, diffsecond / 1000 * -1);
        now.add(Calendar.SECOND, timezone * 3600 );
        return now;
    }

    /**
     * 取得本地时间对应的GMT时间(一天的开始)
     *
     * @param day      格式 yyyy-MM-dd
     * @param timezone like +8 / -7
     * @return String
     */
    public static String getGMTTimeFrom(String day, int timezone) {
        try {
            String date = day + " 00:00:00";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeTo :date format error");
        }
    }

    /**
     * 取得本地时间对应的GMT时间(一天的结束)
     *
     * @param day      格式 yyyy-MM-dd
     * @param timezone like +8 / -7
     * @return String
     */
    public static String getGMTTimeTo(String day, int timezone) {
        try {
            String date = day + " 23:59:59";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeTo :date format error");
        }
    }

    /**
     * 取得本地时间对应的GMT时间(一天的开始)
     *
     * @param timezone like +8 / -7
     * @return String
     */
    public static String getGMTTimeFrom(int timezone) {
        try {
            String date = getLocalTime(timezone, DateTimeUtil.DEFAULT_DATE_FORMAT) + " 00:00:00";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeTo :date format error");
        }
    }

    /**
     * 取得本地时间对应的GMT时间(一天的结束)
     *
     * @param timezone like +8 / -7
     * @return String
     */
    public static String getGMTTimeTo(int timezone) {
        try {
            String date = getLocalTime(timezone, DateTimeUtil.DEFAULT_DATE_FORMAT) + " 23:59:59";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeTo :date format error");
        }
    }

    /**
     * 取得本地时间与天数相加后对应的GMT时间(开始)
     *
     * @param timezone like +8 / -7
     * @param days
     * @return String
     */
    public static String getGMTTimeFrom(int timezone,int days) {
        try {
            String date = getLocalTime(timezone, DateTimeUtil.DEFAULT_DATE_FORMAT) + " 00:00:00";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addDays(fromDate, days);
            toDate = DateTimeUtil.addHours(toDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeFrom :date format error");
        }
    }

    /**
     * 取得本地时间与天数相加后对应的GMT时间(结束)
     *
     * @param timezone like +8 / -7
     * @param days
     * @return String
     */
    public static String getGMTTimeTo(int timezone,int days) {
        try {
            String date = getLocalTime(timezone, DateTimeUtil.DEFAULT_DATE_FORMAT) + " 23:59:59";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addDays(fromDate, days);
            toDate = DateTimeUtil.addHours(toDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeTo :date format error");
        }
    }

    /***
     * 取得指定天数之前的日期
     * @param intDays
     * @return 返回的日期
     */
    public static String getDateBeforeDays(int intDays) {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat simple = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

        // 取得指定天数之前的日期
        currentDate.add(Calendar.HOUR, -(24 * intDays));
        return simple.format(currentDate.getTime());
    }

    /**
     * 取得指定时间以后多少分钟的时间。如果要得到以前多少分钟的时间，参数用负数。
     *
     * @param date  基准日期
     * @param minutes 增加的时间数
     */
    public static Date addMinutes(Date date, int minutes) {
        return add(date, minutes, Calendar.MINUTE);
    }

    /**
     * 获得两个日期时间间隔（flag：0-3以外默认为0）
     *
     * flag 0:天数   1:小时   2:分钟   3:秒
     *
     * @return
     */
    public static long getInterVal(String startDateTime, String endDateTime, int flag) throws ParseException{
        long intervale = 0;

        SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        Date begin = df.parse(startDateTime);
        Date end = df.parse(endDateTime);

        // 除以1000是为了转换成秒
        long between = (end.getTime() - begin.getTime()) / 1000;

        if (flag != 0 && flag != 1 && flag != 2 && flag != 3) {
            flag = 0;
        }

        // 天数
        if (flag == 0) {
            long day = between / (24 * 3600);
            intervale = day;

            // 小时
        } else if (flag == 1) {
            long hour = between / 3600;
            intervale = hour;

            // 分钟
        } else if (flag == 2) {
            long minute = between / 60;
            intervale = minute;

            // 秒
        } else if (flag == 3) {
            long second = between;
            intervale = second;
        }

        return intervale;
    }

    /**
     * 美国时间变化
     *
     */
    public static String changeDefautlDateTimeToUSFormat(String orderDateTime) {
        Date a = DateTimeUtil.parse(orderDateTime, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        String ret = DateTimeUtil.format(a, DateTimeUtil.US_DATE_FORMAT);

        return ret;
    }

    /**
     * 美国时间变化
     *
     */
    public static String changeDefautlDateTimeToENFormat(String orderDateTime) {
        Date a = DateTimeUtil.parse(orderDateTime, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        String  ret = DateTimeUtil.format(a, DateTimeUtil.EN_DATE_FORMAT);

        return ret;
    }

    /**
     * 英国的时间 dd/MM/yyyy HH:mm:ss 转换成 yyyy-MM-dd HH:mm:ss
     *
     */
    public static String changeENDateTimeToDefault(String orderDateTime) {
        Date a = DateTimeUtil.parse(orderDateTime, DateTimeUtil.EN_DATE_FORMAT);
        return DateTimeUtil.format(a, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * UNIXTIME转换成yyyy-MM-dd HH:mm:ss格式
     *
     * @param unixTime
     * @return
     */
    public static String getDateTimeFromUnixTime(String unixTime) {
        long unixTimeL = 0;
        if (!StringUtils.isNullOrBlank2(unixTime)) {
            unixTimeL = Long.parseLong(unixTime);
        }
        unixTimeL = unixTimeL * 1000;

        SimpleDateFormat sf = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
        String dateTime = sf.format(new Date(unixTimeL));

        return dateTime;
    }

    /**
     * 根据Date型dateTime获得pattern格式的字符串日期
     *
     * @param dateTime
     * @param pattern
     * @return
     */
    public static String getDateTime(Date dateTime, String pattern) {
        if (StringUtils.isNullOrBlank2(pattern)) {
            pattern = DEFAULT_DATETIME_FORMAT;
        }

        SimpleDateFormat sf = new SimpleDateFormat(pattern);
        return sf.format(dateTime);
    }

    /**
     * @description 将时间字符串转化成XMLGregorianCalendar
     * @param dateTimeStr 时间字符串
     * @return XMLGregorianCalendar
     */
    public static XMLGregorianCalendar strDateTimeTOXMLGregorianCalendar(String dateTimeStr){
        Date dateTimeDate;
        try {
            dateTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTimeStr);
            GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(dateTimeDate);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
        } catch (Exception e) {
            throw new RuntimeException("时间字符串【" + dateTimeStr + "】转化成XMLGregorianCalendar出现错误：" + e);
        }
    }

    /**
     * @description 将XMLGregorianCalendar转化成时间字符串
     * @param cal XMLGregorianCalendar
     * @return 时间字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String XMLGregorianCalendarToDate(XMLGregorianCalendar cal) throws Exception{
        GregorianCalendar ca = cal.toGregorianCalendar();
        return DateTimeUtil.format(ca.getTime(), DateTimeUtil.DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 得到当前时间TimeStamp
     *
     * @return 当前日期及时间
     */
    public static String getNowTimeStamp() {
        return new Timestamp(System.currentTimeMillis()).toString();
    }


    /**
     * 取得本地时间对应的GMT时间(一天的开始)SP第三方仓库发货日报用
     *
     * @param day      格式 yyyy-MM-dd
     * @param timezone like +8 / -7
     * @return String
     */
    public static String getGMTTimeSPFrom(String day, int timezone, String hours) {
        try {
            String date = day + " " + hours + ":00:00";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeTo :date format error");
        }
    }

    /**
     * 取得本地时间对应的GMT时间(一天的结束)SP第三方仓库发货日报用
     *
     * @param day      格式 yyyy-MM-dd
     * @param timezone like +8 / -7
     * @return String
     */
    public static String getGMTTimeSPTo(String day, int timezone, String hours) {
        try {
            String date = day + " " + hours + ":59:59";

            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addHours(fromDate, timezone * -1);

            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeTo :date format error");
        }
    }

    /**
     * 得到当前时间TimeStamp
     *
     * @return 当前日期及时间
     */
    public static long getNowTimeStampLong() {
        return System.currentTimeMillis();
    }


    public static Long getTime(String user_time,String dateFormat,int timezone) throws Exception {
        String re_time = null;
        Calendar cal = Calendar.getInstance();

        TimeZone locTimeZone = cal.getTimeZone();
        int diffsecond = locTimeZone.getRawOffset()/1000/3600;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date d;


        d = sdf.parse(user_time);
        long l = d.getTime()/1000-(timezone-diffsecond)*3600;

        return l;
    }

    public static XMLGregorianCalendar calendarToXML(Calendar calendar) {
        XMLGregorianCalendar cal = new XMLGregorianCalendarImpl();
        cal.setYear(calendar.get(Calendar.YEAR));
        cal.setMonth(calendar.get(Calendar.MONTH) + 1);
        cal.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        cal.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        cal.setMinute(calendar.get(Calendar.MINUTE));
        cal.setSecond(calendar.get(Calendar.SECOND));
        cal.setMillisecond(calendar.get(Calendar.MILLISECOND));
        cal.setTimezone(calendar.get(Calendar.ZONE_OFFSET) / 60000);
        return cal;
    }

    /**
     * 获取更改时区后的日期
     *
     * @param date    日期
     * @param zone    新时区对象
     * @return 日期
     */
    public static Date changeTimeZone(Date date, TimeZone zone) {
        return changeTimeZone(date, TimeZone.getDefault(), zone);
    }

    /**
     * 获取更改时区后的日期
     *
     * @param date    日期
     * @param oldZone 旧时区对象
     * @param newZone 新时区对象
     * @return 日期
     */
    public static Date changeTimeZone(Date date, TimeZone oldZone, TimeZone newZone) {
        Date dateTmp = null;
        if (date != null) {
            int timeOffset = oldZone.getRawOffset() - newZone.getRawOffset();
            dateTmp = new Date(date.getTime() - timeOffset);
        }
        return dateTmp;
    }


    /**
     * 取得本地时间对应的GMT时间(一天的开始)Wella出库记录用
     *
     * @param date      格式 yyyy-MM-dd HH:mm:ss
     * @param timezone like +8 / -7
     * @return String
     */
    public static String getGMTTimeFromByMinutes(String date, int timezone) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(DateTimeUtil.DEFAULT_DATETIME_FORMAT);
            Date fromDate = df.parse(date);

            Date toDate = DateTimeUtil.addMinutes(fromDate, timezone);

            //add(getDate(), fromDate, Calendar.DATE);
            return format(toDate, DateTimeUtil.DEFAULT_DATETIME_FORMAT);
        } catch (ParseException e) {
            return ("getGMTTimeFromMinutes :date format error");
        }
    }

}
