package com.ifhz.core.base.commons.util;

import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * 类描述
 *
 * @author yangjian
 */
public final class FtpUtils {

    /**
     * FTP上传单个文件测试
     */
    public static String ftpUpload(InputStream is, String srcDir, String fileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_URL),
                    MapConfig.getInt(GlobalConstants.FTP_SERVER_PORT, GlobalConstants.GLOBAL_CONFIG, 21));
            ftpClient.login(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_USERNAME),
                    GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_PASSWORD));
            ftpClient.makeDirectory(srcDir);
            //设置上传目录
            ftpClient.changeWorkingDirectory(srcDir);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("GBK");
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(fileName, is);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(is);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
        return null;
    }

    /**
     * FTP下载单个文件测试
     */
    public static void ftpDownload(String remoteFileName, String toFile) {
        FTPClient ftpClient = new FTPClient();
        FileOutputStream fos = null;
        try {
            ftpClient.connect(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_URL),
                    MapConfig.getInt(GlobalConstants.FTP_SERVER_PORT, GlobalConstants.GLOBAL_CONFIG, 21));
            ftpClient.login(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_USERNAME),
                    GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_PASSWORD));
            fos = new FileOutputStream(toFile);
            ftpClient.setBufferSize(1024);
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(remoteFileName, fos);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }

    /**
     * 生成带有时间戳的文件名
     *
     * @param fileName
     * @return
     */
    public static String generateFileName(String fileName) {
        if (StringUtils.isEmpty(fileName)) return null;
        if (fileName.indexOf(".") == -1) return fileName;
        String tempName = fileName.substring(0, fileName.lastIndexOf("."));
        String extName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        String result = tempName + Calendar.getInstance().getTimeInMillis() + extName;
        return result;
    }

}
