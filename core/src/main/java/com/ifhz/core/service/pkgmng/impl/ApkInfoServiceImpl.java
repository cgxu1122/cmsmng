package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.ApkInfoAdapter;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ApkInfo;
import com.ifhz.core.service.pkgmng.ApkInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:06
 */
@Service("apkInfoService")
public class ApkInfoServiceImpl implements ApkInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApkInfoServiceImpl.class);

    @Resource(name = "apkInfoAdapter")
    private ApkInfoAdapter apkInfoAdapter;

    @Override
    public List<ApkInfo> queryAllList() {
        return apkInfoAdapter.queryAllList();
    }

    @Override
    public List<ApkInfo> queryUpgradeList(Date date) {
        return apkInfoAdapter.queryUpgradeList(date);
    }


    @Override
    public ApkInfo getById(Long id) {
        return apkInfoAdapter.getById(id);
    }

    @Override
    public List<ApkInfo> queryByVo(Pagination page, ApkInfo record) {
        return apkInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(ApkInfo record) {
        return apkInfoAdapter.insert(record);
    }

    @Override
    public int update(ApkInfo record) {
        return apkInfoAdapter.update(record);
    }

    @Override
    public int delete(ApkInfo record) {
        return apkInfoAdapter.delete(record);
    }
}
