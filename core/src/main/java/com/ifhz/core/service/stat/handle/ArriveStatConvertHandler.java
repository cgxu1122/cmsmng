package com.ifhz.core.service.stat.handle;

import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.LogArriveStat;
import com.ifhz.core.po.stat.LogArriveStatTemp;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.po.stat.ProductArriveStatTemp;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:56
 */
public class ArriveStatConvertHandler {


    public static LogArriveStat initLogArriveStat(DataLog dataLog) {
        LogArriveStat logArriveStat = new LogArriveStat();
        logArriveStat.setUa(dataLog.getUa());
        logArriveStat.setModelName(dataLog.getModelName());
        logArriveStat.setChannelId(dataLog.getChannelId());
        logArriveStat.setArriveDate(dataLog.getCounterUploadTime());
        logArriveStat.setGroupId(dataLog.getGroupId());
        logArriveStat.setCreateTime(new Date());

        logArriveStat.setTotalNum(0L);
        logArriveStat.setValidNum(0L);
        logArriveStat.setInvalidNum(0L);
        logArriveStat.setReplaceNum(0L);
        logArriveStat.setUninstallNum(0L);
        logArriveStat.setUnAndReNum(0L);
        logArriveStat.setVersion(0);

        return logArriveStat;
    }

    public static LogArriveStatTemp convertLogArriveStatTemp(LogArriveStat record) {
        LogArriveStatTemp logArriveStatTemp = new LogArriveStatTemp();
        logArriveStatTemp.setId(record.getId());
        logArriveStatTemp.setUa(record.getUa());
        logArriveStatTemp.setModelName(record.getModelName());
        logArriveStatTemp.setChannelId(record.getChannelId());
        logArriveStatTemp.setGroupId(record.getGroupId());
        logArriveStatTemp.setArriveDate(record.getArriveDate());
        logArriveStatTemp.setCreateTime(record.getCreateTime());

        logArriveStatTemp.setTotalNum(record.getTotalNum());
        logArriveStatTemp.setValidNum(record.getValidNum());
        logArriveStatTemp.setInvalidNum(record.getInvalidNum());
        logArriveStatTemp.setReplaceNum(record.getReplaceNum());
        logArriveStatTemp.setUninstallNum(record.getUninstallNum());
        logArriveStatTemp.setUnAndReNum(record.getUnAndReNum());
        logArriveStatTemp.setVersion(record.getVersion());

        return logArriveStatTemp;
    }

    //针对以下列进行MD5加密UA + ChannelId + DeviceCode + BatchCode + ProcessTime

    /**
     * MD5加密 UA + ChannelId + DeviceCode  + CounterUploadTime
     * 数据统计表中 数据加密 表中唯一
     *
     * @param dataLog
     * @return
     */
    public static String getMd5KeyForLogArriveStat(DataLog dataLog) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(dataLog.getUa());
        buffer.append(",");
        buffer.append(dataLog.getChannelId());
        buffer.append(",");
        buffer.append(DateFormatUtils.formatDate(dataLog.getCounterUploadTime(), GlobalConstants.DATE_FORMAT_DPT));

        return DesencryptUtils.md5Str(buffer.toString());

    }

    /**
     * MD5加密 UA + ChannelId + productId + CounterUploadTime
     * 产品数据统计表中 数据加密 表中唯一
     *
     * @param dataLog
     * @param productId
     * @return
     */
    public static String getMd5KeyForProductArriveStat(DataLog dataLog, Long productId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(dataLog.getUa());
        buffer.append(",");
        buffer.append(dataLog.getChannelId());
        buffer.append(",");
        buffer.append(productId);
        buffer.append(",");
        buffer.append(DateFormatUtils.formatDate(dataLog.getCounterUploadTime(), GlobalConstants.DATE_FORMAT_DPT));

        return DesencryptUtils.md5Str(buffer.toString());

    }

    public static ProductArriveStat initProductArriveStat(DataLog dataLog, Long productId) {
        ProductArriveStat productArriveStat = new ProductArriveStat();
        productArriveStat.setUa(dataLog.getUa());
        productArriveStat.setModelName(dataLog.getModelName());
        productArriveStat.setProductId(productId);
        productArriveStat.setGroupId(dataLog.getGroupId());
        productArriveStat.setChannelId(dataLog.getChannelId());
        productArriveStat.setArriveDate(dataLog.getCounterUploadTime());
        productArriveStat.setCreateTime(new Date());

        productArriveStat.setTotalNum(0L);
        productArriveStat.setValidNum(0L);
        productArriveStat.setInvalidNum(0L);
        productArriveStat.setReplaceNum(0L);
        productArriveStat.setUninstallNum(0L);
        productArriveStat.setUnAndReNum(0L);
        productArriveStat.setVersion(0);

        return productArriveStat;
    }


    public static ProductArriveStatTemp convertProductArriveStatTemp(ProductArriveStat record) {
        ProductArriveStatTemp result = new ProductArriveStatTemp();
        result.setId(record.getId());
        result.setUa(record.getUa());
        result.setModelName(record.getModelName());
        result.setProductId(record.getProductId());
        result.setGroupId(record.getGroupId());
        result.setChannelId(record.getChannelId());
        result.setArriveDate(record.getArriveDate());
        result.setCreateTime(record.getCreateTime());

        result.setTotalNum(record.getTotalNum());
        result.setValidNum(record.getValidNum());
        result.setInvalidNum(record.getInvalidNum());
        result.setReplaceNum(record.getReplaceNum());
        result.setUninstallNum(record.getUninstallNum());
        result.setUnAndReNum(record.getUnAndReNum());
        result.setVersion(record.getVersion());

        return result;
    }
}
