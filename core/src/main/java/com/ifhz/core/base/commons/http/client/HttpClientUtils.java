package com.ifhz.core.base.commons.http.client;

import com.google.common.io.Closeables;
import com.ifhz.core.base.commons.http.constants.HTTPConstants;
import com.ifhz.core.base.commons.http.constants.HttpClientConfig;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Map;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

/**
 * Http客户端工具类
 *
 * @author chenggang.xu@qunar.com created at 13-9-5 下午2:45
 * @version 1.0.0
 */
public final class HttpClientUtils {

    private HttpClientUtils() {
    }


    /**
     * 解压缩GZIP或者DEFLATE输入流
     *
     * @param compressFormat
     * @param inputStream    原始字节流
     * @return 解压缩后的字符串
     * @throws java.io.IOException
     */
    public static String uncompressStream(HttpClientConfig.CompressFormat compressFormat,
                                          InputStream inputStream) throws IOException {
        FilterInputStream compressStream = null;
        InputStreamReader inputReader = null;
        StringWriter strWriter = null;
        try {
            switch (compressFormat) {
                case COMPRESS_FORMAT_GZIP:
                    compressStream = new GZIPInputStream(inputStream);
                    break;
                case COMPRESS_FORMAT_DEFLATE:
                    compressStream = new DeflaterInputStream(inputStream);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            inputReader = new InputStreamReader(compressStream, HTTPConstants.DEFAULT_CHARSET);
            strWriter = new StringWriter();
            IOUtils.copy(inputReader, strWriter);
            return strWriter.toString();
        } finally {
            //关闭顺序 :先外后里
            Closeables.closeQuietly(strWriter);
            Closeables.closeQuietly(inputReader);
            Closeables.closeQuietly(compressStream);
        }
    }

    /**
     * 组装拼接get请求参数
     *
     * @param getMethod get方法
     * @param getParams 请求的query参数，可以为null或长度为0
     * @return
     */
    public static void assemblyGetParams(GetMethod getMethod, Map<String, String> getParams) {
        if (getParams == null || getParams.size() == 0) {
            return;
        }
        NameValuePair[] nvPairs = new NameValuePair[getParams.size()];
        int idx = 0;
        for (Map.Entry<String, String> entry : getParams.entrySet()) {
            nvPairs[idx] = new NameValuePair(entry.getKey(), entry.getValue());
            ++idx;
        }
        // 该方法默认使用UTF-8编码
        getMethod.setQueryString(nvPairs);
    }

    /**
     * 组装拼接post请求参数
     *
     * @param postMethod post方法
     * @param postParams post参数，可以为null或长度为0
     */
    public static void assemblyPostParams(PostMethod postMethod, Map<String, String> postParams) {
        if (postParams == null || postParams.size() == 0) {
            return;
        }
        NameValuePair[] nvPairs = new NameValuePair[postParams.size()];
        int idx = 0;
        for (Map.Entry<String, String> entry : postParams.entrySet()) {
            nvPairs[idx] = new NameValuePair(entry.getKey().trim(), entry.getValue().trim());
            ++idx;
        }
        postMethod.addParameters(nvPairs);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, HTTPConstants.DEFAULT_CHARSET);
    }

    /**
     * 组装拼接请求头部
     *
     * @param httpMethod GET或POST等Http方法
     * @param headerMap  Http请求头部参数，可以为null或长度为0
     */
    public static void assemblyHeaders(HttpMethod httpMethod, Map<String, String> headerMap) {
        if (headerMap == null || headerMap.size() == 0) {
            return;
        }
        for (Map.Entry<String, String> entry : headerMap.entrySet()) {
            httpMethod.addRequestHeader(entry.getKey(), entry.getValue());
        }
    }

}
