package com.ifhz.core.adapter;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.SettleInfo;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/16
 * Time: 21:20
 */
public interface SettleInfoAdapter {

    public SettleInfo getById(Long settleId);

    public List<SettleInfo> queryByVo(Pagination page, SettleInfo record);

    public int insert(SettleInfo record);

    public int update(SettleInfo record);

    public int delete(SettleInfo record);
}
