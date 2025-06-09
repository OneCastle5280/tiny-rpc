package com.wang.rpc.core.utils;

/**
 * @author wangjiabao
 */
public class ThrowableUtil {

    /**
     * transfer throwable to string
     *
     * @param throwable
     * @return
     */
    public static String toString(Throwable throwable) {
        StringBuilder msg = new StringBuilder();
        msg.append("Exception Type: ").append(throwable.getClass().getName()).append("\n");
        msg.append("Exception Message: ").append(throwable.getMessage()).append("\n");

        for (StackTraceElement element : throwable.getStackTrace()) {
            msg.append("\tat ").append(element.toString()).append("\n");
        }
        return msg.toString();
    }
}
