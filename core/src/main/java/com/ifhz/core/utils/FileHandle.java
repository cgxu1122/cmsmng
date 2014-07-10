package com.ifhz.core.utils;

import com.google.common.io.Closeables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * User:
 * Date: 11-11-21
 * Time: 下午2:33
 */
public class FileHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileHandle.class);
    public static final String HEADER_FILE_NAME = "filename=\"";
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 从multi part的header中解析文件名,使用默认的编码{@link #DEFAULT_CHARSET}
     *
     * @param contentDisposition
     * @return
     */
    public static String getFileName(final String contentDisposition) {
        return getFileName(contentDisposition, DEFAULT_CHARSET);
    }

    /**
     * 从multi part的header中解析文件名
     *
     * @param contentDisposition header的内容
     * @param charset            编码
     * @return
     */
    public static String getFileName(final String contentDisposition, final String charset) {
        if (contentDisposition == null || contentDisposition.isEmpty()) {
            return null;
        }
        int fileNameBegin = contentDisposition.indexOf(HEADER_FILE_NAME);
        if (fileNameBegin < 0) {
            return null;
        }
        fileNameBegin = fileNameBegin + HEADER_FILE_NAME.length();
        int fileNameEnd = contentDisposition.indexOf("\"", fileNameBegin);
        if (fileNameEnd < 0) {
            return null;
        }
        String fileName = contentDisposition.substring(fileNameBegin, fileNameEnd).trim();
        try {
            fileName = URLDecoder.decode(fileName, charset);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        return fileName;
    }

    /**
     * 取得文件的后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(final String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return null;
        }
        int begin = index + 1;
        if (begin >= (fileName.length())) {
            return null;
        }
        return fileName.substring(begin);
    }

    public static void safeWrite(ServletResponse response, String msg) {
        if (response == null) {
            return;
        }
        synchronized (response) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-javascript");
            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.write(msg);
                writer.flush();
            } catch (IOException e) {
                LOGGER.error("safe write error.", e);
            } finally {
                Closeables.closeQuietly(writer);
            }
        }
    }
}
