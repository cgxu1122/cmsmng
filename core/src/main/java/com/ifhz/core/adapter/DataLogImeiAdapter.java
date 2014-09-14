package com.ifhz.core.adapter;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/14
 * Time: 11:17
 */
public interface DataLogImeiAdapter {

    public List<String> getLogInstall(Map<String, Object> paramList);

    public List<String> getLogInstallArrive(Map<String, Object> paramList);

    public List<String> getLogArrive(Map<String, Object> paramList);

    public List<String> getLogArriveTemp(Map<String, Object> paramList);

    public List<String> getProductInstall(Map<String, Object> paramList);

    public List<String> getProductInstallArrive(Map<String, Object> paramList);

    public List<String> getProductArrive(Map<String, Object> paramList);

    public List<String> getProductArriveTemp(Map<String, Object> paramList);
}
