package com.ifhz.core.service.export.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/6/18.
 */
public class BaseExportModel {
    private List<? extends Object> dataList;
    private Map<String, String> titleMap;

    public List<? extends Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<? extends Object> dataList) {
        this.dataList = dataList;
    }

    public Map<String, String> getTitleMap() {
        return titleMap;
    }

    public void setTitleMap(Map<String, String> titleMap) {
        this.titleMap = titleMap;
    }
}
