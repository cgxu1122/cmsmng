package com.ifhz.core.base.commons.codec;

import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/1
 * Time: 17:44
 */
public final class AnalysisApkFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalysisApkFile.class);

    public static final String OS;
    public static final File WorkDir;
    public static final boolean isLinux;

    static {
        String workDirPath = MapConfig.getString("aapt.work.dir", GlobalConstants.GLOBAL_CONFIG, "");
        WorkDir = new File(workDirPath);
        if (!WorkDir.exists()) {
            throw new RuntimeException("aapt work dir not exists");
        } else {
            if (!WorkDir.isDirectory()) {
                throw new RuntimeException("aapt work dir must be directory");
            }
        }
        OS = System.getProperty("os.name").toLowerCase();
        LOGGER.info("os.name={}", OS);
        if (StringUtils.containsIgnoreCase(OS, "linux")) {
            isLinux = true;
        } else {
            isLinux = false;
        }
    }

    public static String parseApk(String toFilePath) {
        long start = System.currentTimeMillis();
        String result = null;
        try {
            if (StringUtils.isNotBlank(toFilePath)) {
                if (isLinux) {
                    String linuxCmd = getLinuxCmd(toFilePath);
                    result = execLinux(linuxCmd, WorkDir);
                } else {
                    String winCmd = getWinCmd(toFilePath);
                    result = execWin(winCmd, WorkDir);
                }
            }
        } catch (Exception e) {
            LOGGER.error("paserApk error", e);
        } finally {
            long end = System.currentTimeMillis();
            LOGGER.info("parseApkFile totalTime={}", end - start);
        }

        return result;
    }

    private static String parseActivtyPath(String input) {
        if (StringUtils.isNotBlank(input)) {
            String temp = input.substring(input.indexOf("\'") + 1);
            return temp.substring(0, temp.indexOf("\'"));
        }

        return null;
    }

    public static String execLinux(String cmd, File workDir) {
        BufferedReader br = null;
        String pkgNameTemp = "";
        String activtyNameTemp = "";
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd, null, workDir);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String temp;
            while ((temp = br.readLine()) != null) {
                if (StringUtils.startsWith(temp, "launchable-activity:")) {
                    activtyNameTemp = temp;
                }
                if (StringUtils.startsWith(temp, "package:")) {
                    pkgNameTemp = temp;
                }
            }
            if (StringUtils.isNotBlank(pkgNameTemp) && StringUtils.isNotBlank(activtyNameTemp)) {
                String pkgName = parseActivtyPath(pkgNameTemp);
                String activityName = parseActivtyPath(activtyNameTemp);
                if (StringUtils.isNotBlank(pkgName) && StringUtils.isNotBlank(activityName)) {
                    return pkgName + "/" + activityName;
                }
            }
        } catch (IOException e) {
            LOGGER.error("execWin error", e);
        } finally {
            IOUtils.closeQuietly(br);
        }

        return null;
    }

    private static String getWinCmd(String apkPath) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("cmd.exe  /c  aapt  dump badging ");
        buffer.append(apkPath);

        return buffer.toString();
    }

    private static String getLinuxCmd(String apkPath) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("./aapt  dump badging ");
        buffer.append(apkPath);

        return buffer.toString();
    }

    public static String execWin(String cmd, File workDir) {
        BufferedReader br = null;
        try {
            String pkgNameTemp = "";
            String activtyNameTemp = "";
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd, null, workDir);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String temp;
            while ((temp = br.readLine()) != null) {
                if (StringUtils.startsWith(temp, "launchable-activity:")) {
                    activtyNameTemp = temp;
                }
                if (StringUtils.startsWith(temp, "package:")) {
                    pkgNameTemp = temp;
                }
            }
            if (StringUtils.isNotBlank(pkgNameTemp) && StringUtils.isNotBlank(activtyNameTemp)) {
                String pkgName = parseActivtyPath(pkgNameTemp);
                String activityName = parseActivtyPath(activtyNameTemp);
                if (StringUtils.isNotBlank(pkgName) && StringUtils.isNotBlank(activityName)) {
                    return pkgName + "/" + activityName;
                }
            }
        } catch (IOException e) {
            LOGGER.error("execWin error", e);
        } finally {
            IOUtils.closeQuietly(br);
        }

        return null;
    }


    public static void main(String[] args) throws Exception {
        String path = "D:\\1.apk";
        String t = parseApk(path);
        System.out.println(t);
        path = "D:\\2.apk";
        t = parseApk(path);
        System.out.println(t);
        path = "D:\\3.apk";
        t = parseApk(path);
        System.out.println(t);

        path = "D:\\qq.apk";
        t = parseApk(path);
        System.out.println(t);


    }

    private AnalysisApkFile() {
    }
}
