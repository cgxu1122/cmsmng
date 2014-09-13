package com.ifhz.core.service.stat.handle;

import com.ifhz.core.po.stat.*;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/13
 * Time: 23:01
 */
public final class DeductionHandler {

    public static LogArriveStatTemp initLogArriveStatTemp(LogArriveStat record, StatDeduction deduction) {
        LogArriveStatTemp result = new LogArriveStatTemp();
        result.setId(record.getId());
        result.setChannelId(record.getChannelId());
        result.setGroupId(record.getGroupId());
        result.setUa(record.getUa());
        result.setCreateTime(record.getCreateTime());
        result.setLaowuId(record.getLaowuId());
        result.setStatDate(record.getStatDate());

        result.setValidNum(getDeductionNum(record.getValidNum(), deduction));
        result.setInvalidNum(getDeductionNum(record.getInvalidNum(), deduction));

        return result;
    }

    public static ProductArriveStatTemp initProductArriveStatTemp(ProductArriveStat record, StatDeduction deduction) {
        ProductArriveStatTemp result = new ProductArriveStatTemp();
        result.setId(record.getId());
        result.setChannelId(record.getChannelId());
        result.setGroupId(record.getGroupId());
        result.setUa(record.getUa());
        result.setCreateTime(record.getCreateTime());
        result.setStatDate(record.getStatDate());

        result.setValidNum(getDeductionNum(record.getValidNum(), deduction));
        result.setInvalidNum(getDeductionNum(record.getInvalidNum(), deduction));

        return result;
    }


    private static long getDeductionNum(Long input, StatDeduction deduction) {
        if (input == 0) {
            return 0L;
        }
        if (deduction.getPercentage() == 100) {
            return 0L;
        }
        if (deduction.getPercentage() == 0) {
            return input;
        }
        if (input < deduction.getBasicNum()) {
            return input;
        }

        return (int) Math.floor(input * (deduction.getPercentage() / 100));
    }
}
