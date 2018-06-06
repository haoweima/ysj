package com.logindemo.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by marnon on 2018/5/17.
 */
public class DateUtil {
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static String getOffsetDateStr(Date d, int day, String format){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String date = simpleDateFormat.format(now.getTime());

        return date;
    }

    public static Date getDateFromString(String d, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            Date date = simpleDateFormat.parse(d);
            return date;
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String getStringFromDate(Date d, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String date = simpleDateFormat.format(d);

        return date;
    }

    public static String typeFormat(String d, String format1, String format2){
        Date date = getDateFromString(d, format1);
        return getStringFromDate(date, format2);
    }
    /**
     * 获取日期前day天日期序列
     */
    public static List<String> getNearDate(Date d, int day){
        List<String> result = new ArrayList<>();

        for(int i = 0; i < day; i++){
            result.add(getOffsetDateStr(d, i, YYYYMMDD));
        }

        return result;
    }

}
