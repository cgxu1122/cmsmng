package com.ifhz.core.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ifhz.core.service.cache.DictInfoCacheService;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.common.bean.SplitTableBean;
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
    private static String TablePrefix = "TY_DATA_LOG_";

    @Resource(name = "dictInfoCacheService")
    private DictInfoCacheService dictInfoCacheService;


    @Override
    public String getCurrentTableName(Date now) {
        String tableName = TablePrefix + getTableSuffix(now);
        LOGGER.info("{},{}", JSON.toJSONString(now), tableName);
        return tableName;
    }

    @Override
    public List<String> getTableNameList(Date now) {
        List<String> list = getQueryTableList(TablePrefix, now);
        LOGGER.info("{},{}", JSON.toJSONString(now), list);
        return list;
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


    private List<String> getQueryTableList(String prefix, Date now) {
        List<String> tableList = Lists.newArrayList();
        Date initDate = dictInfoCacheService.getSystemInitDate();
        SplitTableBean bean = new SplitTableBean(initDate, now);
        List<String> suffixList = bean.getSuffixList();
        for (String suffix : suffixList) {
            tableList.add(prefix + suffix);
        }

        return tableList;
    }
}
