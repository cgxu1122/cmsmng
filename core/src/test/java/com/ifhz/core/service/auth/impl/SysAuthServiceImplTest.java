package com.ifhz.core.service.auth.impl;

import com.ifhz.core.mapper.RoleResourceRefMapper;
import com.ifhz.core.po.RoleResourceRef;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author radishlee
 */
public class SysAuthServiceImplTest extends BaseTest {

    @Resource
    RoleResourceRefMapper roleResourceRefMapper;

    @Test
    public void testInsert() throws Exception {
        RoleResourceRef ref = new RoleResourceRef();
        ref.setResourceId(3L);
        ref.setRoleId(3L);
        ref.setAcces(1);
        ref.setCreateTime(new Date());
        roleResourceRefMapper.insert(ref);
        System.out.println(1);
    }

    @Test
    public void testDeleteAllRefByResId() throws Exception {
        roleResourceRefMapper.deleteAllRefByResId(1);
    }

    @Test
    public void testDeleteAllRefByRoleId() throws Exception {
        roleResourceRefMapper.deleteAllRefByRoleId(2);
    }

    @Test
    public void testFindAllUser() throws Exception {
        List<RoleResourceRef> refList = roleResourceRefMapper.getRoleIdAndResourceId(null, 3, 3);
        for (RoleResourceRef rrr : refList) {
            System.out.println(rrr.getId());
        }
    }
}
