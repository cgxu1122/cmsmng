package com.ifhz.core.progress;

import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpSession;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/9
 * Time: 22:08
 */
public class UploadProgressListener implements ProgressListener {
    private HttpSession session;

    public UploadProgressListener(HttpSession session) {
        ProgressModel model = new ProgressModel();
        this.session = session;
    }


    @Override
    public void update(long bytesRead, long contentLength, int items) {
        ProgressModel model = (ProgressModel) session.getAttribute("upload_progress");
        if (model == null) {
            model = new ProgressModel();
        }
        model.setBytesRead(bytesRead);
        model.setContentLength(contentLength);
        model.setItems(items);
        session.setAttribute("upload_progress", model);
    }
}
