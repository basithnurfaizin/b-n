package com.bantuin.ticket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {

    private static final Logger logger = LogManager.getLogger("co.id.bantuin");

    public static void debug(String TAG, String message) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        logger.debug(generateTag(TAG) + message);
    }

    public static void debug(String TAG, String message, Double value) {
        debug(TAG,  message + " " + String.format("%1$,.2f", value));
    }

    public static void debug(String TAG, String message, Integer value) {
        debug(TAG,  message + " " + value);
    }

    public static void debug(String TAG, Object object) {
        debug(TAG, "", object);
    }

    public static void debug(String TAG, String message,  Object object) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        try {
            debug(TAG,  message+ " " + new ObjectMapper().writeValueAsString(object));
        } catch (Exception e) {
            error(TAG, e);
        }
    }

    public static void debug(String TAG, String message, Object... args) {
        debug(TAG, String.format(message, args));
    }

    public static void info(String TAG, String message) {
        logger.info(generateTag(TAG) + message);
    }

    public static void info(String TAG, String message, Object... args) {
        info(TAG, String.format(message, args));
    }

    public static void info(String TAG, Object object) {
        try {
            info(TAG, new ObjectMapper().writeValueAsString(object));
        } catch (JsonProcessingException e) {
            error(TAG, e);
        }
    }

    public static void error(String TAG, Throwable throwable) {
        logger.error(TAG, throwable);
    }

    private static String generateTag(String TAG) {
        if (logger.isDebugEnabled()) {
            return TAG + "@" + getMethodName(TAG) + " - ";
        }
        return TAG + " - ";
    }

    private static String getMethodName(String TAG) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length <= 0) {
            return "";
        }
        for (StackTraceElement stackTrace :elements) {
            final String className = stackTrace.getClassName();
            if (!className.equalsIgnoreCase("utils.LogUtil") && className.contains(TAG)) {
                return stackTrace.getMethodName() + "()[" + stackTrace.getLineNumber() + "]";
            }
        }
        return "";
    }
}
