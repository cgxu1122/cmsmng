package com.ifhz.core.mapper;

import com.ifhz.core.po.PubChlModRef;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/24
 * Time: 17:38
 */
public interface PubChlModRefMapper {


    public List<PubChlModRef> queryNormalPkgList(@Param("groupId") Long groupId,
                                                 @Param("channelId") Long channelId,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    public List<PubChlModRef> queryCommonPkgList(@Param("groupId") Long groupId,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);
}
