package com.ifhz.core.service.partner;


import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PartnerInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yangjian
 */
public interface PartnerInfoService {
    public PartnerInfo getById(Long id);

    public List<PartnerInfo> queryByVo(Pagination page, PartnerInfo record);

    public int insert(PartnerInfo record);

    public int update(PartnerInfo record);

    public int delete(PartnerInfo record);

    public PartnerInfo getPartnerInfoByUserId(Long userId);
}
