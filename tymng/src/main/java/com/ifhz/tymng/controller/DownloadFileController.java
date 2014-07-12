package com.ifhz.tymng.controller;

import com.ifhz.core.base.BaseController;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author yangjian
 */
@Controller
@RequestMapping("/tymng/downloadFile")
public class DownloadFileController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadFileController.class);

    @RequestMapping(value = "/downloadFile")
    @ResponseBody
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getParameter("path");
        if (StringUtils.isEmpty(path)) {
            return;
        }
        String fileName = path.substring(path.lastIndexOf(File.separator) + 1, path.length());
        // 读到流中
        InputStream inStream = null;// 文件的存放路径
        try {
            inStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                response.getOutputStream().write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inStream);
        }
    }

}