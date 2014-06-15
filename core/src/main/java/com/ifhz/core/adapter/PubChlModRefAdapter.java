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

    public int insert(PubChlModRef record);

    public int deleteRepeatRef(PubChlModRef record);

    public int deleteByPublishId(PubChlModRef record);

    public int deleteByPackageId(PubChlModRef record);

    public int updateByPackageId(PubChlModRef record);

    public int updateByGroupIdAndGroupId(PubChlModRef record);

    public List<PubChlModRef> queryNormalPkgList(Long groupId, Long channelId, String active, Date startTime, Date endTime);

    public List<PubChlModRef> queryCommonPkgList(Long groupId, Date startTime, Date endTime);
}
