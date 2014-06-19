package com.ifhz.core.service.export;


import com.ifhz.core.service.export.model.BaseExportModel;

import java.io.File;
import java.util.List;


/**
 * <p>
 * 功能描述：导出数据Biz接口
 * </p>
 *
 * @version v1.0
 */
public interface ExportService {
    /**
     * 将数据写入文件
     *
     * @param dataList
     * @param file
     */
    void writeData(List<String[]> dataList, File file);

    void writeData(BaseExportModel dataModel, File file);

    /**
     * 将数据写入xls文件
     *
     * @param dataList
     * @param file
     */
    void writeXLSData(List<String[]> dataList, File file);

}
