package com.ifhz.core.mapper;

import com.ifhz.core.base.page.Pagination;
import com.ifhz.core.po.User;
import com.ifhz.core.util.MD5keyUtil;
import com.ifhz.core.vo.UserVo;
import com.test.BaseTest;
import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author radishlee
 */
public class UserMapperTest extends BaseTest {
    @Autowired
    UserMapper userMapper;

//    @Test
//    public void testInsertUser() throws Exception {
//        User user = new User();
//        user.setAddress("aaaaaaa");
//        user.setCellphone("11111");
//        user.setLoginName("aaaaaaaaa");
//        user.setPassword(MD5keyUtil.getMD5Str("222222"));
//        user.setRealName("AAAAAAAA");
//        user.setStatus(1);
//        user.setType(1);
//        int cnt = userMapper.insertUser(user);
//        log(cnt);
//    }
//        @Test
//    public void testFindUser() throws Exception {
//        User user = userMapper.findById(2l);
//        log(user);
//    }
//
//    @Test
//    public void testUpdateUserAdmin() throws Exception {
//        User user = userMapper.findById(2l);
//        user.setRealName("fffff");
//        user.setCellphone("ffffffffff");
//        user.setStatus(2);
//        user.setAddress("ffffffffffffff");
//        int cnt = userMapper.updateUserAdmin(user);
//        log(cnt);
//    }
//
//    @Test
//    public void testUpdateUser() throws Exception {
//        User user = userMapper.findById(2l);
//        user.setCellphone("ggggggg");
//        user.setAddress("gggggg");
//        int cnt = userMapper.updateUser(user);
//        log(cnt);
//    }
//    @Test
//    public void testUpdateUserPassword() throws Exception {
//        User user = userMapper.findById(2l);
//        user.setPassword(MD5keyUtil.getMD5Str("222222"));
//        int cnt = userMapper.updateUserPassword(user.getUserId(),user.getPassword());
//        log(cnt);
//    }
//
//    @Test
//    public void testDeleteUser() throws Exception {
//        int cnt = userMapper.deleteUser(3);
//        log(cnt);
//    }

//    @Test
//    public void testFindAllUser() throws Exception {
//        UserVo userVo = new UserVo();
//        userVo.setLoginName("aaaaaaaaa");
//        Pagination page = new Pagination();
//        page.setCurrentPage(1);
//        page.setPageSize(10);
//        List<UserVo> list =  userMapper.queryAllUser(page, userVo);
//        log(list.size());
//    }

//    @Test
//    public void testGetUserVoCount() throws Exception {
//        UserVo userVo = new UserVo();
//        userVo.setLoginName("aaaaaaaaa");
//        Pagination page = new Pagination();
//        page.setCurrentPage(1);
//        page.setPageSize(10);
//        long cnt = userMapper.queryUserVoCount(page,userVo);
//        log(cnt);
//    }

//    }

    //    @Test
//    public void testFindUserByLoginName() throws Exception {
//        User user = userMapper.findUserByLoginName("aaaaaaaaa");
//        log(user);

//    @Test
//    public void testFindUserByCondition() throws Exception {
//        UserVo userVo = new UserVo();
//        userVo.setLoginName("aaaaaaaaa2");
//        Pagination page = new Pagination();
//        page.setCurrentPage(1);
//        page.setPageSize(10);
//        List<User> list = userMapper.findUserByCondition(page,userVo);
//        log(list);
//    }

//    @Test
//    public void testFindUserByRoleId() throws Exception {
//        List<User> user = userMapper.findUserByRoleId(2l);
//        log(user);
//    }

//   @Test
//    public void testFindUserByLoginNameAndNotId() throws Exception {
//      log(userMapper.findUserByLoginNameAndNotId(null,"aaaaaaaaa2",3));
//    }


}
