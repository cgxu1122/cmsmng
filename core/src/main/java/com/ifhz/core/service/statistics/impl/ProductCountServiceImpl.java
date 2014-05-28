package com.ifhz.core.service.statistics.impl;

import com.ifhz.core.adapter.CounterUploadLogAdapter;
import com.ifhz.core.adapter.DeviceProcessLogAdapter;
import com.ifhz.core.adapter.ProductCountAdapter;
import com.ifhz.core.base.commons.codec.DesencryptUtils;
import com.ifhz.core.base.commons.constants.CommonConstants;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.mapper.BatchProductRefMapper;
import com.ifhz.core.po.BatchProductRef;
import com.ifhz.core.po.CounterUploadLog;
import com.ifhz.core.po.DeviceProcessLog;
import com.ifhz.core.po.ProductCount;
import com.ifhz.core.service.common.SplitTableService;
import com.ifhz.core.service.statistics.ProductCountService;
import com.ifhz.core.service.statistics.handle.BeanConvertHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by lm on 14-5-21.
 */
@Service("productCountService")
public class ProductCountServiceImpl implements ProductCountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCountServiceImpl.class);
    @Resource(name = "productCountAdapter")
    private ProductCountAdapter productCountAdapter;

    @Override
    public ProductCount getById(Long id) {
        return productCountAdapter.getById(id);
    }

    @Override
    public List<ProductCount> queryByVo(ProductCount record) {
        return productCountAdapter.queryByVo(record);
    }

    @Override
    public int insert(ProductCount record) {
        return productCountAdapter.insert(record);
    }

    @Override
    public int update(ProductCount record) {
        return productCountAdapter.update(record);
    }

    @Resource(name = "deviceProcessLogAdapter")
    private DeviceProcessLogAdapter deviceProcessLogAdapter;
    @Resource(name = "counterUploadLogAdapter")
    private CounterUploadLogAdapter counterUploadLogAdapter;
    @Resource(name = "splitTableService")
    private SplitTableService splitTableService;
    @Resource(name = "batchProductRefMapper")
    private BatchProductRefMapper batchProductRefMapper;

    @Override
    public int delete(ProductCount record) {
        return productCountAdapter.delete(record);
    }

    //logCounts存放统计信息。md5(ua+productId+groupId+countTime)作为健，值为统计对象
    public final Map<String, ProductCount> logCounts = new HashMap();
    public final int pageLogNum = 2;

    @Override
    public void countProductLogByDate(Date startDate, Date endDate) {
        deviceCountLog(startDate, endDate);
        counterCountLog(startDate, endDate);
        //对统计日期是昨天进行批量插入
        Set set = logCounts.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            ProductCount pc = productCountAdapter.getByProcessKey(key);
            ProductCount histLogCount = logCounts.get(key);
            //如果数据库无此统计记录，则做插入
            if (pc == null) {
                productCountAdapter.insert(histLogCount);
            } else {//如果数据库存在此记录，则对相关统计数加上后面的增量
                pc.setProcessDayCount(pc.getProcessDayCount() + histLogCount.getProcessDayCount());

                pc.setAllCount(pc.getAllCount() + histLogCount.getAllCount());
                pc.setActiveCount(pc.getActiveCount() + histLogCount.getActiveCount());
                pc.setNonActiveCount(pc.getNonActiveCount() + histLogCount.getNonActiveCount());
                pc.setNonActiveReplaceCount(pc.getNonActiveReplaceCount() + histLogCount.getNonActiveReplaceCount());
                pc.setNonActiveUninstallCount(pc.getNonActiveUninstallCount() + histLogCount.getNonActiveUninstallCount());

                productCountAdapter.update(pc);
            }
        }
    }

    /*
              liming
              统计加工流水
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
                //没有新建一条统计信息
                try {
                    BeanConvertHandler.convertDeviceBean(dpl);
                } catch (Exception e) {
                    LOGGER.error("加工设备数据转化成流水有误", e);
                    continue;
                }
                //查询该批次所有产品列表
                List<BatchProductRef> bprs = batchProductRefMapper.queryByBatchCode(dpl.getBatchCode());
                //每个产品加工数加1
                for (BatchProductRef bpr : bprs) {
                    //针对以下列进行MD5加密(ua+productId+groupId+countTime)作为健，值为统计对象
                    String p = "" + bpr.getProductId() + dpl.getUa() + dpl.getGroupId() + DateFormatUtils.convertYYYYMMDD(dpl.getProcessTime()).getTime();
                    String key = DesencryptUtils.md5Str(p);
                    ProductCount pc = logCounts.get(key);
                    if (pc == null) {
                        pc = BeanConvertHandler.instDeviceProductCount(dpl, bpr.getProductId(), key);
                    }
                    pc.setProcessDayCount(pc.getProcessDayCount() + 1);
                    logCounts.put(key, pc);
                }
            }
        }
    }

    //计数器流水统计
    public void counterCountLog(Date startDate, Date endDate) {
        //获取统计表名
        String tableName = splitTableService.getTableNameForCounterByNow(startDate);
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
                //没有新建一条统计信息
                try {
                    BeanConvertHandler.convertCounterBean(cul);
                } catch (Exception e) {
                    LOGGER.error("计数器数据转化成流水有误", e);
                    continue;
                }
                //查询该批次所有产品列表
                List<BatchProductRef> bprs = batchProductRefMapper.queryByBatchCode(cul.getBatchCode());
                //每个产品加工数加1
                for (BatchProductRef bpr : bprs) {
                    //针对以下列进行MD5加密(ua+productId+groupId+countTime)作为健，值为统计对象
                    String p = "" + bpr.getProductId() + cul.getUa() + cul.getGroupId() + DateFormatUtils.convertYYYYMMDD(cul.getProcessTime()).getTime();
                    String key = DesencryptUtils.md5Str(p);
                    ProductCount pc = logCounts.get(key);
                    if (pc == null) {
                        pc = BeanConvertHandler.instCounterProductCount(cul, bpr.getProductId(), key);
                    }
                    pc.setProcessDayCount(pc.getProcessDayCount() + 1);
                    pc.setAllCount(pc.getAllCount() + 1);
                    String active = cul.getActive();
                    if (CommonConstants.ARRIVE_ACTIVE.equals(active)) pc.setActiveCount(pc.getActiveCount() + 1);
                    else if (CommonConstants.ARRIVE_NonActiveReplace.equals(active)) {
                        pc.setNonActiveCount(pc.getNonActiveCount() + 1);
                        pc.setNonActiveReplaceCount(pc.getNonActiveReplaceCount() + 1);
                    } else if (CommonConstants.ARRIVE_NonActiveUninstall.equals(active)) {
                        pc.setNonActiveCount(pc.getNonActiveCount() + 1);
                        pc.setNonActiveUninstallCount(pc.getNonActiveUninstallCount() + 1);
                    }
                    logCounts.put(key, pc);
                }


            }

        }

    }
}
