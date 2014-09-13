package com.ifhz.core.service.channel.handle;

import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.commons.MapConfig;
import com.ifhz.core.constants.DeductionType;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.stat.StatDeduction;

import java.util.Date;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/13
 * Time: 19:44
 */
public final class StatDeductionHandler {

    private static final Integer DB_BASIC_NUM = MapConfig.getInt("db.basic.num", GlobalConstants.GLOBAL_CONFIG, 40);
    private static final Integer DB_PERCENTAGE = MapConfig.getInt("db.percentage", GlobalConstants.GLOBAL_CONFIG, 10);
    private static final Integer QT_BASIC_NUM = MapConfig.getInt("qt.basic.num", GlobalConstants.GLOBAL_CONFIG, 50);
    private static final Integer QT_PERCENTAGE = MapConfig.getInt("db.percentage", GlobalConstants.GLOBAL_CONFIG, 10);

    @Log
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

    @Log
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
