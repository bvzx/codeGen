package io.bvzx.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by lss on 16-8-1.
 */
public class Logger {


    private String currentClazzName;

    private static final String format="yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat(format);
    private static final Date date=new Date();


    public void log(String log){
        date.setTime(System.currentTimeMillis());
        System.out.println("["+currentClazzName+"]["+ simpleDateFormat.format(date)+"]--"+log);
    }

    public static Logger getLogger(Class<?> clazz){
        Objects.requireNonNull(clazz);
        return new Logger(clazz.getName());
    }

    private Logger(String identification) {
        currentClazzName=identification;
    }
}
