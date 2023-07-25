package com.xinxu.user.util;



import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


public class SafeConverter {

    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    public static int toInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        if (obj instanceof Long) {
            // 这里可能会丢失精度
            return ((Long) obj).intValue();
        }
        String str = obj.toString();
        str = StringUtils.trim(str);
        return NumberUtils.toInt(str, defaultValue);
    }


    public static long toLong(Object obj) {
        return toLong(obj, 0);
    }

    public static long toLong(Object obj, long defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        }
        if (obj instanceof Date) {
            return ((Date) obj).getTime();
        }
        if (obj instanceof Instant) {
            return ((Instant) obj).toEpochMilli();
        }
        String str = obj.toString();
        str = ExtendedStringUtils.trim(str);
        return NumberUtils.toLong(str, defaultValue);
    }

    public static double toDouble(Object obj) {
        return toDouble(obj, 0);
    }

    public static double toDouble(Object obj, double defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Double) {
            return (Double) obj;
        }
        String str = obj.toString();
        str = ExtendedStringUtils.trim(str);
        return NumberUtils.toDouble(str, defaultValue);
    }

    public static float toFloat(Object obj) {
        return toFloat(obj, 0);
    }

    public static float toFloat(Object obj, float defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Float) {
            return (Float) obj;
        }
        String str = obj.toString();
        str = ExtendedStringUtils.trim(str);
        return NumberUtils.toFloat(str, defaultValue);
    }


    public static boolean toBoolean(Object obj) {
        return toBoolean(obj, false);
    }


    public static boolean toBoolean(Object obj, boolean defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue() == 1;
        }
        String str = obj.toString();
        str = ExtendedStringUtils.trim(str);
        return ExtendedStringUtils.equals(str, "true");
    }

    public static String toString(Object obj) {
        return toString(obj, null);
    }


    public static String toString(Object obj, String defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        // every non-null object can to converted to string by invoking toString method
        // but pay attention sometimes toString return meaningless string for some objects
        if (obj.getClass().isEnum()) {
            return ((Enum) obj).name();
        }
        if (obj instanceof Date) {
            String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
            return FastDateFormat.getInstance(pattern).format((Date) obj);
        }
        if (obj instanceof Calendar) {
            String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
            return FastDateFormat.getInstance(pattern).format(((Calendar) obj).getTime());
        }
        // invoke object to string directly
        return obj.toString();
    }

    public static Date toDate(Object obj) {
        return toDate(obj, null);
    }


    public static Date toDate(Object obj, Date defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Calendar) {
            Calendar calendar = (Calendar) obj;
            return calendar.getTime();
        }
        if (obj instanceof Long) {
            Long l = (Long) obj;
            return new Date(l);
        }
        if (obj instanceof Integer) {
            Integer i = (Integer) obj;
            return new Date(i);
        }
        if (obj instanceof String) {
            String s = (String) obj;
            // hmmm, string passed in, just try followig patterns
            String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
            Date date;
            try {
                date = FastDateFormat.getInstance(pattern).parse(s);
            } catch (Exception ex) {
                date = null;
            }
            if (date != null) {
                return date;
            }
            // continue try this pattern
            pattern = "yyyy-MM-dd HH:mm:ss";
            try {
                date = FastDateFormat.getInstance(pattern).parse(s);
            } catch (Exception ex) {
                date = null;
            }
            return date == null ? defaultValue : date;
        }
        // we don't accept other types object, just return default value here
        return defaultValue;
    }
}
