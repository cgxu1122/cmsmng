package com.ifhz.core.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 不能处理中文文件名
 */
public class ZipUtils {
    private static final int buffer = 2048;

    public static void main(String[] args) {
        unZip("E:\\test.zip");
    }

    public static void unZip(String path) {
        if (!path.endsWith(".zip")) {
            return;
        }
        BufferedOutputStream bos = null;
        ZipEntry entry = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipFile zipfile = new ZipFile(path);

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    continue;
                } else {
                    InputStreamReader is = new InputStreamReader(zipfile.getInputStream(entry));
                    BufferedReader br = new BufferedReader(is);
                    String con = null;
                    while ((con = br.readLine()) != null) {
                        System.out.println(entry.getName());
                        System.out.println(con);
                    }
                }
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
