package com.ifhz.core.base;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
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
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
