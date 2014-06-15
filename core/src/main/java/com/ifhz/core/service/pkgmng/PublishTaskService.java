package com.ifhz.core.service.pkgmng;

import com.ifhz.core.vo.PackageVo;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:05
 */
public interface PublishTaskService {


    public List<PackageVo> queryPackageVoList(long channelId, Date startTime, Date endTime);

    public List<PackageVo> queryCommonPkgList(long groupId, Date startTime, Date endTime);
}
