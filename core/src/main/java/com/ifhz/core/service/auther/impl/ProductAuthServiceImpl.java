package com.ifhz.core.service.auther.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ifhz.core.adapter.ProductInfoAdapter;
import com.ifhz.core.adapter.SysRoleAdapter;
import com.ifhz.core.adapter.SysUserAdapter;
import com.ifhz.core.adapter.SysUserProductRefAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.Active;
import com.ifhz.core.po.ProductInfo;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.po.auth.SysUserProductRef;
import com.ifhz.core.service.auther.ProductAuthService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 19:08
 */
@Service("productAuthService")
public class ProductAuthServiceImpl implements ProductAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAuthServiceImpl.class);

    @Autowired
    private SysRoleAdapter sysRoleAdapter;
    @Autowired
    private SysUserAdapter sysUserAdapter;
    @Autowired
    private ProductInfoAdapter productInfoAdapter;
    @Autowired
    private SysUserProductRefAdapter sysUserProductRefAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean authProduct(Active active, SysUserProductRef record) {
        int ret = 0;
        if (active == Active.Y) {
            ret = sysUserProductRefAdapter.insert(record);
        } else {
            ret = sysUserProductRefAdapter.delete(record);
        }
        return ret == 1;
    }

    @Override
    public List<SysUserProductRef> queryListByUserId(Long userId) {
        return null;
    }

    @Override
    public List<SysUser> queryUserList(Pagination pagination, String searchValue) {
        if (StringUtils.isBlank(searchValue)) {
            searchValue = null;
        }

        return sysUserAdapter.queryListByVo(pagination, searchValue);
    }

    @Override
    public Map<String, List<ProductInfo>> queryProductList(Long userId) {
        Map<String, List<ProductInfo>> map = Maps.newHashMap();
        List<ProductInfo> productInfoList = productInfoAdapter.queryAllList();
        List<Long> refList = sysUserProductRefAdapter.queryProductIdListByUserId(userId);
        if (CollectionUtils.isNotEmpty(productInfoList)) {
            for (ProductInfo productInfo : productInfoList) {
                String key = productInfo.getPartnerName().trim();
                if (refList.contains(productInfo.getProductId())) {
                    productInfo.setHasAuth(true);
                } else {
                    productInfo.setHasAuth(false);
                }
                if (map.containsKey(key)) {
                    List<ProductInfo> value = map.get(key);
                    value.add(productInfo);
                    map.put(key, value);
                } else {
                    List<ProductInfo> value = Lists.newArrayList();
                    value.add(productInfo);
                    map.put(key, value);
                }
            }
        }

        return map;
    }
}
