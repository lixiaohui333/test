package com.meeting.client.comm.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTool {

    public static final int MINUTE = 60;
    public static final int HOUR = 60 * 60;
    public static final int DAY = 24 * 60 * 60;

    private static int simple_now_date = 0;

    public static SimpleDateFormat sdf_msg_sss = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.sss");

    public static SimpleDateFormat sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static SimpleDateFormat sdf_mm = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    public static SimpleDateFormat sdf_msg_HH = new SimpleDateFormat("HH");

    public static SimpleDateFormat sdf_msg_hh_mm = new SimpleDateFormat("hh:mm");

    public static SimpleDateFormat sdf_msg_yesterday_hh_mm = new SimpleDateFormat(
            "HH:mm");

    public static SimpleDateFormat sdf_msg = new SimpleDateFormat("MM-dd HH:mm");

    public static SimpleDateFormat sdf_name = new SimpleDateFormat(
            "yyyyMMdd_HHmmss");

    public static SimpleDateFormat sdf_simple_name = new SimpleDateFormat(
            "yyyyMMdd");


    public static SimpleDateFormat sdf_ymd = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static SimpleDateFormat sdf_ymd_point = new SimpleDateFormat(
            "yyyy.MM.dd");

    public static SimpleDateFormat sdf_simple_name_cn = new SimpleDateFormat(
            "yyyy年MM月dd日");


    public static SimpleDateFormat sdf_simple_name_ym_cn = new SimpleDateFormat(
            "yyyy年MM月");

    public static SimpleDateFormat sdf_simple_y = new SimpleDateFormat(
            "yyyy");
    public static SimpleDateFormat sdf_simple_m = new SimpleDateFormat(
            "MM");
    public static SimpleDateFormat sdf_simple_d = new SimpleDateFormat(
            "dd");
    public static SimpleDateFormat sdf_HHmmss = new SimpleDateFormat(
            "HHmmss");

    public static Calendar StringConvertCalendar(String time) {
        try {

            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSukangTime(long time) {

        if (time <= 0) {
            return "";
        }

        String temp_date = sdf_simple_name.format(new Date(time));

        int simple_date = Integer.parseInt(temp_date);

        if (simple_now_date <= 0) {
            String temp_now_date = sdf_simple_name.format(new Date(System
                    .currentTimeMillis()));
            simple_now_date = Integer.parseInt(temp_now_date);
        }

        int offset = simple_now_date - simple_date;

        if (offset == 0) {

            String temp_hh_date = sdf_msg_HH.format(new Date(time));

            int simple_hh_date = Integer.parseInt(temp_hh_date);
            if (simple_hh_date > 12) {

                return "下午 " + sdf_msg_hh_mm.format(new Date(time));
            } else {
                return "上午 " + sdf_msg_hh_mm.format(new Date(time));
            }
        } else if (offset == 1) {
            return "昨天 " + sdf_msg_yesterday_hh_mm.format(new Date(time));
        } else {
            return sdf_msg.format(new Date(time));
        }

    }

    public static String getAllTime(long time) {
        return sdf.format(new Date(time));
    }

    public static String getTimeName(long time) {
        return sdf_name.format(new Date(time));
    }

    public static String getTimeYMD(long time) {
        return sdf_simple_name.format(new Date(time));
    }

    public static String getTimeYMDPoint(long time) {
        return sdf_ymd_point.format(new Date(time));
    }

    public static String getDilaogYMD(long time) {
        return sdf_ymd.format(new Date(time));
    }

    public static String getTimeYMDcn(long time) {
        return sdf_simple_name_cn.format(new Date(time));
    }

    public static String getTimeYMcn(long time) {
        return sdf_simple_name_ym_cn.format(new Date(time));
    }

    public static String getTimeHM(long time) {
        return sdf_msg_yesterday_hh_mm.format(new Date(time));
    }

    public static String getTimeY(long time) {
        return sdf_simple_y.format(new Date(time));
    }


    public static long parseTimeY(String year) {
        try {
            return sdf_simple_y.parse(year).getTime();
        } catch (Exception e) {
            return 0l;
        }
    }


    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        return Integer.parseInt(getTimeY(System.currentTimeMillis()));

    }


    public static int getYear(long time) {
        return Integer.parseInt(getTimeY(time));
    }


    public static String getTimeMM(long time) {
        return sdf_mm.format(new Date(time));
    }


    public static String getTimeM(long time) {
        return sdf_simple_m.format(new Date(time));
    }

    public static String getTimeD(long time) {
        return sdf_simple_d.format(new Date(time));
    }

    public static String getSimpleName() {
        return sdf_simple_name.format(new Date(System.currentTimeMillis()));
    }

    public static String getTimeHMS(long time){
        return sdf_HHmmss.format(new Date(time));
    }

    public static String getTimeFormat(long time, String format) {
        if (TextUtils.isEmpty(format)) {
            return getTimeYMDcn(time);
        } else {
            String d = sdf.format(time);
            try {
                Date date = sdf.parse(d);
                return new SimpleDateFormat(format).format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    public static String getFormatTime(String time, SimpleDateFormat format) {
        try {
            Date date = sdf_msg_sss.parse(time);
            String dateTime = format.format(date);
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 过期时间
     *
     * @param time
     * @param serviceTime
     * @return
     */
    public static String getDeadTime(String time, long serviceTime) {
        try {
            Date date = sdf.parse(time);
            long dead = date.getTime();

            long compare = dead - serviceTime;
            if (compare <= 0) {
                return "已过期";
            }
            long seconds = compare / 1000;
            long min = seconds / 60;//分钟
            long remainMin = seconds % 60;
            long hour = min / 60;
            long remainHour = min % 60;
            long day = hour / 24;

            long sec = seconds % 60;//s
            if (day >= 1) {
                return day + "天";
            } else {
                if (hour > 0) {
                    return hour + "小时" + remainMin + "分";
                } else {
                    if (remainMin > 0) {
                        return remainMin + " : " + sec;
                    }
                    return sec + "";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "已过期";
    }

    /**
     * 判断福利券是否过期
     *
     * @param time
     * @param serviceTime
     * @return
     */
    public static boolean isOverdue(String time, long serviceTime) {
        try {
            Date date = sdf.parse(time);
            long dead = date.getTime();
            if (dead > serviceTime) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String getCurrentTime(long expire) {
        if (expire <= 0) {
            return "0";
        }
        long seconds = expire / 1000;
        long min = seconds / 60;//分钟
        long sec = seconds % 60;//s
        if (min > 0) {
            return min + " : " + sec;
        }
        return sec + "";
    }
}
