package com.ifhz.core.base.commons.excel;

import com.google.common.collect.Lists;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.handle.ModelHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/7
 * Time: 13:50
 */
public class ExcelHandle {
    private static final int MaxRowSize = 3000;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHandle.class);

    public static List<String> readImeiListFromExcel(String excelFilePath) throws Exception {
        List<String> result = Lists.newArrayList();
        Workbook wkbook = null;
        try {
            wkbook = new XSSFWorkbook(new FileInputStream(excelFilePath));
        } catch (Exception ex) {
            wkbook = new HSSFWorkbook(new FileInputStream(excelFilePath));
        }
        //获取第一个表格!
        Sheet sheet = wkbook.getSheetAt(0);
        int maxRow = (MaxRowSize > sheet.getLastRowNum() ? sheet.getLastRowNum() : MaxRowSize);
        for (int rowNum = 0; rowNum <= maxRow; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            String imei = row.getCell(0).getStringCellValue();
            if (StringUtils.isNotBlank(imei)) {
                result.add(imei);
            }
        }

        return result;
    }


    public static List<DataLog> readImeiDataFromExcel(String excelFilePath) throws Exception {
        List<DataLog> result = Lists.newArrayList();
        Workbook wkbook = null;
        try {
            wkbook = new XSSFWorkbook(new FileInputStream(excelFilePath));
        } catch (Exception ex) {
            wkbook = new HSSFWorkbook(new FileInputStream(excelFilePath));
        }
        //获取第一个表格!
        Sheet sheet = wkbook.getSheetAt(0);
        int maxRow = (MaxRowSize > sheet.getLastRowNum() ? sheet.getLastRowNum() : MaxRowSize);
        for (int rowNum = 0; rowNum <= maxRow; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            try {
                DataLog dataLog = new DataLog();
                String imei = row.getCell(0).getStringCellValue();
                String ua = row.getCell(1).getStringCellValue();
                String batchCode = row.getCell(2).getStringCellValue();
                String deviceCode = row.getCell(3).getStringCellValue();
                dataLog.setImei(StringUtils.trimToEmpty(imei));
                dataLog.setUa(ModelHandler.translateUa(StringUtils.trimToEmpty(ua)));
                dataLog.setBatchCode(StringUtils.trimToEmpty(batchCode));
                dataLog.setDeviceCode(StringUtils.trimToEmpty(deviceCode));

                result.add(dataLog);
            } catch (Exception e) {
                LOGGER.error("read row error", e);
            }
        }

        return result;
    }
}
