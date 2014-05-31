package com.ifhz.core.mapper;

import com.ifhz.core.po.UserRoleRef;
import com.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author radishlee
 */
public class UserRoleRefMapperTest extends BaseTest {

    @Resource
    UserRoleRefMapper userRoleRefMapper;

//    @Test
//    public void testFindRoleIdByUserId() throws Exception {
//        List<UserRoleRef> list = userRoleRefMapper.findRoleIdByUserId(3l);
//        System.out.println(list.size());
//    }
//
//    @Test
//    public void testDeleteAllRefByRoleId() throws Exception {
//        int cnt = userRoleRefMapper.deleteAllRefByRoleId(1l);
//        System.out.println(cnt);
//    }
//
//
//    @Test
//    public void testDeleteAllRefByUserId() throws Exception {
//        int cnt = userRoleRefMapper.deleteAllRefByUserId(1l);
//        System.out.println(cnt);
//    }

//    @Test
//    public void testInsert() throws Exception {
//        UserRoleRef urf = new UserRoleRef();
//        urf.setRoleId(1l);
//        urf.setUserId(1l);
//        userRoleRefMapper.insert(urf);
//    }
}
