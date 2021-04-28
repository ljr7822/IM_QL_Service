package com.iwen.web.qingliao.push.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志工具类的封装
 *
 * @Author: iwen大大怪
 * @DateTime: 2021/04/29 1:31
 */
public class LogUtils {

    /**
     * 每条 Log 的 tag 输出的最大长度, 超过部分将被截断
     */
    private static final int TAG_MAX_LENGTH = 20;

    /**
     * 每条 Log 的 message 输出的最大长度, 超过部分将被截断
     */
    private static final int MESSAGE_MAX_LENGTH = 1024;

    /**
     * 日期前缀格式化
     */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd HH:mm:ss.SSS");

    /**
     * 日志当前的输出级别, 默认为 INFO 级别
     */
    private static Level logOutLevel = Level.INFO;

    /**
     * 是否输出到控制台, 默认输出
     */
    private static boolean isOutToConsole = true;

    /**
     * 是否输出到文件
     */
    private static boolean isOutToFile = false;

    /**
     * 日志输出文件, 追加到文件尾
     */
    private static File logOutFile;

    /**
     * 日志文件输出流, 追加到文件尾
     */
    private static RandomAccessFile logOutFileStream;

    public static void setLogOutLevel(Level currentLevel) {
        if (currentLevel == null) {
            currentLevel = Level.INFO;
        }
        LogUtils.logOutLevel = currentLevel;
    }

    public static synchronized void setLogOutFile(File logOutFile) throws IOException {
        LogUtils.logOutFile = logOutFile;

        if (logOutFileStream != null) {
            closeStream(logOutFileStream);
            logOutFileStream = null;
        }

        if (LogUtils.logOutFile != null) {
            try {
                logOutFileStream = new RandomAccessFile(LogUtils.logOutFile, "rw");
                logOutFileStream.seek(LogUtils.logOutFile.length());
            } catch (IOException e) {
                closeStream(logOutFileStream);
                logOutFileStream = null;
                throw e;
            }
        }
    }

    public static void setLogOutTarget(boolean isOutToConsole, boolean isOutToFile) {
        LogUtils.isOutToConsole = isOutToConsole;
        LogUtils.isOutToFile = isOutToFile;
    }

    public static void debug(String tag, String message) {
        printLog(Level.DEBUG, tag, message, false);
    }

    public static void info(String tag, String message) {
        printLog(Level.INFO, tag, message, false);
    }

    public static void warn(String tag, String message) {
        printLog(Level.WARN, tag, message, false);
    }

    public static void error(String tag, String message) {
        printLog(Level.ERROR, tag, message, true);
    }

    public static void error(String tag, Exception e) {
        if (e == null) {
            error(tag, (String) null);
            return;
        }

        PrintStream printOut = null;

        try {
            ByteArrayOutputStream bytesBufOut = new ByteArrayOutputStream();
            printOut = new PrintStream(bytesBufOut);
            e.printStackTrace(printOut);
            printOut.flush();
            error(tag, bytesBufOut.toString("UTF-8"));

        } catch (Exception e1) {
            e1.printStackTrace();

        } finally {
            closeStream(printOut);
        }
    }

    private static void printLog(Level level, String tag, String message, boolean isOutToErr) {
        if (level.getLevelValue() >= logOutLevel.getLevelValue()) {
            String log = DATE_FORMAT.format(new Date()) +
                    " " +
                    level.getTag() +
                    "/" +
                    checkTextLengthLimit(tag, TAG_MAX_LENGTH) +
                    ": " +
                    checkTextLengthLimit(message, MESSAGE_MAX_LENGTH);

            if (isOutToConsole) {
                outLogToConsole(isOutToErr, log);
            }
            if (isOutToFile) {
                outLogToFile(log);
            }
        }
    }

    private static void outLogToConsole(boolean isOutToErr, String log) {
        if (isOutToErr) {
            // System.err 和 System.out 是两个不同的输出流通道, 如果极短时间内连
            // 续输出 log 到 err 和 out, 控制台上的打印顺序可能会不完全按时序打印.
            System.err.println(log);
        } else {
            System.out.println(log);
        }
    }

    private static synchronized void outLogToFile(String log) {
        if (logOutFileStream != null) {
            try {
                logOutFileStream.write((log + "\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String checkTextLengthLimit(String text, int maxLength) {
        if ((text != null) && (text.length() > maxLength)) {
            text = text.substring(0, maxLength - 3) + "...";
        }
        return text;
    }

    private static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                // nothing
            }
        }
    }

    public static enum Level {
        // DEBUG
        DEBUG("D", 1),
        // INFO
        INFO("I", 2),
        // WARN
        WARN("W", 3),
        // ERROR
        ERROR("E", 4);

        private String tag;

        private int levelValue;

        private Level(String tag, int levelValue) {
            this.tag = tag;
            this.levelValue = levelValue;
        }

        public String getTag() {
            return tag;
        }

        public int getLevelValue() {
            return levelValue;
        }
    }
}
