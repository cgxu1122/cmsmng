package com.ifhz.core.service.pkgmng.impl;

import com.ifhz.core.adapter.PubChlModRefAdapter;
import com.ifhz.core.adapter.PublishTaskAdapter;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PubChlModRef;
import com.ifhz.core.po.PublishTask;
import com.ifhz.core.service.pkgmng.PublishTaskService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类描述
 * User: chenggangxu@sohu-inc.com
 * Date: 2014/5/21
 * Time: 18:07
 */
@Service("publishTaskService")
public class PublishTaskServiceImpl implements PublishTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishTaskServiceImpl.class);

    @Resource(name = "publishTaskAdapter")
    private PublishTaskAdapter publishTaskAdapter;
    @Resource(name = "pubChlModRefAdapter")
    private PubChlModRefAdapter pubChlModRefAdapter;

    @Override
    public PublishTask getById(Long id) {
        return publishTaskAdapter.getById(id);
    }

    @Override
    public List<PublishTask> queryByVo(Pagination page, PublishTask record) {
        return publishTaskAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(PublishTask record) {
        int flag = publishTaskAdapter.insert(record);
        List<PubChlModRef> pubChlList = record.getPubChlList();
        List<PubChlModRef> pubModList = record.getPubModList();
        if (CollectionUtils.isNotEmpty(pubChlList) && CollectionUtils.isNotEmpty(pubModList)) {
            for (PubChlModRef pubChl : pubChlList) {
                for (PubChlModRef pubMod : pubModList) {
                    PubChlModRef pubChlModRef = new PubChlModRef();
                    pubChlModRef.setActive(JcywConstants.ACTIVE_Y);
                    pubChlModRef.setPublishId(record.getPublishId());
                    pubChlModRef.setPackageId(record.getPackageId());
                    pubChlModRef.setPkgType(record.getPkgType());
                    pubChlModRef.setGroupId(pubChl.getGroupId());
                    pubChlModRef.setChannelId(pubChl.getChannelId());
                    pubChlModRef.setModelId(pubMod.getModelId());
                    //先根据groupId，channelId和modelId删除重复的数据
                    pubChlModRefAdapter.deleteRepeatRef(pubChlModRef);
                    //再添加新数据
                    pubChlModRefAdapter.insert(pubChlModRef);
                }
            }
        }
        return flag;
    }

    @Override
    public int delete(PublishTask record) {
        //先删除关联表信息
        PubChlModRef pubChlModRef = new PubChlModRef();
        pubChlModRef.setPublishId(record.getPublishId());
        pubChlModRefAdapter.deleteByPublishId(pubChlModRef);
        return publishTaskAdapter.delete(record);
    }

}
