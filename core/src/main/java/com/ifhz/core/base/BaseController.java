package com.ifhz.core.base;

import com.ifhz.core.base.commons.util.ExportDataUtil;
import com.ifhz.core.service.export.model.BaseExportModel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author radishlee
 */
public class BaseController {


    public Map<String, FileItem> paserMultiData(HttpServletRequest request) {
        Map<String, FileItem> map = new HashMap<String, FileItem>();
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(200 * 1024 * 1024);
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        try {
            // 分析构成文件列表,把表单每个项都列表了,要进行判断
            List<FileItem> listItems = servletFileUpload.parseRequest(request);
            for (FileItem fileItem : listItems) {
                if (!StringUtils.isEmpty(fileItem.getFieldName())) {
                    map.put(fileItem.getFieldName(), fileItem);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String readInputStreamData(InputStream in) {
        String result = null;
        try {
            byte[] tt = new byte[in.available()];
            int b;
            while ((b = in.read(tt)) != -1) {
            }
            result = new String(tt, "utf-8");
        } catch (IOException e) {
        } finally {
            IOUtils.closeQuietly(in);
        }
        return result;
    }

    public void exportXLSData(HttpServletRequest request, HttpServletResponse response, BaseExportModel exportModel) {
        String basePath = request.getSession().getServletContext().getRealPath("/");
        String fileName = Calendar.getInstance().getTimeInMillis() + ".xls";
        String filePath = basePath + "/tempExport/" + fileName;
        File file = new File(filePath);
        ExportDataUtil.writeData(exportModel, file);
        // 读到流中
        InputStream inStream = null;// 文件的存放路径
        try {
            inStream = new FileInputStream(filePath);
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
            if (file.exists()) {
                System.gc();
                file.delete();
            }
        }
    }
}
