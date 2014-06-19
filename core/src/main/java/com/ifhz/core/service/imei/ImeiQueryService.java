package com.ifhz.core.service.imei;

import com.ifhz.core.service.imei.bean.ImeiResult;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 22:29
 */
public interface ImeiQueryService {


    public List<ImeiResult> queryListByImeiList(List<String> imeiList);
}
