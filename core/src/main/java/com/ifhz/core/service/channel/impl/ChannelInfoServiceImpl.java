package com.ifhz.core.service.channel.impl;

import com.ifhz.core.adapter.ChannelInfoAdapter;
import com.ifhz.core.base.commons.anthrity.UserConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.ChannelInfo;
import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.channel.ChannelInfoService;
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


    @Override
    public ChannelInfo getById(Long id) {
        return channelInfoAdapter.getById(id);
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
            if (user != null) {
                user.setPassword(record.getPassword());
                user.setAddress(record.getAddress());
                user.setRealName(record.getContact());
                user.setCellphone(record.getPhone());
                userService.updateUser(user);
            }
        }
        return channelInfoAdapter.update(record);
    }

    @Override
    public int delete(ChannelInfo record) {
        return channelInfoAdapter.delete(record);
    }

    @Override
    public ChannelInfo getChannelInfoByUserId(Long userId) {
        if (userId == null) return null;
        return channelInfoAdapter.getChannelInfoByUserId(userId);
    }
}
