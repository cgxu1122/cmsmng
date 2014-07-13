package com.ifhz.core.service.channel.impl;

import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.base.commons.anthrity.UserConstants;
import com.ifhz.core.base.commons.constants.JcywConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
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

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "channelInfoCacheService")
    private ChannelInfoCacheService channelInfoCacheService;


    @Override
    public ChannelInfo getById(Long id) {
        return channelInfoAdapter.getById(id);
    }

    @Override
    public ChannelInfo getByUserId(Long userId) {
        return channelInfoAdapter.getByUserId(userId);
    }

    @Override
    public List<ChannelInfo> queryByVo(Pagination page, ChannelInfo record) {
        return channelInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(ChannelInfo record) {
        if (StringUtils.isNotEmpty(record.getUsername()) && StringUtils.isNotEmpty(record.getPassword())) {
            User user = new User();
            user.setLoginName(record.getUsername());
            user.setPassword(record.getPassword());
            user.setAddress(record.getAddress());
            user.setRealName(record.getUsername());
            user.setCellphone(record.getPhone());
            user.setStatus(UserConstants.USER_STATUS_ENABLE);
            user.setType(UserConstants.USER_TYPE_NORMAL);
            userService.insertUser(user, UserConstants.USER_ROLE_NORMAL);
            record.setUserId(user.getUserId());
        }
        return channelInfoAdapter.insert(record);
    }

    @Override
    public int update(ChannelInfo record) {
        if (record.getUserId() != null) {
            User user = userService.findById(record.getUserId());
            if (user != null && (record.getAddress() != null || record.getPhone() != null)) {
                user.setPassword(record.getPassword());
                user.setAddress(record.getAddress());
                user.setRealName(record.getContact());
                user.setCellphone(record.getPhone());
                userService.updateUser(user);
            }
        }
        int result = channelInfoAdapter.update(record);
        channelInfoCacheService.remove(record.getChannelId());
        return result;
    }

    @Override
    public int delete(ChannelInfo record) {
        //删除所有子节点
        deleteSubChannel(record.getChannelId());
        return channelInfoAdapter.delete(record);
    }

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
    public ChannelInfo getChannelInfoByUserId(Long userId) {
        if (userId == null) return null;
        return channelInfoAdapter.getChannelInfoByUserId(userId);
    }
}
