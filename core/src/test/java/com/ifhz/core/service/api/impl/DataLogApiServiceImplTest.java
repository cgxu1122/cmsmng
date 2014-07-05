package com.ifhz.core.service.api.impl;

import com.google.common.collect.Lists;
import com.ifhz.core.base.commons.date.DateFormatUtils;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.DataLog;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.service.api.ApiUploadService;
import com.ifhz.core.service.api.DataLogApiService;
import com.test.BaseTest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataLogApiServiceImplTest extends BaseTest {

    @Resource
    private DataLogApiService dataLogApiService;
    @Resource
    private ApiUploadService apiUploadService;
    @Resource
    private JdbcTemplate jdbcTemplate;
    public DecimalFormat df = new DecimalFormat("0");

    @Test
    public void testInsertDeviceData() throws Exception {
        String filePath = "D:\\imei.xls";
        File file = new File(filePath);
        List<DataLog> dataLogList = readExcel(file);

    }

    @Test
    public void testUpdateCounterData() throws Exception {

    }

    @Test
    public void testGetByImei() throws Exception {

    }


    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List<DataLog> readExcel(File file) {
        POIFSFileSystem fs;
        Workbook wb;
        Sheet sheet;
        Row row;
        InputStream input = null;

        List<DataLog> content = Lists.newArrayList();
        if (file != null) {
            try {
                input = new BufferedInputStream(new FileInputStream(file));
                fs = new POIFSFileSystem(input);
                wb = new HSSFWorkbook(fs);
                if (wb != null) {
                    sheet = wb.getSheetAt(0);//得到第一个工作表
                    int rowNum = sheet.getLastRowNum(); // 得到总行数
                    int start = 0;
                    int colNum = 4;
                    for (int i = start; i <= rowNum; i++) {
                        row = sheet.getRow(i);
                        if (row != null) {
                            String pTime = getStringCellValue(row.getCell(0)).trim();
                            String modelName = getStringCellValue(row.getCell(1)).trim();
                            String channelName = getStringCellValue(row.getCell(2)).trim();
                            String imei = getStringCellValue(row.getCell(3)).trim();
                            DataLog dataLog = translate(pTime, modelName, channelName, imei);
                            if (dataLog != null) {
                                apiUploadService.saveDeviceDataLog(dataLog);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(input);
            }
        }
        return content;
    }


    private DataLog translate(String pTime, String modelName, String channelName, String imei) {
        DataLog dataLog = null;
        Date processTime = new Date();
        if (StringUtils.isNotBlank(pTime)) {
            processTime = DateFormatUtils.parse(pTime, "yyyy-MM-dd");
        }
        ChannelInfo channelInfo = null;
        if (StringUtils.isNotBlank(channelName)) {
            String modelSql = "select GROUP_ID,CHANNEL_ID from TY_CHANNEL_INFO where channel_name = ?";
            Object[] os = new Object[]{channelName};
            List<ChannelInfo> channelInfoList = jdbcTemplate.query(modelSql, os, new ChannelRowMapper());
            if (CollectionUtils.isNotEmpty(channelInfoList)) {
                channelInfo = channelInfoList.get(0);
            }
        }
        ModelInfo modelInfo = null;
        if (StringUtils.isNotBlank(modelName) && channelInfo != null) {
            String modelSql = "select UA from TY_MODEL_INFO where model_name = ? and group_id = ?";
            Object[] os = new Object[]{modelName, channelInfo.getGroupId()};
            List<ModelInfo> modelInfoList = jdbcTemplate.query(modelSql, os, new ModelRowMapper());
            if (CollectionUtils.isNotEmpty(modelInfoList)) {
                modelInfo = modelInfoList.get(0);
            }
        }

        if (StringUtils.isNotBlank(imei) && modelInfo != null && processTime != null) {
            dataLog = new DataLog();
            dataLog.setImei(imei);
            dataLog.setUa(modelInfo.getUa());
            dataLog.setDeviceUploadTime(new Date());
            dataLog.setGroupId(channelInfo.getGroupId());
            dataLog.setProcessTime(processTime);
            dataLog.setBatchCode(getBatchCode());
            dataLog.setDeviceCode(getDeviceCode());
            dataLog.setChannelId(channelInfo.getChannelId());

        }

        return dataLog;
    }

    private String getBatchCode() {
        String[] bs = new String[]{"TY2", "TY3", "TY4"};
        Random random = new Random();

        return bs[random.nextInt(3)];
    }

    private String getDeviceCode() {
        String str = "Device";
        String dd = "C4:46:19:73:37:A7";
        Random random = new Random();
        int t = random.nextInt(101);
        if (t == 100) {
            return dd;
        }

        return str + t;
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


    private class ModelRowMapper implements RowMapper<ModelInfo> {

        @Override
        public ModelInfo mapRow(ResultSet resultSet, int i) throws SQLException {
            ModelInfo ret = new ModelInfo();
            ret.setUa(resultSet.getString("UA"));

            return ret;
        }
    }

    private class ChannelRowMapper implements RowMapper<ChannelInfo> {

        @Override
        public ChannelInfo mapRow(ResultSet resultSet, int i) throws SQLException {
            ChannelInfo ret = new ChannelInfo();
            ret.setGroupId(resultSet.getLong("GROUP_ID"));
            ret.setChannelId(resultSet.getLong("CHANNEL_ID"));

            return ret;
        }
    }
}