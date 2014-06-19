package com.ifhz.core.service.export.impl;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVWriter;
import com.ifhz.core.constants.GlobalConstants;
import com.ifhz.core.service.export.ExportService;
import com.ifhz.core.service.export.model.BaseExportModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;


/**
 * <p>
 * 功能描述：导出数据Biz接口
 * </p>
 *
 * @version v1.0
 */
@Service("exportService")
public class ExportServiceImpl implements ExportService {

    @Override
    public void writeData(List<String[]> dataList, File file) {
        if (!CollectionUtils.isEmpty(dataList)) {
            try {
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("GBK"));
                CSVWriter writer = new CSVWriter(out, CSVParser.DEFAULT_SEPARATOR);
                writer.writeAll(dataList);
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeData(BaseExportModel dataModel, File file) {
        if (dataModel != null && file != null) {
            List<? extends Object> dataList = dataModel.getDataList();
            Map<String, String> titleMap = dataModel.getTitleMap();
            if (!titleMap.isEmpty()) {
                List<String[]> dl = new ArrayList<String[]>();
                //添加标题栏
                Collection<String> titleList = titleMap.values();
                String[] title = new String[titleList.size()];
                int titleI = 0;
                for (String titleString : titleList) {
                    title[titleI] = titleString;
                    titleI++;
                }
                dl.add(title);
                Set<String> key = titleMap.keySet();
                if (!CollectionUtils.isEmpty(dataList)) {
                    //确定小于最大导出数
                    Integer maxNum = Integer.valueOf(GlobalConstants.GLOBAL_CONFIG.get(GlobalConstants.EXPORT_NUM_MAX));
                    if (dataList.size() < maxNum) {
                        maxNum = dataList.size();
                    }
                    for (int i = 0; i < maxNum; i++) {
                        Object obj = dataList.get(i);
                        String[] data = new String[key.size()];
                        int j = 0;
                        Iterator<String> keyIterator = key.iterator();
                        while (keyIterator.hasNext()) {
                            String keyString = keyIterator.next();
                            try {
                                Field field = obj.getClass().getDeclaredField(keyString);
                                field.setAccessible(true);
                                data[j] = field.get(obj).toString();
                                j++;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }
                        //添加内容数据
                        dl.add(data);
                    }
                }
                String fileName = file.getName();
                String extName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                if ("csv".equals(extName)) {
                    writeData(dl, file);
                } else if ("xls".equals(extName) || "xlsx".equals(extName)) {
                    writeXLSData(dl, file);
                } else {
                    writeData(dl, file);
                }
            }
        }
    }

    public void writeXLSData(List<String[]> dataList, File file) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Sheet1");
        //设置CELL格式为文本格式  
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        Row row = null;
        Cell cell = null;
        if (!CollectionUtils.isEmpty(dataList)) {
            for (int rownum = 0; rownum < dataList.size(); rownum++) {
                row = sheet.createRow(rownum);
                String data[] = (String[]) dataList.get(rownum);
                if (data != null && data.length > 0) {
                    for (int cellnum = 0; cellnum < data.length; cellnum++) {
                        cell = row.createCell(cellnum);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(data[cellnum]);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                    }
                }
            }
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
                wb.write(out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
