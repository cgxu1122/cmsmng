package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.stat.ProductArriveStatTempAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.stat.ProductArriveStat;
import com.ifhz.core.po.stat.ProductArriveStatTemp;
import com.ifhz.core.service.stat.ProductArriveStatTempService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/8/24
 * Time: 19:51
 */
@Service
public class ProductArriveStatTempServiceImpl implements ProductArriveStatTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductArriveStatTempServiceImpl.class);

    @Resource
    private ProductArriveStatTempAdapter productArriveStatTempAdapter;

    @Override
    public int insert(ProductArriveStat productArriveStat) {
        ProductArriveStatTemp result = new ProductArriveStatTemp();
        result.setId(productArriveStat.getId());
        result.setChannelId(productArriveStat.getChannelId());
        result.setGroupId(productArriveStat.getGroupId());
        result.setUa(productArriveStat.getUa());
        result.setCreateTime(productArriveStat.getCreateTime());
        result.setStatDate(productArriveStat.getStatDate());
        result.setTotalNum(productArriveStat.getTotalNum());
        result.setValidNum(productArriveStat.getValidNum());

        return productArriveStatTempAdapter.insert(result);
    }

    @Override
    public boolean syncProductArriveStat() {
        return false;
    }

    @Override
    public List<ProductArriveStatTemp> queryByVo(Pagination pagination, ProductArriveStatTemp record) {
        return productArriveStatTempAdapter.queryByVo(pagination, record);
    }

    @Override
    public List<ProductArriveStatTemp> querySumByVo(Pagination pagination, ProductArriveStatTemp record) {
        return productArriveStatTempAdapter.querySumByVo(pagination, record);
    }

    @Override
    public ProductArriveStatTemp queryCountByVo(ProductArriveStatTemp record) {
        return productArriveStatTempAdapter.queryCountByVo(record);
    }
}
