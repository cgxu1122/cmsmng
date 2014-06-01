package com.ifhz.tymng.utils.toexcel;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

public class ExportToExcel implements Export {


    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFRow row;
    private HSSFCell cell;
    private HSSFCellStyle bigBold, smallBold, smallMiddle, smallLeft, smallRight;
    private int j = 0;
    //protected static final Logger log = Logger.getLogger(ExportToExcel.class);


    public ExportToExcel() {
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet();
        row = sheet.createRow((short) 0);
        cell = row.createCell((short) 0);
        bigBold = getStyle(Constants.bigFontSize, Constants.fontStrong, HSSFCellStyle.ALIGN_CENTER);
        smallBold = getStyle(Constants.littleFontSize, Constants.fontStrong, HSSFCellStyle.ALIGN_CENTER);
        smallMiddle = getStyle(Constants.littleFontSize, "", HSSFCellStyle.ALIGN_CENTER);
        smallLeft = getStyle(Constants.littleFontSize, "", HSSFCellStyle.ALIGN_LEFT);
        smallRight = getStyle(Constants.littleFontSize, "", HSSFCellStyle.ALIGN_RIGHT);
    }

    //生成2维表头 excel
    public void exportExcel2(String name[][],                    //表头值
                             String[][] names,               //表列表
                             List list,                         //集合
                             HttpServletResponse response,    //response
                             String filename) {                //文件名


        try {
            j = 1;
            row = sheet.createRow((short) (j - 1));
            createSingleCell(name);
            j = 2;
            row = sheet.createRow((short) (j - 1));
            createSingleCell(names);
            j = 3;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                HSSFRow row2 = sheet.createRow((short) (j - 1));
                List ll = (List) it.next();
                createTableCell(row2, ll.toArray());
                j++;
            }

            //是否导出excel.或者直接在内存下载
            ifCreateExcel(workbook, response, filename, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //生成 excel
    public void exportExcel(
            String[][] names,               //表列表
            List list,                         //集合
            HttpServletResponse response,    //response
            String filename) {                //文件名


        try {
            j = 1;
            row = sheet.createRow((short) (j - 1));
            createSingleCell(names);
            j = 2;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                HSSFRow row2 = sheet.createRow((short) (j - 1));
                List ll = (List) it.next();
                createTableCell(row2, ll.toArray());
                j++;
            }

            //是否导出excel.或者直接在内存下载
            ifCreateExcel(workbook, response, filename, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断是否导出excel.或者直接在内存下载
    private void ifCreateExcel(HSSFWorkbook webbook,
                               HttpServletResponse response,
                               String fileName,
                               boolean bool) {
        fileName = FileUtil.processFileTail(fileName, Constants.XLS);
        if (bool) {
            try {
                //得到输出路径，用到时间戳
                String filePath = FileUtil.createTimeBag(Constants.outPath);
                //输出路径处理 文件名 根后缀
                filePath = filePath + fileName;
                FileOutputStream out = new FileOutputStream(filePath);
                webbook.write(out);
                out.flush();
                out.close();
                response.reset();
                response.setContentType("text/html;charset=GBK");
                response.getWriter().println("<script language=\"javascript\" type=\"\">");
                response.getWriter().println("alert(\"EXCEL 生成成功 ！！\");");
                response.getWriter().println("window.close();");
                response.getWriter().println("</script>");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                //文件流输出
                response.reset();
                response.setContentType("application/vnd.ms-excel;charset=GBK");
                response.setHeader("Content-Disposition", "attachment;filename=\"" +
                        new String(fileName.getBytes(), "iso-8859-1") + "\"");

                ServletOutputStream out = response.getOutputStream();
                webbook.write(out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //生成单元格
    private void createTableCell(HSSFRow row, Object[] names) {
        int e = 0;
        Object oo = null;
        for (int k = 0; k < names.length; k++) {
            //cell
            oo = names[k];
            HSSFCell cell2 = row.createCell((short) e);

            if (oo instanceof Double) {
                createRow(cell2, smallRight);
                cell2.setCellValue(((Double) oo).doubleValue());
            } else if (oo instanceof Integer) {
                createRow(cell2, smallRight);
                cell2.setCellValue(((Integer) oo).intValue());
            } else if (oo instanceof Date) {
                createRow(cell2, smallMiddle);
                cell2.setCellValue((String) (FormatUtil.processDate((Date) oo)));
            } else {
                if (k == 0) {
                    createRow(cell2, smallMiddle);
                } else {
                    createRow(cell2, smallLeft);
                }
                if (oo == null) oo = " ";
                cell2.setCellValue(oo.toString());
            }
            e++;
            oo = null;
        }
    }

    //单表头
    private void createSingleCell(String[][] names) {

        int i = 0;
        for (int k = 0; k < names.length; k++) {
            //cell
            cell = row.createCell((short) i);
            createRow(cell, smallMiddle);
            cell.setCellValue((String) names[k][0]);
            i++;
        }

    }

    //单元格属性
    private void createRow(HSSFCell cell, HSSFCellStyle style) {
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
    }

    //得到字体等属性
    private HSSFCellStyle getStyle(int fontSize, String fontWeight, short sh) {
        HSSFFont font = workbook.createFont();
        HSSFCellStyle style = workbook.createCellStyle();
        //begin
        font.setFontName("宋体");
        //font height
        font.setFontHeightInPoints((short) fontSize);
        //bold weight
        if (fontWeight.equals("bold"))
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        else
            font.setBoldweight((short) 0);

        style.setFont(font);
        style.setAlignment(sh);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;
    }

}

