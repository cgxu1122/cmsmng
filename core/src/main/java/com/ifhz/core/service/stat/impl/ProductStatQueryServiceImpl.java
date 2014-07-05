package com.ifhz.core.service.stat.impl;

import com.ifhz.core.adapter.ChannelGroupAdapter;
import com.ifhz.core.adapter.ModelInfoAdapter;
import com.ifhz.core.adapter.ProductStatAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelGroup;
import com.ifhz.core.po.ModelInfo;
import com.ifhz.core.po.ProductStat;
import com.ifhz.core.service.stat.ProductStatQueryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/21
 * Time: 17:17
 */
@Service("productStatQueryService")
public class ProductStatQueryServiceImpl implements ProductStatQueryService {
    @Resource(name = "productStatAdapter")
    private ProductStatAdapter productStatAdapter;
    @Resource(name = "modelInfoAdapter")
    private ModelInfoAdapter modelInfoAdapter;
    @Resource(name = "channelGroupAdapter")
    private ChannelGroupAdapter channelGroupAdapter;

    @Override
    public List<ProductStat> queryByVo(Pagination page, ProductStat record) {
        List<ProductStat> productStatList = productStatAdapter.queryByVo(page, record);
        if (CollectionUtils.isNotEmpty(productStatList)) {
            for (ProductStat productStat : productStatList) {
                String ua = productStat.getUa();
                if (StringUtils.isNotEmpty(ua)) {
                    ModelInfo modelInfo = modelInfoAdapter.getByGroupIdAndUa(productStat.getGroupId(), ua);
                    if (modelInfo != null) {
                        productStat.setModelName(modelInfo.getModelName());
                    }
                }
                if (productStat.getGroupId() != null) {
                    ChannelGroup channelGroup = channelGroupAdapter.getById(productStat.getGroupId());
                    if (channelGroup != null) {
                        productStat.setGroupName(channelGroup.getGroupName());
                    }
                }
            }
        }
        return productStatList;
    }

    @Override
    public ProductStat queryCountByVo(ProductStat record) {
        return productStatAdapter.queryCountByVo(record);
    }
}
