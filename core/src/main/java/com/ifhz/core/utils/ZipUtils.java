package com.ifhz.core.utils;

import java.io.*;
import java.util.zip.ZipEntry;
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
        int count = -1;
        int index = -1;

        String savepath = "E:\\save";
        boolean flag = false;

        savepath = path.substring(0, path.lastIndexOf("\\")) + "\\";
        if (!savepath.endsWith(".zip")) {
            return;
        }

        try {
            BufferedOutputStream bos = null;
            ZipEntry entry = null;
            FileInputStream fis = new FileInputStream(path);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));

            while ((entry = zis.getNextEntry()) != null) {
                byte data[] = new byte[buffer];
                String temp = entry.getName();
                index = temp.lastIndexOf("/");
                if (index > -1) {
                    temp = temp.substring(index + 1);
                }
                temp = savepath + temp;

                File f = new File(temp);
                f.createNewFile();

                FileOutputStream fos = new FileOutputStream(f);
                bos = new BufferedOutputStream(fos, buffer);

                while ((count = zis.read(data, 0, buffer)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
            }
            zis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
