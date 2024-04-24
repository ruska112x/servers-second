package org.karabalin.task11;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Formatter {

    private ThreadLocal<SimpleDateFormat> dateFormatThreadLocal;

    public Formatter(String pattern) {
        if (pattern == null) {
            pattern = "dd.MM.yyyy - HH:mm:ss";
        }
        String finalPattern = pattern;
        dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(finalPattern));
    }

    public String format(Date date) {
        return dateFormatThreadLocal.get().format(date);
    }
}
