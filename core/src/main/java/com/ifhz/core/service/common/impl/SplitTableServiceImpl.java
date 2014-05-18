package com.ifhz.core.service.common.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.service.cache.DictInfoCacheService;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.common.enums.SplitTableEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/18
 * Time: 12:48
 */
@Service("splitTableService")
public class SplitTableServiceImpl implements SplitTableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplitTableServiceImpl.class);
    private static String deviceTablePrefix = "TY_DEVICE_PROCESS_LOG_";
    private static String counterTablePrefix = "TY_COUNTER_UPLOAD_LOG_";

    @Resource(name = "dictInfoCacheService")
    private DictInfoCacheService dictInfoCacheService;


    public String getTableNameForDeviceByNow(Date now) {
        return deviceTablePrefix + getTableSuffix(now);
    }

    public String getTableNameForCounterByNow(Date now) {
        return counterTablePrefix + getTableSuffix(now);
    }

    private String getTableSuffix(Date now) {
        StringBuffer buff = new StringBuffer("");
        //获取当前时间年和月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        SplitTableEnums enums = SplitTableEnums.getByCalendarMonth(nowMonth);
        buff.append(nowYear).append(enums.SEASON);

        return buff.toString();
    }


    private List<String> getQueryTableList(Date now) {
        List<String> tableList = Lists.newArrayList();


        return tableList;
    }


    private List<String> getTableForYear(String prefix, int year, int startReason, int endReason) {
        List<String> result = Lists.newArrayList();
        for (int i = startReason; i <= endReason; i++) {
            result.add(prefix + year + i);
        }

        return result;
    }


}
