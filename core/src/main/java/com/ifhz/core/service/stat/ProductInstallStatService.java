package com.ifhz.core.service.stat;

import com.ifhz.core.po.DataLog;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 23:52
 */
public interface ProductInstallStatService {

    public boolean statProductInstall(DataLog record);


    public boolean statProductInstallForArrive(DataLog record);
}
