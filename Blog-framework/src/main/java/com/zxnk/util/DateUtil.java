package com.zxnk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DateUtil
 * @Description 时间格式工具类
 * @Author cc
 * @Date 2023/7/5 14:02
 * @Version 1.0
 */
public class DateUtil {

    private static final String FORMATPATTERN = "YYYY-MM-DD HH-mm-ss";

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMATPATTERN);

    public static String format(Date date){
        return simpleDateFormat.format(date);
    }
}