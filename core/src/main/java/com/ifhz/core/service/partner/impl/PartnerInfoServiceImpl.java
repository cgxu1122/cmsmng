package com.ifhz.core.service.partner.impl;

import com.ifhz.core.adapter.PartnerInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PartnerInfo;
import com.ifhz.core.service.partner.PartnerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("partnerInfoService")
public class PartnerInfoServiceImpl implements PartnerInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerInfoServiceImpl.class);

    @Resource(name = "partnerInfoAdapter")
    private PartnerInfoAdapter partnerInfoAdapter;


    @Override
    public PartnerInfo getById(Long id) {
        return partnerInfoAdapter.getById(id);
    }

    @Override
    public List<PartnerInfo> queryByVo(Pagination page, PartnerInfo record) {
        return partnerInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(PartnerInfo record) {
        return partnerInfoAdapter.insert(record);
    }

    @Override
    public int update(PartnerInfo record) {
        return partnerInfoAdapter.update(record);
    }

    @Override
    public int delete(PartnerInfo record) {
        return partnerInfoAdapter.delete(record);
    }
}
