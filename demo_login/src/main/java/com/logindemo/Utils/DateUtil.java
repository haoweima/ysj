package com.logindemo.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by marnon on 2018/5/17.
 */
public class DateUtil {

    public static String getOffsetDateStr(Date d, int day){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(now.getTime());

        return date;
    }

    public static String getStringFromDate(Date d){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(d);

        return date;
    }

}
