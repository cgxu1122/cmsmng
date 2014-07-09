package com.ifhz.core.progress;

import org.apache.commons.fileupload.ProgressListener;

import javax.servlet.http.HttpSession;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/9
 * Time: 22:09
 */
public class DownloadProgressListener implements ProgressListener {

    private HttpSession session;

    public DownloadProgressListener(HttpSession session) {
        this.session = session;
    }

    @Override
    public void update(long bytesRead, long contentLength, int items) {

    }
}
