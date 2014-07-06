package com.ifhz.core.mapper;

import com.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author radishlee
 */
public class RoleMapperTest extends BaseTest {

    @Autowired
    RoleMapper roleMapper;


//    public void testInsertRoleResRef() throws Exception {
//        List<RoleResourceRef> rrfList = new ArrayList<RoleResourceRef>();
//        RoleResourceRef rrf  = new RoleResourceRef();
//        rrf.setRoleId(1l);
//        rrf.setResourceId(1l);
//        rrf.setAcces(1);
//        rrfList.add(rrf);
//
//        RoleResourceRef rrf1  = new RoleResourceRef();
//        rrf.setRoleId(1l);
//        rrf.setResourceId(2l);
//        rrf.setAcces(1);
//        rrfList.add(rrf1);
//
//        int cnt = roleMapper.batchInsertRoleResRef(rrfList);
//        System.out.println(cnt);
//    }

//    public void testBatchInsertRoleResRef() throws Exception {
//
//    }
//    public void testQueryRoleInfoByRoleName() throws Exception {
//        roleMapper.queryRoleInfoByRoleName(null,"11111");
//    }
//
//    public void testGetResourceList() throws Exception {
//        List<Map> list = roleMapper.getResourceList();
//       System.out.println(list.size());
//    }


//    }


    //    @Test
//    public void testInsertRole() throws Exception {
//        Role role = new Role();
//        role.setFullPath("sssssss");
//        role.setParentId(2l);
//        role.setLevels(111);
//        role.setRoleName("11111");
//        int cnt = roleMapper.insertRole(role);
//        System.out.println(cnt);
//    }
//    @Test
//    public void testFindAllRole() throws Exception {
//        List<Role> roleList = roleMapper.findAllRole();
//        System.out.println(roleList.size());
//    @Test
//    public void testFindRootRole() throws Exception {
//        Role role = roleMapper.findRootRole();
//        System.out.println(role.getRoleName());
//    }
//    @Test
//    public void testFindAllChildrenById() throws Exception {
//        List<Role> roleList = roleMapper.findAllChildrenById(2);
//        System.out.println(roleList.size());
//    }
//    @Test
//    public void testFindById() throws Exception {
//        Map map = roleMapper.findById(3);
//        System.out.println(map);
//    }
//    @Test
//    public void testGetAdminRole() throws Exception {
//        Role role = roleMapper.getAdminRole();
//        System.out.println(role.getRoleId());
//    }
//    @Test
//    public void testFindParentById() throws Exception {
//        Role role =  roleMapper.findParentById(null,3l);
//       // System.out.println(role);
//       log(role);
//    }
//
//    @Test
//    public void testFindByRoleName() throws Exception {
//        Role role =  roleMapper.findByRoleName("111112");
//       log(role);
//    }
//    @Test
//    public void testFindByRoleNameBesideSelf() throws Exception {
//        Role role = roleMapper.findByRoleNameBesideSelf(null,"11111",3);
//        log(role);
//    }
//    @Test
//    public void testUpdateRoleName() throws Exception {
//        int cnt = roleMapper.saveFullPathAndType(3l,"pathaaaa");
//        log(cnt);
//    }
//    @Test
//    public void testUpdateRole() throws Exception {
//        Role role = roleMapper.findByRoleName("11111");
//        role.setRoleName("fffffff");
//        int cnt = roleMapper.updateRole(role);
//        log(cnt);
//    }

    @Test
    public void testDelete() throws Exception {
        int cnt = roleMapper.delete(3l);
        log(cnt);
    }


}
