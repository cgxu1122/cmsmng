package com.ifhz.core.service.imei.impl;

import com.google.common.collect.Maps;
import com.ifhz.core.adapter.BatchInfoAdapter;
import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.commons.codec.OtherCodecUtils;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.bean.ImeiStatus;
import com.ifhz.core.service.api.handle.ModelHandler;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.cache.LocalDirCacheService;
import com.ifhz.core.service.imei.ZipUploadService;
import com.ifhz.core.service.imei.bean.XmlBean;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/2
 * Time: 16:20
 */
@Service("zipUploadService")
public class ZipUploadServiceImpl implements ZipUploadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUploadServiceImpl.class);

    @Resource(name = "localDirCacheService")
    private LocalDirCacheService localDirCacheService;
    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;
    @Resource(name = "apiUploadService")
    private ApiUploadService apiUploadService;
    private ModelInfoAdapter modelInfoAdapter;
    @Resource(name = "batchInfoAdapter")
    private BatchInfoAdapter batchInfoAdapter;


    @Log
    @Override
    public Map<ImeiStatus, Integer> processFile(String filePath, Long channelId, Date processDate) {
        Map<ImeiStatus, Integer> result = Maps.newHashMap();
        XStream stream = new XStream();
        if (StringUtils.isBlank(filePath) || channelId == null || processDate == null) {
            return result;
        }
        ChannelInfo channelInfo = channelInfoCacheService.getByChannelId(channelId);
        FileInputStream fis = null;
        ZipInputStream zis = null;
        try {
            ZipEntry entry = null;
            fis = new FileInputStream(filePath);
            zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipFile zipfile = new ZipFile(filePath);

            while ((entry = zis.getNextEntry()) != null) {
                try {
                    if (entry.isDirectory()) {
                        continue;
                    } else {
                        InputStream inputStream = zipfile.getInputStream(entry);
                        String originFileName = entry.getName();
                        String storeTempFilePath = localDirCacheService.storeTempFile(inputStream, originFileName);
                        if (StringUtils.isNotBlank(storeTempFilePath)) {
                            String fileContent = FileUtils.readFileToString(new File(storeTempFilePath), "UTF-8");
                            if (StringUtils.isNotBlank(fileContent)) {
                                String content = OtherCodecUtils.decode(fileContent);
                                LOGGER.info("xmlContent={}", content);
                                stream.processAnnotations(XmlBean.class);
                                XmlBean bean = (XmlBean) stream.fromXML(content.trim());
                                if (bean != null) {
                                    ImeiStatus status = null;
                                    try {
                                        DataLog dataLog = new DataLog();
                                        dataLog.setImei(bean.getImei());
                                        dataLog.setUa(ModelHandler.translateUa(bean.getUa()));
                                        dataLog.setProcessTime(processDate);
                                        dataLog.setChannelId(channelId);
                                        String batchCode = getBatchCode(bean);
                                        dataLog.setBatchCode(batchCode);
                                        dataLog.setDeviceCode("手工安装");
                                        dataLog.setDeviceUploadTime(new Date());
                                        if (channelInfo != null) {
                                            dataLog.setGroupId(channelInfo.getGroupId());
                                        }
                                        if (validExcelData(dataLog)) {
                                            status = apiUploadService.saveDeviceDataLog(dataLog);
                                        } else {
                                            status = ImeiStatus.Invalid;
                                        }
                                    } catch (Exception e) {
                                        LOGGER.error("saveDeviceDataLog error", e);
                                        status = ImeiStatus.Failure;
                                    }
                                    if (status != null) {
                                        if (result.containsKey(status)) {
                                            Integer count = result.get(status);
                                            result.put(status, count + 1);
                                        } else {
                                            result.put(status, 1);
                                        }
                                    }
                                }
                            }
                        } else {
                            LOGGER.info("ZipFile={} , store local file={}    failure", originFileName, storeTempFilePath);
                            continue;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("ZipFile parse error", e);
                    continue;
                }
            }
        } catch (Exception e) {
            LOGGER.error("processFile error", e);
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(zis);
        }

        return result;
    }

    @Log
    private boolean validExcelData(DataLog dataLog) {
        //验证非法数据
        if (StringUtils.isBlank(dataLog.getImei())) {
            return false;
        }
        if (StringUtils.isBlank(dataLog.getUa())) {
            return false;
        }
        if (StringUtils.isBlank(dataLog.getBatchCode())) {
            return false;
        }
        if (StringUtils.isBlank(dataLog.getDeviceCode())) {
            return false;
        }
        if (batchInfoAdapter.queryByGroupIdAndBatchCode(dataLog.getGroupId(), dataLog.getBatchCode()) == null) {
            return false;
        }

        return true;
    }


    @Log
    private String getBatchCode(XmlBean bean) {
        String result = "";
        if (bean != null) {
            String apkName;
            if (StringUtils.isNotBlank(bean.getApkSuccName())) {
                apkName = bean.getApkSuccName().trim();
            } else {
                apkName = bean.getApkFailName().trim();
            }
            if (StringUtils.isNotBlank(apkName)) {
                if (StringUtils.contains(apkName, "_")) {
                    result = StringUtils.substring(apkName, 0, StringUtils.indexOf(apkName, "_"));
                }
            }
        }
        LOGGER.info("getBatchCode result={}", result);
        return result;
    }
}
