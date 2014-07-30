package com.ifhz.core.service.partner.impl;

import com.ifhz.core.adapter.PartnerInfoAdapter;
import com.ifhz.core.base.commons.anthrity.UserConstants;
import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.constants.Active;
import com.ifhz.core.po.PartnerInfo;
import com.ifhz.core.po.auth.SysUser;
import com.ifhz.core.service.auther.SysUserService;
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
    @Resource(name = "sysUserService")
    private SysUserService sysUserService;


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
            SysUser user = new SysUser();
            user.setLoginName(record.getUsername());
            user.setRealName(record.getUsername());
            user.setPassword(record.getPassword());
            user.setActive(Active.Y.dbValue);
            user.setRoleId(UserConstants.CP_QUERY);
            sysUserService.insert(user);
            record.setUserId(user.getUserId());
        }
        return partnerInfoAdapter.insert(record);
    }

    @Override
    public int update(PartnerInfo record) {
        if (record.getUserId() != null) {
            SysUser user = sysUserService.getById(record.getUserId());
            if (user != null) {
                user.setPassword(record.getPassword());
                sysUserService.updatePassword(user);
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
