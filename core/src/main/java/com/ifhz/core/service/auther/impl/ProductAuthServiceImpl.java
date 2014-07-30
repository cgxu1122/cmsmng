package com.ifhz.core.service.auther.impl;

import com.ifhz.core.po.auth.SysUserProductRef;
import com.ifhz.core.service.auther.ProductAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/7/30
 * Time: 19:08
 */
@Service("productAuthService")
public class ProductAuthServiceImpl implements ProductAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAuthServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean authProduct(Long userId, List<SysUserProductRef> recordList) {
        return false;
    }

    @Override
    public List<SysUserProductRef> queryListByUserId(Long userId) {
        return null;
    }
}
