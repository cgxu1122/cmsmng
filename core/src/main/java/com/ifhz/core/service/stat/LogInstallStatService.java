package com.ifhz.core.service.stat;

import com.ifhz.core.po.DataLog;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 23:52
 */
public interface LogInstallStatService {


    public boolean statLogInstall(DataLog record);

    public boolean statLogInstallForArrive(DataLog record);
}
