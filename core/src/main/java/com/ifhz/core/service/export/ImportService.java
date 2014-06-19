package com.ifhz.core.service.export;


/**
 * <p>
 * 功能描述：导入数据Biz接口
 * </p>
 *
 * @version v1.0
 */
public interface ImportService {
    
   /* *//**
     * 读取文件表头
     * @param fileName 文件名
     * @return String[]
     *//*
    public String[] readHead(String fileName);
    
    *//**
     * 读取文件内容
     * @param fileName 文件名
     * @return List<String[]>
     *//*
    public List<String[]> readContent(String fileName);
    *//**
     * 获得目标数据及源数据
     * @param data 文件表头数据
     * @param map 数据库字段数据
     * @return Map
     *//*
    public Map<String, Object> getSourceTarget(String[] data, Map<String, Item> map);
    *//**
     * 导入写库
     * @param beanName bean名称
     * @param dataList 匹配成功的数据
     * @return
     *//*
    public Integer importData(String beanName, List<BaseModel> dataList);
    *//**
     * 导出错误数据
     * @param fileName  文件名
     * @param dataList 错误数据
     * @return
     *//*
    public String exportErrorData(String fileName, List<String[]> dataList);*/

}
