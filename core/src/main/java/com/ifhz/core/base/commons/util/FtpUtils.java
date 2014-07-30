package com.ifhz.core.base.commons.util;

import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.GlobalConstants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpUtils.class);

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
            LOGGER.error("ftp error", e);
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(is);
            try {
                ftpClient.disconnect();
            } catch (Exception e) {
                LOGGER.error("ftp error", e);
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
            LOGGER.error("ftp error", e);
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            IOUtils.closeQuietly(fos);
            try {
                ftpClient.disconnect();
            } catch (Exception e) {
                LOGGER.error("ftp error", e);
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }

    /**
     * FTP下载单个文件测试
     */
    public static void ftpDelete(String remoteFilePath) {
        FTPClient ftpClient = new FTPClient();
        String parentDirPath = getParentDirPath(remoteFilePath);
        try {
            String hosts = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_URL);
            int port = MapConfig.getInt(GlobalConstants.FTP_SERVER_PORT, GlobalConstants.GLOBAL_CONFIG, 21);
            String username = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_USERNAME);
            String password = GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.FTP_SERVER_PASSWORD);
            ftpClient.connect(hosts, port);
            ftpClient.login(username, password);
            ftpClient.setBufferSize(1024);
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.deleteFile(remoteFilePath);
            if (StringUtils.isNotBlank(parentDirPath)) {
                ftpClient.removeDirectory(parentDirPath);
            }
        } catch (IOException e) {
            LOGGER.error("Ftp error", e);
            throw new RuntimeException("FTP客户端出错！", e);
        } finally {
            try {
                ftpClient.disconnect();
            } catch (Exception e) {
                LOGGER.error("ftp error", e);
                throw new RuntimeException("关闭FTP连接发生异常！", e);
            }
        }
    }


    private static String getParentDirPath(String remoteFilePath) {
        if (StringUtils.isNotBlank(remoteFilePath)) {
            return StringUtils.substring(remoteFilePath, 0, remoteFilePath.lastIndexOf("/"));
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        /*String str = "/data/apk/1404531957414/1 (37).apk";
        String t = getParentDirPath(str);
        System.out.println(t);*/
        String filePath = "D:\\work\\nginx.tar";
        InputStream in = new FileInputStream(filePath);
        FtpUtils.ftpUpload(in, "/data/device", "nginx.tar");
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
