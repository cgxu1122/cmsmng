package com.ifhz.core.service.export.impl;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.service.export.CSVReaderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * 功能描述：读取CSV数据Biz接口
 * </p>
 *
 * @version v1.0
 */
@Service("csvReaderService")
public class CSVReaderServiceImpl implements CSVReaderService {

    private DataInputStream in = null;
    private CSVReader reader = null;

    @Log
    public String[] readHeader(File file) {// 读取表头的内容
        String header[] = null;
        try {
            in = new DataInputStream(new FileInputStream(file));
            reader = new CSVReader(new InputStreamReader(in, "gbk"), CSVParser.DEFAULT_SEPARATOR);
            if (reader != null) {
                header = reader.readNext();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return header;
    }


    @Override
    @Log
    public List<String[]> readContent(File file) {
        List<String[]> content = new ArrayList<String[]>();
        try {
            in = new DataInputStream(new FileInputStream(file));
            reader = new CSVReader(new InputStreamReader(in, "gbk"), CSVParser.DEFAULT_SEPARATOR);
            if (reader != null) {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    Set<Boolean> flag = new HashSet<Boolean>();
                    for (int i = 0; i < nextLine.length; i++) {
                        if (StringUtils.isEmpty(nextLine[i])) {
                            flag.add(false);
                        } else {
                            flag.add(true);
                        }
                    }
                    if (flag.contains(true)) {
                        content.add(nextLine);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;
    }

}
