package com.ifhz.core.adapter;

import com.ifhz.core.po.PubChlModRef;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/6/15
 * Time: 16:05
 */
public interface PubChlModRefAdapter {

    public List<PubChlModRef> queryNormalPkgList(Long groupId, Long channelId, Date startTime, Date endTime);

    public List<PubChlModRef> queryCommonPkgList(Long groupId, Date startTime, Date endTime);
}
