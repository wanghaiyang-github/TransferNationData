package com.bazl.lims.transfer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lizhihua on 2019-05-09.
 */
public class DateUtils {

    public static String dateToString(Date date, String fmt){
        return format(fmt, date);
    }

    public static String format(String format, Date value) {
        SimpleDateFormat dateFmt = new SimpleDateFormat(format);
        return dateFmt.format(value);
    }

    public static String format(Date value) {
        return format("yyyy-MM-dd HH:mm:ss", value);
    }

    public static Date parse(String format, String value) throws ParseException {
        SimpleDateFormat dateFmt = new SimpleDateFormat(format);
        return dateFmt.parse(value);
    }

    public static Date parse(String value) throws ParseException {
        return parse("yyyy-MM-dd HH:mm:ss.SSS", value);
    }
}
