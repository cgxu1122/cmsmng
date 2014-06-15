package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.PackageApkRefAdapter;
import com.ifhz.core.adapter.PublishTaskAdapter;
import com.ifhz.core.service.pkgmng.PackageInfoService;
import com.ifhz.core.service.pkgmng.PublishTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:07
 */
@Service("publishTaskService")
public class PublishTaskServiceImpl implements PublishTaskService {

    @Resource(name = "publishTaskAdapter")
    private PublishTaskAdapter publishTaskAdapter;
    @Resource(name = "packageInfoService")
    private PackageInfoService packageInfoService;
    @Resource(name = "packageApkRefAdapter")
    private PackageApkRefAdapter packageApkRefAdapter;

}
