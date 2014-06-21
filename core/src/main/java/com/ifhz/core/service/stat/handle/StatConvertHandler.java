package com.ifhz.core.service.stat.handle;

import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ProductStat;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:56
 */
public class StatConvertHandler {


    public static LogStat initDataStat(DataLog dataLog) {
        LogStat logStat = new LogStat();
        logStat.setUa(dataLog.getUa());
        logStat.setBatchCode(dataLog.getBatchCode());
        logStat.setDeviceCode(dataLog.getDeviceCode());
        logStat.setModelName(dataLog.getModelName());
        logStat.setChannelId(dataLog.getChannelId());
        logStat.setProcessDate(dataLog.getProcessTime());

        logStat.setDevicePrsDayNum(0L);
        logStat.setDevicePrsDayNum(0L);
        logStat.setPrsActiveTotalNum(0L);
        logStat.setPrsActiveValidNum(0L);
        logStat.setPrsActiveInvalidNum(0L);
        logStat.setPrsInvalidReplaceNum(0L);
        logStat.setPrsInvalidUninstallNum(0L);
        logStat.setCounterUpdDayNum(0L);
   /*     //地包渠道数据需要设置劳务公司ID
        if (logStat.getGroupId() == CommonConstants.CHANNAL_DIBAO && laowuId != null) {
            logStat.setLaowuId(laowuId);
        }*/

        return logStat;
    }

    //针对以下列进行MD5加密UA + ChannelId + DeviceCode + BatchCode + ProcessTime
    public static String getMd5KeyForLogStat(DataLog dataLog) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(dataLog.getUa());
        buffer.append(dataLog.getChannelId());
        buffer.append(dataLog.getDeviceCode());
        buffer.append(dataLog.getBatchCode());
        buffer.append(DateFormatUtils.formatDate(dataLog.getProcessTime(), GlobalConstants.DATE_FORMAT_DPT));

        return DesencryptUtils.md5Str(buffer.toString());

    }

    public static ProductStat initProductStat(DataLog dataLog, Long productId) {
        ProductStat productStat = new ProductStat();
        productStat.setUa(dataLog.getUa());
        productStat.setModelName(dataLog.getModelName());
        productStat.setProductId(productId);
        productStat.setGroupId(dataLog.getGroupId());
        productStat.setProcessDate(dataLog.getProcessTime());


        productStat.setProductPrsDayNum(0L);
        productStat.setProductUpdDayNum(0L);
        productStat.setPrsActiveTotalNum(0L);
        productStat.setPrsActiveValidNum(0L);
        productStat.setPrsActiveInvalidNum(0L);
        productStat.setPrsInvalidReplaceNum(0L);
        productStat.setPrsInvalidUninstallNum(0L);
        productStat.setCounterProductDayNum(0L);

        productStat.setMd5Key(getMd5KeyForProductStat(dataLog, productId));

        return productStat;
    }

    //针对以下列进行MD5加密UA + GrroupId + productId + ProcessTime
    public static String getMd5KeyForProductStat(DataLog dataLog, Long productId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(dataLog.getUa());
        buffer.append(dataLog.getGroupId());
        buffer.append(productId);
        buffer.append(DateFormatUtils.formatDate(dataLog.getProcessTime(), GlobalConstants.DATE_FORMAT_DPT));

        return DesencryptUtils.md5Str(buffer.toString());

    }


    public static long getPageNum(long totalCount, long pageSize) {
        long pageNum;
        if (totalCount % pageSize > 0) {
            pageNum = totalCount / pageSize + 1;
        } else {
            pageNum = totalCount / pageSize;
        }

        return pageNum;
    }
}
