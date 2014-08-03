package com.ifhz.core.service.channel.impl;

import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.base.annotation.Log;
import com.ifhz.core.base.commons.anthrity.UserConstants;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.Active;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysUserService;
import com.ifhz.core.service.cache.ChannelInfoCacheService;
import com.ifhz.core.service.channel.ChannelInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("channelInfoService")
public class ChannelInfoServiceImpl implements ChannelInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelInfoServiceImpl.class);

    @Resource(name = "channelInfoAdapter")
    private ChannelInfoAdapter channelInfoAdapter;

    @Resource(name = "sysUserService")
    private SysUserService sysUserService;

    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;


    @Override
    @Log
    public ChannelInfo getById(Long id) {
        return channelInfoAdapter.getById(id);
    }

    @Override
    @Log
    public ChannelInfo getByUserId(Long userId) {
        return channelInfoAdapter.getByUserId(userId);
    }

    @Override
    @Log
    public List<ChannelInfo> queryByVo(Pagination page, ChannelInfo record) {
        return channelInfoAdapter.queryByVo(page, record);
    }

    @Override
    @Log
    public int insert(ChannelInfo record) {
        if (StringUtils.isNotEmpty(record.getUsername()) && StringUtils.isNotEmpty(record.getPassword())) {
            SysUser user = new SysUser();
            user.setLoginName(record.getUsername());
            user.setPassword(record.getPassword());
            user.setAddress(record.getAddress());
            user.setRealName(record.getUsername());
            user.setCellPhone(record.getPhone());
            user.setActive(Active.Y.dbValue);
            if (JcywConstants.CHANNEL_GROUP_TY_ID_1.equals(record.getGroupId())) {
                user.setRoleId(UserConstants.TY_QUERY);
            } else if (JcywConstants.CHANNEL_GROUP_DB_ID_2.equals(record.getGroupId())) {
                user.setRoleId(UserConstants.DB_QUERY);
            } else if (JcywConstants.CHANNEL_GROUP_LW_ID_4.equals(record.getGroupId())) {
                user.setRoleId(UserConstants.LW_QUERY);
            }

            sysUserService.insert(user);
            record.setUserId(user.getUserId());
        }
        return channelInfoAdapter.insert(record);
    }

    @Override
    @Log
    public int update(ChannelInfo record) {
        if (record.getUserId() != null) {
            SysUser user = sysUserService.getById(record.getUserId());
            if (user != null && (record.getAddress() != null || record.getPhone() != null)) {
                user.setPassword(record.getPassword());
                user.setAddress(record.getAddress());
                user.setRealName(record.getContact());
                user.setCellPhone(record.getPhone());

                sysUserService.update(user);
            }
        }
        int result = channelInfoAdapter.update(record);
        channelInfoCacheService.remove(record.getChannelId());
        return result;
    }

    @Override
    @Log
    public int delete(ChannelInfo record) {
        //删除所有子节点
        deleteSubChannel(record.getChannelId());
        return channelInfoAdapter.delete(record);
    }

    @Log
    private void deleteSubChannel(Long parentId) {
        if (parentId == null) return;
        ChannelInfo ci = new ChannelInfo();
        ci.setParentId(parentId);
        ci.setActive(JcywConstants.ACTIVE_Y);
        List<ChannelInfo> subChannelList = channelInfoAdapter.queryByVo(null, ci);
        if (CollectionUtils.isNotEmpty(subChannelList)) {
            for (ChannelInfo subChannelInfo : subChannelList) {
                channelInfoAdapter.delete(subChannelInfo);
                deleteSubChannel(subChannelInfo.getChannelId());
            }
        }
    }

    @Override
    @Log
    public ChannelInfo getChannelInfoByUserId(Long userId) {
        if (userId == null) return null;
        return channelInfoAdapter.getChannelInfoByUserId(userId);
    }
}
