package com.ifhz.core.service.export.impl;

import com.ifhz.core.service.export.ImportService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 功能描述：导入数据Biz接口
 * </p>
 *
 * @version v1.0
 */
@Service("importService")
public class ImportServiceImpl implements ImportService {

    /*private ExcelReaderBiz excelReaderBiz;

    private CSVReaderBiz csvReaderBiz;

    private ExportBiz exportBiz;

    private FileBiz fileBiz;

    public void setFileBiz(FileBiz fileBiz) {
        this.fileBiz = fileBiz;
    }

    public void setExcelReaderBiz(ExcelReaderBiz excelReaderBiz) {
        this.excelReaderBiz = excelReaderBiz;
    }

    public void setCsvReaderBiz(CSVReaderBiz csvReaderBiz) {
        this.csvReaderBiz = csvReaderBiz;
    }

    public void setExportBiz(ExportBiz exportBiz) {
        this.exportBiz = exportBiz;
    }
    
    private static String path = FileUtils.DATA_TEMP + File.separator;
    
    public String[] readHead(String fileName) {
        String[] source = null;// 数据
        if (!StringUtils.isEmpty(fileName)) {
            File file = fileBiz.getData(path, fileName);
            String extName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());// 获取后缀名
            if(!StringUtils.isEmpty(extName)){
                if ("xls".equals(extName.toLowerCase())||"xlsx".equals(extName.toLowerCase())) {
                    source = excelReaderBiz.readHeader(file);
                } else if ("csv".equals(extName.toLowerCase())) {
                    source = csvReaderBiz.readHeader(file);
                }
            }
        }
        return source;
    }

    public List<String[]> readContent(String fileName) {
        // 读取文件内容
        List<String[]> dataList = null;
        if (!StringUtils.isEmpty(fileName)) {
            File file = fileBiz.getData(path, fileName);
            String extName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());// 获取后缀名
            if(!StringUtils.isEmpty(extName)){
                if ("xls".equals(extName.toLowerCase())||"xlsx".equals(extName.toLowerCase())) {
                    dataList = excelReaderBiz.readContent(file, false);
                } else if ("csv".equals(extName.toLowerCase())) {
                    dataList = csvReaderBiz.readContent(file);
                }
            }
        }
        return dataList;
    }

    public Map<String, Object> getSourceTarget(String[] source, Map<String, Item> map) {
        List<Item> targetList = new ArrayList<Item>(); // 目标数据
        List<String> sourceList = new ArrayList<String>(); // 源数据
        if (!CollectionUtils.isEmpty(map)) {
            for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
                String itemCode = (String) it.next();
                Item item = (Item) map.get(itemCode);
                String value = null;
                int p = 9999;
                Boolean flag = false;
                if(source != null && source.length > 0){
                    String name = item.getName();
                    for (int y = 0; y < source.length; y++) {
                        if (!StringUtils.isEmpty(source[y])) {
                            if (item!=null && name.equals(source[y])) {
                                flag = true;
                            }else if(Pattern.compile(source[y]).matcher(name).find()){
                                flag = true;
                            }else if(Pattern.compile(name).matcher(source[y]).find()){
                                flag = true;
                            }
                            if(flag){
                                  value = source[y];
                                  p = y;
                                  break;
                            }
                        }
                    }
                }
               
                item.setDescription(value);
                item.setDisplayOrder(p);
                targetList.add(item);// 获得item对象
            }
            // 源数据
            if(source != null && source.length > 0){
                sourceList = Arrays.asList(source);
            }
        }
       
        Map<String, Object> content = new HashMap<String, Object>();
        content.put("source", sourceList);
        content.put("target", targetList);
        return content;
    }

    public Integer importData(String beanName, List<BaseModel> dataList) {
        int successNum = 0;// 成功个数
        if (dataList != null && dataList.size() > 0) {
            successNum = dataList.size();
            if (!StringUtils.isEmpty(beanName)) {
                // 检查是否是bean,如果是bean则直接处理
                BaseImportBiz baseImportBiz = (BaseImportBiz) ApplicationContextAccessor.getBean(beanName);
                if (baseImportBiz != null) {
                    baseImportBiz.importData(dataList);
                } else {
                    successNum = 0;
                }
            }
        }
        return successNum;
    }
    
    
    public String exportErrorData(String fileName, List<String[]> dataList) {
       String errorName = "";
       String extName = null;
       if (dataList != null && dataList.size() > 0) {
            if (!StringUtils.isEmpty(fileName)) {
                extName = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());// 获取后缀名
                extName = extName.toLowerCase();
            }
            String prefix = ZDateUtils.getCurrentDateStr(ZDateUtils.YYYYMMDDHHMMSS);
            if(!StringUtils.isEmpty(extName)){
                if ("xls".equals(extName)) {
                    errorName =  prefix+"Error.xls";
                    File file = fileBiz.getData(path, errorName);
                    exportBiz.writeXLSData(dataList, file);
                } else if ("csv".equals(extName)) {
                    errorName = prefix+"Error.csv";
                    File file = fileBiz.getData(path, errorName);
                    exportBiz.writeData(dataList, file);
                }
            }
       }
        return errorName;
    }*/
}
