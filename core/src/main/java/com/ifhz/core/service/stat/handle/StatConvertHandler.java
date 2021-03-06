package com.ifhz.core.service.stat.handle;

import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.LogStat;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.po.stat.ProductInstallStat;

import java.util.Date;

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
        logStat.setGroupId(dataLog.getGroupId());
        logStat.setCreateTime(new Date());

        logStat.setDevicePrsDayNum(0L);
        logStat.setDevicePrsDayNum(0L);
        logStat.setPrsActiveTotalNum(0L);
        logStat.setPrsActiveValidNum(0L);
        logStat.setPrsActiveInvalidNum(0L);
        logStat.setPrsInvalidReplaceNum(0L);
        logStat.setPrsInvalidUninstallNum(0L);
        logStat.setPrsInvalidUnAndReNum(0L);
        logStat.setVersion(0);

        return logStat;
    }

    //针对以下列进行MD5加密UA + ChannelId + DeviceCode + BatchCode + ProcessTime

    /**
     * MD5加密 UA + ChannelId + DeviceCode  + ProcessTime
     * 数据统计表中 数据加密 表中唯一
     *
     * @param dataLog
     * @return
     */
    public static String getMd5KeyForLogStat(DataLog dataLog) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(dataLog.getUa());
        buffer.append(",");
        buffer.append(dataLog.getChannelId());
        buffer.append(",");
        buffer.append(dataLog.getDeviceCode());
        buffer.append(",");
        buffer.append(DateFormatUtils.formatDate(dataLog.getProcessTime(), GlobalConstants.DATE_FORMAT_DPT));

        return DesencryptUtils.md5Str(buffer.toString());

    }

    /**
     * MD5加密 UA + GrroupId + productId + ProcessTime
     * 产品数据统计表中 数据加密 表中唯一
     *
     * @param dataLog
     * @param productId
     * @return
     */
    public static String getMd5KeyForProductStat(DataLog dataLog, Long productId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(dataLog.getUa());
        buffer.append(",");
        buffer.append(dataLog.getChannelId());
        buffer.append(",");
        buffer.append(productId);
        buffer.append(",");
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
        productStat.setBatchCode(dataLog.getBatchCode());
        productStat.setCreateTime(new Date());


        productStat.setProductPrsDayNum(0L);
        productStat.setProductUpdDayNum(0L);
        productStat.setPrsActiveTotalNum(0L);
        productStat.setPrsActiveValidNum(0L);
        productStat.setPrsActiveInvalidNum(0L);
        productStat.setPrsInvalidReplaceNum(0L);
        productStat.setPrsInvalidUninstallNum(0L);
        productStat.setPrsInvalidUnAndReNum(0L);
        productStat.setVersion(0);

        return productStat;
    }

    /**
     * MD5加密 UA + GrroupId + productId + ProcessTime
     * 产品数据统计表中 数据加密 表中唯一
     *
     * @param dataLog
     * @param productId
     * @return
     */
    public static String getMd5KeyForProductInstallStat(DataLog dataLog, Long productId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(dataLog.getUa());
        buffer.append(",");
        buffer.append(dataLog.getChannelId());
        buffer.append(",");
        buffer.append(productId);
        buffer.append(",");
        buffer.append(DateFormatUtils.formatDate(dataLog.getProcessTime(), GlobalConstants.DATE_FORMAT_DPT));

        return DesencryptUtils.md5Str(buffer.toString());

    }

    public static ProductInstallStat initProductInstallStat(DataLog dataLog, Long productId) {
        ProductInstallStat result = new ProductInstallStat();
        result.setUa(dataLog.getUa());
        result.setModelName(dataLog.getModelName());
        result.setProductId(productId);
        result.setGroupId(dataLog.getGroupId());
        result.setChannelId(dataLog.getChannelId());
        result.setStatDate(dataLog.getProcessTime());
        result.setBatchCode(dataLog.getBatchCode());
        result.setCreateTime(new Date());

        result.setInstallTotalNum(0L);
        result.setTotalNum(0L);
        result.setValidNum(0L);
        result.setInvalidNum(0L);
        result.setReplaceNum(0L);
        result.setUninstallNum(0L);
        result.setUnAndReNum(0L);
        result.setVersion(0);

        return result;
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
