package com.ifhz.core.service.stat;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.stat.ProductInstallStat;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 23:52
 */
public interface ProductInstallStatService {

    public boolean statProductProcess(DataLog record);

    public boolean statProductInstallForArrive(DataLog record);

    public boolean statProductInstall(DataLog record);

    public boolean statProductArrive(DataLog record);

    public List<ProductInstallStat> queryByVo(Pagination pagination, ProductInstallStat record);
}
