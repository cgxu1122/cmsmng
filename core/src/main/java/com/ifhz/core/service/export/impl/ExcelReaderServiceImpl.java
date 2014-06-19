package com.ifhz.core.service.export.impl;

import com.ifhz.core.service.export.ExcelReaderService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 * 功能描述：读取Excel数据Biz接口
 * </p>
 *
 * @version v1.0
 */
@Service("excelReaderService")
public class ExcelReaderServiceImpl implements ExcelReaderService {

    private POIFSFileSystem fs;
    private Workbook wb;
    private Sheet sheet;
    private Row row;
    private InputStream input;
    public DecimalFormat df = new DecimalFormat("0");

    public String[] readHeader(File file) {// 读取Excel表格表头的内容
        String header[] = null;
        if (file != null) {
            try {
                input = new BufferedInputStream(new FileInputStream(file));
                fs = new POIFSFileSystem(input);
                wb = new HSSFWorkbook(fs);
//               wb = WorkbookFactory.create(input);
                if (wb != null) {
                    sheet = wb.getSheetAt(0);
                    row = sheet.getRow(0);// 得到标题的内容对象。
                    if (row != null) {
                        int colNum = row.getPhysicalNumberOfCells(); // 得到标题总列数
                        header = new String[colNum];
                        for (int i = 0; i < colNum; i++) {
                            header[i] = getStringCellValue(row.getCell(i));
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return header;
    }


    @Override
    public List<String[]> readContent(File file, boolean isRandom) {
        List<String[]> content = new ArrayList<String[]>();
        if (file != null) {
            try {
                input = new BufferedInputStream(new FileInputStream(file));
                fs = new POIFSFileSystem(input);
                wb = new HSSFWorkbook(fs);
                if (wb != null) {
                    sheet = wb.getSheetAt(0);//得到第一个工作表
                    int rowNum = sheet.getLastRowNum(); // 得到总行数
                    int start = 0;
                    int step = 5;
                    if (isRandom) {
                        Random random = new Random();
                        if ((rowNum - step) > 0) {//总行数大于5条时
                            start = random.nextInt(rowNum - step) % (rowNum - step) + 1;//随机从1-（rowNum-step）中取值
                            rowNum = start + step;
                        }
                    }
                    int colNum = sheet.getRow(0).getPhysicalNumberOfCells();// 得到一行的列数
                    for (int i = start; i <= rowNum; i++) {
                        row = sheet.getRow(i);
                        if (row != null) {
                            int j = 0;
                            String[] a = new String[colNum];
                            Set<Boolean> flag = new HashSet<Boolean>();
                            while (j < colNum) {
                                a[j] = getStringCellValue(row.getCell(j)).trim();
                                if (StringUtils.isEmpty(a[j])) {
                                    flag.add(false);
                                } else {
                                    flag.add(true);
                                }
                                j++;
                            }
                            if (flag.contains(true)) {
                                content.add(a);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    // 获取单元格数据内容为字符串类型的数据
    private String getStringCellValue(Cell cell) {
        String strCell = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date d = cell.getDateCellValue();
                        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                        strCell = formater.format(d);
                    } else {
                        //防止数字变成科学计数法的形式
                        strCell = df.format(cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    strCell = "";
                    break;
                default:
                    strCell = "";
                    break;
            }
            if (strCell.equals("") || strCell == null) {
                return "";
            }
        }
        return strCell;
    }
}
