package com.ifhz.tymng.utils.toexcel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileUtil {

    //delete file
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //create workbag
    public static void createWorkBag(String workPath) {
        File file = new File(workPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    //得到现在的系统时刻
    public static String timeStamp(boolean boo) {
        String currentTime = "";
        String parameter = "";
        if (boo) {
            parameter = "yyyyMMddhhmmss";
        } else {
            parameter = "yyyyMMdd";
        }
        Calendar a = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(parameter);
        currentTime = s.format(a.getTime());
        return currentTime;
    }

    //以时间为文件生成路径
    public static String createTimeBag(String basePath) {
        String path = "";
        String timeStamp = timeStamp(false);
        path = path + timeStamp.substring(0, 4) + "\\" +
                timeStamp.substring(4, 6) + "\\" +
                timeStamp.substring(6, 8) + "\\";
        basePath = basePath + path;
        createWorkBag(basePath);
        return basePath;
    }

    //根据传进的文件格式进行判断转换,组合成  XXXX.xls
    public static String processFileTail(String fileName, String synax) {
        String resultFileName = "";
        int dot = fileName.indexOf(".");
        if (dot > 0) {
            resultFileName = fileName.substring(0, dot) + synax;
        } else {
            resultFileName = fileName + synax;
        }
        return resultFileName;
    }

    //数组转换，把 一纬数组 转换成 2纬数组
    public static String[][] getArray(String[] name) {
        int length = name.length;
        String[][] outValue = new String[length][1];
        for (int i = 0; i < length; i++) {
            outValue[i][0] = name[i];
        }
        return outValue;
    }
}
