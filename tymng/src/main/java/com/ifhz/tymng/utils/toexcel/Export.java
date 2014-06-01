package com.ifhz.tymng.utils.toexcel;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface Export {

    //导出excel，接受 2 维数组
    void exportExcel(
            String[][] names,             //表头值
            List list,                        //集合
            HttpServletResponse response,  //response
            String filename);              //文件名

    //导出excel，接受 2 维数组
    void exportExcel2(String[][] name,
                      String[][] names,             //表头值
                      List list,                        //集合
                      HttpServletResponse response,  //response
                      String filename);              //文件名

}

