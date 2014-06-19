package com.ifhz.core.service.export;

import java.io.File;
import java.util.List;


/**
 * <p>
 * 功能描述：读取Excel数据Biz接口
 * </p>
 *
 * @version v1.0
 */
public interface ExcelReaderService {
    /**
     * 读取Excel数据内容
     *
     * @param file
     * @return String[]
     */
    String[] readHeader(File file);

    /**
     * 读取Excel数据内容
     *
     * @param file
     * @return List<String[]>
     */
    List<String[]> readContent(File file, boolean isRandom);

}
