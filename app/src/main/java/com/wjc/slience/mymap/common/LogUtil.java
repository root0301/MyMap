package com.wjc.slience.mymap.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 调试和日志信息打印类
 */
public class LogUtil {

    private static LogUtil sLogUtil;

    public final int DEBUG = 0;

    public final int INFO = 1;

    public final int ERROR = 2;

    public final int NOTHING = 3;

    public int level = DEBUG;

    //单例模式中获取唯一的MyApplication实例
    public static LogUtil getInstance() {
        if (sLogUtil == null) {
            synchronized (LogUtil.class) {
                if (sLogUtil == null) {
                    sLogUtil = new LogUtil();
                }
            }
        }
        return sLogUtil;
    }

    public void debug(String msg) {
        if (DEBUG >= level) {
            System.out.println("DEBUG: "+msg);
        }
    }

    public void info(String msg) {
        if (INFO >= level) {
            System.out.println("INFO: "+msg);
        }
    }

    public void error(String msg) {
        if (ERROR >= level) {
            System.out.println("ERROR: "+msg);
        }
    }

    /**
     * 将查询结果写进文件
     */
    public void writeIntoFile(String msg) {
        File file = new File("/sdcard","TripLog.txt");
        try {
            FileWriter fileWriter = new FileWriter(file,true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);
            buffer.write(msg);
            buffer.newLine();
            buffer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取出查询记录
     */
    public String readTheTrip() {
        File file = new File("/sdcard","TripLog.txt");
        String line = "";
        StringBuilder sb = new StringBuilder("");
        try {
            FileReader fileReader =new FileReader(file);
            BufferedReader buffer = new BufferedReader(fileReader);
            while((line=buffer.readLine())!=null) {
                sb.append(line);
                sb.append("\n");
            }
            buffer.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
