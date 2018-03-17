package com.webapp.common.utils;

/**
 * @author Robert.XU
 * @email <xutao@bjnja.com>
 * @date 2018/1/8 0008 下午 05:57
 * @description
 */
public class FormatUtil {
    private static final String NULL = "null";

    /**
     * 判断一个对象是否不为空。
     * 当对象本身不为null时，且对象执行toString方法不为空时，且对象执行toString方法不等于null字符串。
     * @param object 待判断对象
     * @return 返回结论，true不为空，false为空
     */
    public static boolean isNotEmpty(Object object) {
        if (null != object) {
            if (object.toString().trim().length() > 0) {
                if (!NULL.equals(object.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断一个对象是否为空。
     * 打对象为null时、当对象的toString方法为空时、当对象的toString方法等于null字符串时为空。
     * @param object 待判断对象
     * @return 返回结论，true为空，false不为空。
     */
    public static boolean isEmpty(Object object) {
        if (null == object) {
            return true;
        }
        if (object.toString().trim().length() == 0) {
            return true;
        }
        if (NULL.equals(object.toString())) {
            return true;
        }
        return false;
    }
}
