package com.ifhz.core.service.partner.impl;

import com.ifhz.core.adapter.PartnerInfoAdapter;
import com.ifhz.core.base.commons.anthrity.UserConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.PartnerInfo;
import com.ifhz.core.po.User;
import com.ifhz.core.service.auth.UserService;
import com.ifhz.core.service.partner.PartnerInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service("partnerInfoService")
public class PartnerInfoServiceImpl implements PartnerInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PartnerInfoServiceImpl.class);

    @Resource(name = "partnerInfoAdapter")
    private PartnerInfoAdapter partnerInfoAdapter;
    @Resource(name = "userService")
    private UserService userService;


    @Override
    public PartnerInfo getById(Long id) {
        return partnerInfoAdapter.getById(id);
    }

    @Override
    public List<PartnerInfo> queryByVo(Pagination page, PartnerInfo record) {
        return partnerInfoAdapter.queryByVo(page, record);
    }

    @Override
    public int insert(PartnerInfo record) {
        if (StringUtils.isNotEmpty(record.getUsername()) && StringUtils.isNotEmpty(record.getPassword())) {
            User user = new User();
            user.setLoginName(record.getUsername());
            user.setRealName(record.getUsername());
            user.setPassword(record.getPassword());
            user.setStatus(UserConstants.USER_STATUS_ENABLE);
            user.setType(UserConstants.USER_TYPE_NORMAL);
            userService.insertUser(user, UserConstants.CP_QUERY);
            record.setUserId(user.getUserId());
        }
        return partnerInfoAdapter.insert(record);
    }

    @Override
    public int update(PartnerInfo record) {
        if (record.getUserId() != null) {
            User user = userService.findById(record.getUserId());
            if (user != null) {
                user.setPassword(record.getPassword());
                userService.updateUser(user);
            }
        }
        return partnerInfoAdapter.update(record);
    }

    @Override
    public int delete(PartnerInfo record) {
        return partnerInfoAdapter.delete(record);
    }

    @Override
    public PartnerInfo getPartnerInfoByUserId(Long userId) {
        if (userId == null) return null;
        return partnerInfoAdapter.getPartnerInfoByUserId(userId);
    }
}
