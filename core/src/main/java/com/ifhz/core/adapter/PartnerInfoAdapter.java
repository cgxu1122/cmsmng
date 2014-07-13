package com.ifhz.core.adapter;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PartnerInfo;

import java.util.List;

/**
 * 类描述
 * User:yangjian
 */
public interface PartnerInfoAdapter {

    public PartnerInfo getById(Long id);

    public List<PartnerInfo> queryByVo(Pagination page, PartnerInfo record);

    public int insert(PartnerInfo record);

    public int update(PartnerInfo record);

    public int delete(PartnerInfo record);

    public PartnerInfo getPartnerInfoByUserId(Long userId);
}
