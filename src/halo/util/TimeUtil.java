package halo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    public static final String SYS_DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 只保留日期数据，时间数据为0
     * 
     * @param date
     * @return
     */
    public static Date getBeginDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获得某个日期的结束时间 HH:mm:ss:ms 为23:59:59:0
     * 
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static boolean isSameDay(Date a, Date b) {
        Calendar cal_a = Calendar.getInstance();
        Calendar cal_b = Calendar.getInstance();
        cal_a.setTime(a);
        cal_b.setTime(b);
        if (cal_a.get(Calendar.DATE) == cal_b.get(Calendar.DATE)
                && cal_a.get(Calendar.MONTH) == cal_b.get(Calendar.MONTH)
                && cal_a.get(Calendar.YEAR) == cal_b.get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }

    public static String getFmtTime(Date date) {
        long v = System.currentTimeMillis() - date.getTime();
        long sec = v / 1000;
        long min = sec / 60;
        long h = min / 60;
        if (sec == 0) {
            return "1秒前";
        }
        StringBuilder sb = new StringBuilder();
        if (h > 0) {
            if (h < 24) {
                sb.append(h).append("小时前");
                return sb.toString();
            }
            long day = h / 24;
            if (day < 30 && day > 0) {
                sb.append(day).append("天前");
                return sb.toString();
            }
            long month = day / 30;
            if (month < 12 && month > 0) {
                sb.append(month).append("个月前");
                return sb.toString();
            }
            sb.append(month / 12).append("年前");
            return sb.toString();
        }
        if (min > 0) {
            sb.append(min).append("分前");
            return sb.toString();
        }
        sb.append(sec).append("秒前");
        return sb.toString();
    }

    public static Date createNoMillisecondTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date parseTime(String v, String pattern) {
        if (DataUtil.isEmpty(v)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(v);
        }
        catch (ParseException e) {
            return null;
        }
    }

    public static String getFormatTimeString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getFormatTimeString(Date date, String format,
            Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(date);
    }
}
