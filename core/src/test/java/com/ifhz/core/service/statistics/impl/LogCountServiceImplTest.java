package com.ifhz.core.service.statistics.impl;

import com.ifhz.core.service.api.CounterUploadLogService;
import com.ifhz.core.service.api.DeviceProcessLogService;
import com.ifhz.core.service.statistics.LogCountService;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lm on 14-5-20.
 */
public class LogCountServiceImplTest extends BaseTest {
    @Resource(name = "logCountService")
    private LogCountService logCountService;
    @Resource(name = "deviceProcessLogService")
    private DeviceProcessLogService deviceProcessLogService;
    @Resource(name = "counterUploadLogService")
    private CounterUploadLogService counterUploadLogService;
    /*
    @Test
    public void testAutoCountLog() throws Exception {

        Date startDate = new Date();
        startDate.setDate(17);
        startDate.setHours(13);
        startDate.setMinutes(0);
        startDate.setSeconds(0);

        Date endDate = new Date();
        endDate.setDate(23);
        endDate.setHours(15);
        endDate.setMinutes(0);
        endDate.setSeconds(0);
        logCountService.countLogByDate(startDate, endDate);

    }
    @Test
    public void testPartnerQuery() throws Exception {
        Map pars=new HashMap();
       // pars.put("modleName",modleName);
        pars.put("startDate", DateFormatUtils.convertYYYYMMDD("2014-05-19"));
        pars.put("endDate", DateFormatUtils.convertYYYYMMDD("2014-05-20"));
        pars.put("groupId",Long.parseLong("1"));

        Pagination page = new Pagination();
        page.setPageSize(10);
        page.setCurrentPage(1);

        List<Map<String,Object>> list = logCountService.partnerQuery(page, pars);


    }

    @Test
    public void tesQueryDeviceProcessLog() throws Exception {
        DeviceProcessLog pars = new DeviceProcessLog();
        pars.setProcessTime("2014-05-19");
        pars.setDeviceCode("dc1");
        pars.setModelName("mn1");
        pars.setGroupId(1l);
        pars.setChannelId("10");
        // pars.setTableName("TY_DEVICE_PROCESS_LOG_20142");
        List<DeviceProcessLog> list = deviceProcessLogService.queryDeviceProcessLog(pars);
        for (DeviceProcessLog dpl : list) {
            System.out.println(dpl.getImei());
        }
        System.out.println(list.size());

    }
    @Test
    public void tesQueryArriveImei() throws Exception {
        Map pars=new HashMap();
         pars.put("modleName","mn1");
        pars.put("processTime", "2014-05-19");
        //List actives=new ArrayList();actives.add(2);actives.add(3);
        int[] actives={2,3};
        pars.put("active",actives );

        pars.put("groupId", Long.parseLong("1"));
        pars.put("tableName","TY_COUNTER_UPLOAD_LOG_20142");

        List<Map<String, Object>>list = counterUploadLogService.queryArriveImei(pars);
        for (Map dpl : list) {
            System.out.println(dpl.get("imei")+"\t"+dpl.get("modleName"));

        }
        System.out.println(list.size());
    }


*/
    @Test
    public void tesQueryUploadImei() throws Exception {
        Map pars = new HashMap();
        pars.put("modleName", "mn1");
        pars.put("createTime", "2014-05-19");
        pars.put("groupId", Long.parseLong("1"));
        pars.put("tableName", "TY_COUNTER_UPLOAD_LOG_20142");

        List<Map<String, Object>> list = counterUploadLogService.queryUploadImei(pars);
        for (Map dpl : list) {
            System.out.println(dpl.get("imei") + "\t" + dpl.get("modleName") + "\t" + dpl.get("createTime"));
        }
        System.out.println(list.size());
    }
}
