package com.ifhz.core.service.api.impl;

import com.ifhz.core.service.api.ApiUploadService;
import com.test.BaseTest;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiUploadServiceImplTest extends BaseTest {

    @Autowired
    private ApiUploadService apiUploadService;

    @Test
    public void testSaveCounterDataLog() throws Exception {
        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = connectionManager.getParams();
        params.setDefaultMaxConnectionsPerHost(100);
        params.setConnectionTimeout(20000);
        params.setSoTimeout(2000);

        HttpClientParams clientParams = new HttpClientParams();
        clientParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
        HttpClient httpClient = new HttpClient(clientParams, connectionManager);


    }

    @Test
    public void testSaveDeviceDataLog() throws Exception {

    }

    @Test
    public void testBatchSave() throws Exception {

    }
}