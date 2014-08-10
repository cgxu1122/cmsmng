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

    public int insert(PubChlModRef record);

    public int deleteRepeatRef(PubChlModRef record);

    public int deleteRepeatRefForCommonPkg(PubChlModRef record);

    public int deleteByPublishId(PubChlModRef record);

    public int deleteByPackageId(PubChlModRef record);

    public int updateByPackageId(PubChlModRef record);

    public int updateByGroupIdAndGroupId(PubChlModRef record);

    public List<PubChlModRef> queryNormalPkgList(@Param("groupId") Long groupId,
                                                 @Param("channelId") Long channelId,
                                                 @Param("active") String active,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);

    public List<PubChlModRef> queryCommonPkgList(@Param("groupId") Long groupId,
                                                 @Param("channelId") Long channelId,
                                                 @Param("startTime") Date startTime,
                                                 @Param("endTime") Date endTime);


    public List<Long> queryPkgIdListForNormalPkg(@Param("groupId") Long groupId,
                                                 @Param("channelId") Long channelId);


    public List<Long> queryPkgIdListForCommonPkg(@Param("groupId") Long groupId, @Param("channelId") Long channelId);
}
