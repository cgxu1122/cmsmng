package com.ifhz.core.service.imei;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/6
 * Time: 17:23
 */
public interface InstallImeiQueryService {

    public List<String> getLogInstall(List<Map<String, Object>> paramList);

    public List<String> getLogInstallArrive(List<Map<String, Object>> paramList);

    public List<String> getLogArrive(List<Map<String, Object>> paramList);

    public List<String> getLogArriveTemp(List<Map<String, Object>> paramList, Long totalCount);

    public List<String> getProductInstall(List<Map<String, Object>> paramList);

    public List<String> getProductInstallArrive(List<Map<String, Object>> paramList);

    public List<String> getProductArrive(List<Map<String, Object>> paramList);

    public List<String> getProductArriveTemp(List<Map<String, Object>> paramList, Long totalCount);

}
