package com.ifhz.core.progress;

import java.io.Serializable;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/9
 * Time: 22:10
 */
public class ProgressModel implements Serializable {
    private static final long serialVersionUID = 949123902869028252L;

    private long bytesRead = 0L;   //到目前为止读取文件的比特数
    private long contentLength = 0L;    //文件总大小
    private int items; //当前正在处理第几个文件

    public long getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(long bytesRead) {
        this.bytesRead = bytesRead;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }
}
