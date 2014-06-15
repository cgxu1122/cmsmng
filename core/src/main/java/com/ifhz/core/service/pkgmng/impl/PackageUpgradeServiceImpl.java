package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.PubChlModRefAdapter;
import com.ifhz.core.service.pkgmng.PackageUpgradeService;
import com.ifhz.core.vo.PackageVo;
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
 * Time: 21:28
 */
@Service("packageUpgradeService")
public class PackageUpgradeServiceImpl implements PackageUpgradeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PackageUpgradeServiceImpl.class);

    @Resource(name = "pubChlModRefAdapter")
    private PubChlModRefAdapter pubChlModRefAdapter;


    @Override
    public List<PackageVo> queryNormalPkgList(long groupId, long channelId, Date startTime, Date endTime) {
        return null;
    }

    @Override
    public PackageVo queryCommonPkgList(long groupId, Date startTime, Date endTime) {
        return null;
    }
}
