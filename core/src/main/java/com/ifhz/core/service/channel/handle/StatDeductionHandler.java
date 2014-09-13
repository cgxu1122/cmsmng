package com.ifhz.core.service.channel.handle;

import com.ifhz.core.constants.DeductionType;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.stat.StatDeduction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/13
 * Time: 19:44
 */
public final class StatDeductionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatDeductionHandler.class);

    private static final Integer DB_BASIC_NUM = 40;
    private static final Double DB_PERCENTAGE = 0.10;
    private static final Integer QT_BASIC_NUM = 50;
    private static final Double QT_PERCENTAGE = 0.10;

    public static StatDeduction initStatDeductionForDB(ChannelInfo channelInfo) {
        StatDeduction result = new StatDeduction();
        result.setChannelId(channelInfo.getChannelId());
        result.setGroupId(channelInfo.getGroupId());
        result.setBasicNum(DB_BASIC_NUM);
        result.setPercentage(DB_PERCENTAGE);
        result.setType(DeductionType.Normal.dbValue);
        result.setCreateTime(new Date());
        result.setUpdateTime(new Date());

        return result;
    }

    public static StatDeduction initStatDeductionForQT(ChannelInfo channelInfo) {
        StatDeduction result = new StatDeduction();
        result.setChannelId(channelInfo.getChannelId());
        result.setGroupId(channelInfo.getGroupId());
        result.setBasicNum(QT_BASIC_NUM);
        result.setPercentage(QT_PERCENTAGE);
        result.setType(DeductionType.Normal.dbValue);
        result.setCreateTime(new Date());
        result.setUpdateTime(new Date());

        return result;
    }
}
