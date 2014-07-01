package com.ifhz.core.base.commons.codec;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;
import java.util.regex.Pattern;

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
    //launchable-activity: name='com.tencent.mobileqq.activity.SplashActivity'  label='QQ' icon=''
    //launchable-activity: name='cn.gm.jumping.MainActivity'  label='跳跃忍者' icon=''
//    public static final Pattern PATTERN = Pattern.compile("launchable-activity: name=\\'(\\d+).*\\'  label=(\\d+).*");

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
        if (StringUtils.containsIgnoreCase(OS, "linux")) {
            isLinux = true;
        } else {
            isLinux = false;
        }
    }

    public static String paserApk(InputStream inputStream) {
        long start = System.currentTimeMillis();
        String result = null;
        File toFile = null;
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(MapConfig.getString(GlobalConstants.KEY_LOCAL_STORE_DIR, GlobalConstants.GLOBAL_CONFIG, "/data/app"));
            buffer.append(File.separator);
            buffer.append(new Date().getTime());
            buffer.append(".apk");
            toFile = new File(buffer.toString());
            ByteStreams.copy(inputStream, Files.newOutputStreamSupplier(toFile));
            String apkPath = toFile.getAbsolutePath();
            if (isLinux) {
                String winCmd = getLinuxCmd(apkPath);
                String ret = execLinux(winCmd, WorkDir);
                result = paserActivtyPath(ret);
            } else {
                String winCmd = getWinCmd(apkPath);
                String ret = execWin(winCmd, WorkDir);
                result = paserActivtyPath(ret);
            }
        } catch (Exception e) {
            LOGGER.error("paserApk error", e);
        } finally {
            long end = System.currentTimeMillis();
            if (toFile != null) {
                try {
                    toFile.deleteOnExit();
                    toFile.delete();
                } catch (Exception e) {
                }
            }
            LOGGER.info("parseApkFile totalTime={}", end - start);
        }

        return result;
    }


    private static String paserActivtyPath(String input) {
        if (StringUtils.isNotBlank(input)) {
            String temp = input.substring(input.indexOf("\'") + 1);
            return temp.substring(0, temp.indexOf("\'"));
        }

        return null;
    }

    public static String execLinux(String cmd, File workDir) {
        BufferedReader br = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd, null, workDir);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String temp;
            while ((temp = br.readLine()) != null) {
                if (StringUtils.containsIgnoreCase(temp, "launchable-activity")) {
                    return temp;
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
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(cmd, null, workDir);
            br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String temp;
            while ((temp = br.readLine()) != null) {
                if (StringUtils.containsIgnoreCase(temp, "launchable-activity")) {
                    return temp;
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
        String path = "D:\\qq.apk";
        String cmd = getWinCmd(path);
        String ret = execWin(cmd, WorkDir);
        System.out.println(ret);
        Pattern p = Pattern.compile("^launchable-activity\\sname='(\\d+)'  label=$");
        String temp = ret.substring(ret.indexOf("\'") + 1);
        String tt = temp.substring(0, temp.indexOf("\'"));
        System.out.println(tt);

    }

    private AnalysisApkFile() {
    }
}
