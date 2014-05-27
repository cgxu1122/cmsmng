package com.ifhz.core.service.statistics.impl;

import com.ifhz.core.adapter.CounterUploadLogAdapter;
import com.ifhz.core.adapter.DeviceProcessLogAdapter;
import com.ifhz.core.adapter.LogCountAdapter;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.po.DeviceProcessLog;
import com.ifhz.core.po.LogCount;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.statistics.LogCountService;
import com.ifhz.core.service.statistics.handle.BeanConvertHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lm on 14-5-22.
 */
@Service("logCountService")
public class LogCountServiceImpl implements LogCountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogCountServiceImpl.class);
    @Resource(name = "logCountAdapter")
    private LogCountAdapter logCountAdapter;

    @Override
    public LogCount getById(Long id) {
        return logCountAdapter.getById(id);
    }

    @Override
    public List<LogCount> queryByVo(LogCount record) {
        return null;
    }

    @Override
    public int insert(LogCount record) {
        return logCountAdapter.insert(record);
    }

    @Override
    public int update(LogCount record) {
        return logCountAdapter.update(record);
    }

    @Override
    public int delete(LogCount record) {
        return logCountAdapter.delete(record);
    }


    // @Resource(name = "logCountAdapter")
    // private LogCountAdapter logCountAdapter;
    @Resource(name = "deviceProcessLogAdapter")
    private DeviceProcessLogAdapter deviceProcessLogAdapter;
    @Resource(name = "counterUploadLogAdapter")
    private CounterUploadLogAdapter counterUploadLogAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "sqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;

    public final int pageLogNum = 2;

    //logCounts存放统计信息。md5(model_name+channel_id+device_id+batch_code+count_date)作为健，值为统计对象
    public final Map<String, LogCount> logCounts = new HashMap();

    public void countLogByDate(Date startDate, Date endDate) {
        deviceCountLog(startDate, endDate);
        counterCountLog(startDate, endDate);
        //对统计日期是昨天进行批量插入
        Set set = logCounts.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            LogCount lc = logCountAdapter.getByProcessKey(key);
            LogCount histLogCount = logCounts.get(key);
            //如果数据库无此统计记录，则做插入
            if (lc == null) {
                logCountAdapter.insert(histLogCount);
            } else {//如果数据库存在此记录，则对相关统计数加上后面的增量
                lc.setProcessDayCount(lc.getProcessDayCount() + histLogCount.getProcessDayCount());
                lc.setDeviceUploadDayCount(lc.getDeviceUploadDayCount() + histLogCount.getDeviceUploadDayCount());
                lc.setAllCount(lc.getAllCount() + histLogCount.getAllCount());
                lc.setActiveCount(lc.getActiveCount() + histLogCount.getActiveCount());
                lc.setNonActiveCount(lc.getNonActiveCount() + histLogCount.getNonActiveCount());
                lc.setNonActiveReplaceCount(lc.getNonActiveReplaceCount() + histLogCount.getNonActiveReplaceCount());
                lc.setNonActiveUninstallCount(lc.getNonActiveUninstallCount() + histLogCount.getNonActiveUninstallCount());
                lc.setCounterUploadDayCount(lc.getCounterUploadDayCount() + histLogCount.getCounterUploadDayCount());
                logCountAdapter.update(lc);
            }
        }
    }

    /*
           liming
        */
    public void deviceCountLog(Date startDate, Date endDate) {
        //获取统计表名
        String tableName = splitTableService.getTableNameForDeviceByNow(startDate);
        //获取昨天加工设备流水总数
        Map tab = new HashMap();
        tab.put("tableName", tableName);
        tab.put("startDate", startDate);
        tab.put("endDate", endDate);
        long countlog = deviceProcessLogAdapter.getLogCountByTime(tab);
        //总页数
        long countPageNum = countlog / pageLogNum + 1;
        //分页提取流水表
        for (int i = 0; i < countPageNum; i++) {
            long startNum = pageLogNum * i;
            long endNum = pageLogNum * (i + 1);
            Map pageParms = new HashMap();
            pageParms.put("startNum", startNum);
            pageParms.put("endNum", endNum);
            pageParms.put("tableName", tableName);
            pageParms.put("startDate", startDate);
            pageParms.put("endDate", endDate);

            //提取流水
            List<DeviceProcessLog> list = deviceProcessLogAdapter.getDeviceProcessLog(pageParms);
            //每页流水处理
            for (DeviceProcessLog dpl : list) {
                //针对以下列进行MD5加密model_name+channel_id+device_id+batch_code+process_date
                String p = "" + dpl.getModelName() + dpl.getChannelId() + dpl.getDeviceCode() + dpl.getBatchCode() + DateFormatUtils.convertYYYYMMDD(dpl.getProcessTime()).getTime();
                String key = DesencryptUtils.md5Str(p);
                LogCount lc = logCounts.get(key);
                if (lc == null) {
                    //没有新建一条统计信息
                    try {
                        BeanConvertHandler.convertDeviceBean(dpl);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    lc = BeanConvertHandler.instDeviceLogCount(dpl, "processTime", key);
                }
                lc.setProcessDayCount(lc.getProcessDayCount() + 1);
                Date pt = DateFormatUtils.convertYYYYMMDD(dpl.getProcessTime());
                //假如上传时间和加工时间相同时，上传数加1
                if ((pt.getDate() == dpl.getCreateTime().getDate()) && (pt.getMonth() == dpl.getCreateTime().getDate())) {
                    lc.setDeviceUploadDayCount(lc.getDeviceUploadDayCount() + 1);
                } else {
                    //假如上传日期和加工日期不同时，在统计时间是上传时间的记录中，上传数加1
                    Date createTime = dpl.getCreateTime();
                    createTime.setHours(0);
                    createTime.setMinutes(0);
                    createTime.setSeconds(0);
                    String p2 = "" + dpl.getModelName() + dpl.getChannelId() + dpl.getDeviceCode() + dpl.getBatchCode() + createTime.getTime();
                    String key2 = DesencryptUtils.md5Str(p2);
                    LogCount lc2 = logCounts.get(key2);
                    if (lc2 == null) {
                        //没有新建一条统计信息
                        try {
                            BeanConvertHandler.convertDeviceBean(dpl);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        lc2 = BeanConvertHandler.instDeviceLogCount(dpl, "createTime", key2);
                    }
                    lc2.setDeviceUploadDayCount(lc2.getDeviceUploadDayCount() + 1);
                    logCounts.put(key2, lc2);
                }
                logCounts.put(key, lc);
            }

        }


    }

    //计数器流水统计
    public void counterCountLog(Date startDate, Date endDate) {
        //获取统计表名
        String tableName = splitTableService.getTableNameForCounterByNow(startDate);
        System.out.println("tableName=" + tableName);
        Map tab = new HashMap();
        tab.put("tableName", tableName);
        tab.put("startDate", startDate);
        tab.put("endDate", endDate);
        //获取昨天计数器流水总数
        long countlog = counterUploadLogAdapter.getLogCountByTime(tab);
        //总页数
        long countPageNum = countlog / pageLogNum + 1;
        //分页提取流水表
        for (int i = 0; i < countPageNum; i++) {
            int startNum = pageLogNum * i;
            int endNum = pageLogNum * (i + 1);
            Map pageParms = new HashMap();
            pageParms.put("startNum", startNum);
            pageParms.put("endNum", endNum);
            pageParms.put("tableName", tableName);
            pageParms.put("startDate", startDate);
            pageParms.put("endDate", endDate);
            //提取流水
            List<CounterUploadLog> list = counterUploadLogAdapter.getCounterUploadLog(pageParms);
            //每页流水处理
            for (CounterUploadLog cul : list) {
                //针对以下列进行MD5加密model_name+channel_id+device_id+batch_code+process_date
                String p = "" + cul.getModelName() + cul.getChannelId() + cul.getDeviceCode() + cul.getBatchCode() + DateFormatUtils.convertYYYYMMDD(cul.getProcessTime()).getTime();
                String key = DesencryptUtils.md5Str(p);
                LogCount lc = logCounts.get(key);
                if (lc == null) {
                    //没有新建一条统计信息
                    try {
                        BeanConvertHandler.convertCounterBean(cul);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    lc = BeanConvertHandler.instCounterLogCount(cul, "processTime", key);
                }
                lc.setAllCount(lc.getAllCount() + 1);
                String active = cul.getActive();
                if ("1".equals(active)) lc.setActiveCount(lc.getActiveCount() + 1);
                else if ("2".equals(active)) {
                    lc.setNonActiveCount(lc.getNonActiveCount() + 1);
                    lc.setNonActiveReplaceCount(lc.getNonActiveReplaceCount() + 1);
                } else if ("3".equals(active)) {
                    lc.setNonActiveCount(lc.getNonActiveCount() + 1);
                    lc.setNonActiveUninstallCount(lc.getNonActiveUninstallCount() + 1);
                }
                Date pt = DateFormatUtils.convertYYYYMMDD(cul.getProcessTime());
                //假如上传时间和加工时间相同时，上传数加1
                if ((pt.getDate() == cul.getCreateTime().getDate()) && (pt.getMonth() == cul.getCreateTime().getDate())) {
                    lc.setDeviceUploadDayCount(lc.getDeviceUploadDayCount() + 1);
                } else {
                    //假如上传日期和加工日期不同时，在统计时间是上传时间的记录中，上传数加1
                    Date createTime = cul.getCreateTime();
                    createTime.setHours(0);
                    createTime.setMinutes(0);
                    createTime.setSeconds(0);
                    String p2 = "" + cul.getModelName() + cul.getChannelId() + cul.getDeviceCode() + cul.getBatchCode() + createTime.getTime();
                    String key2 = DesencryptUtils.md5Str(p2);
                    LogCount lc2 = logCounts.get(key2);
                    if (lc2 == null) {
                        //没有新建一条统计信息
                        try {
                            BeanConvertHandler.convertCounterBean(cul);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }
                        lc2 = BeanConvertHandler.instCounterLogCount(cul, "createTime", key2);
                    }
                    lc2.setCounterUploadDayCount(lc2.getCounterUploadDayCount() + 1);
                    logCounts.put(key2, lc2);
                }
                logCounts.put(key, lc);
            }

        }

    }

    public static void main(String[] args) {
        long yesterdayNum = new Date().getTime() - 24 * 60 * 60 * 1000;
        Date yesterday = new Date(yesterdayNum);
        System.out.println(1900 + yesterday.getYear() + "-" + (yesterday.getMonth() + 1) + "-" + yesterday.getDate());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
        Date today = c.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(today));
        Date d2 = DateFormatUtils.convertShort("2014-09-09");
        System.out.println(d2.getYear() + "-" + d2.getMonth() + "-" + d2.getDate());

    }
}
