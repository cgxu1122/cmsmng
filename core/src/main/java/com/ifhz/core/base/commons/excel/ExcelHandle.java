package com.ifhz.core.base.commons.excel;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.service.api.handle.ModelHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/7
 * Time: 13:50
 */
public class ExcelHandle {
    private static final int MaxRowSize = 3000;
    private static final int MaxImeiRowSize = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelHandle.class);

    public static Set<String> readImeiListFromExcel(String excelFilePath) throws Exception {
        Set<String> result = Sets.newHashSet();
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
            String imei = getCellValue(row.getCell(0));
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
        int maxRow = (MaxImeiRowSize > sheet.getLastRowNum() ? sheet.getLastRowNum() : MaxImeiRowSize);
        for (int rowNum = 0; rowNum <= maxRow; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            try {
                DataLog dataLog = new DataLog();
                String imei = getCellValue(row.getCell(0));
                if (StringUtils.containsIgnoreCase(imei, "IMEI")) {
                    continue;
                }
                String ua = getCellValue(row.getCell(1));
                String batchCode = getCellValue(row.getCell(2));
                String deviceCode = getCellValue(row.getCell(3));
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


    private static String getCellValue(Cell cell) {
        if (cell != null) {
            int type = cell.getCellType();
            switch (type) {
                case Cell.CELL_TYPE_BLANK:
                    return "";
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getStringCellValue();
                case Cell.CELL_TYPE_NUMERIC:
                    String value = cell.getNumericCellValue() + "";
                    if (StringUtils.isNotBlank(value)) {
                        DecimalFormat format = new DecimalFormat("0");
                        BigDecimal bd = new BigDecimal(value);
                        return format.format(bd);
                    }
                case Cell.CELL_TYPE_FORMULA:
                    String v = cell.getNumericCellValue() + "";
                    if (StringUtils.isNotBlank(v)) {
                        DecimalFormat format = new DecimalFormat("0");
                        BigDecimal bd = new BigDecimal(v);
                        return format.format(bd);
                    }
                case Cell.CELL_TYPE_ERROR:
                    return "";
                default:
                    return cell.getStringCellValue();
            }
        }

        return "";
    }

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\chenggang.xu.QUNARSERVERS\\Downloads\\ImeiUploadFile.xls";
        List<DataLog> list = readImeiDataFromExcel(path);
        System.out.println(JSON.toJSONString(list));
    }
}
