package com.ifhz.core.adapter.impl;

import com.ifhz.core.adapter.DataLogImeiAdapter;
import com.ifhz.core.mapper.DataLogImeiMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/9/14
 * Time: 11:18
 */
@Repository
public class DataLogImeiAdapterImpl implements DataLogImeiAdapter {

    @Resource
    private DataLogImeiMapper dataLogImeiMapper;


    @Override
    public List<String> getLogInstall(Map<String, Object> paramList) {
        return dataLogImeiMapper.getLogInstall(paramList);
    }

    @Override
    public List<String> getLogInstallArrive(Map<String, Object> paramList) {
        return dataLogImeiMapper.getLogInstallArrive(paramList);
    }

    @Override
    public List<String> getLogArrive(Map<String, Object> paramList) {
        return dataLogImeiMapper.getLogArrive(paramList);
    }

    @Override
    public List<String> getLogArriveTemp(Map<String, Object> paramList) {
        return dataLogImeiMapper.getLogArriveTemp(paramList);
    }

    @Override
    public List<String> getProductInstall(Map<String, Object> paramList) {
        return dataLogImeiMapper.getProductInstall(paramList);
    }

    @Override
    public List<String> getProductInstallArrive(Map<String, Object> paramList) {
        return dataLogImeiMapper.getProductInstallArrive(paramList);
    }

    @Override
    public List<String> getProductArrive(Map<String, Object> paramList) {
        return dataLogImeiMapper.getProductArrive(paramList);
    }

    @Override
    public List<String> getProductArriveTemp(Map<String, Object> paramList) {
        return dataLogImeiMapper.getProductArriveTemp(paramList);
    }
}
