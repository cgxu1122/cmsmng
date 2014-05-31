package com.ifhz.core.mapper;

import com.ifhz.core.po.Resource;
import com.test.BaseTest;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;
import java.util.Map;


/**
 * @author radishlee
 */
public class ResourceMapperTest extends BaseTest {

    @javax.annotation.Resource
    ResourceMapper resourceMapper;

//    @Test
//    public void testGetAllResource() throws Exception {
//        List<Resource> list = resourceMapper.getAllResource();
//        System.out.println(list.size());
//    }

//    @Test
//    public void testFindRootResource() throws Exception {
//        Resource res =  resourceMapper.findRootResource();
//        System.out.println(res.getResName());
//    }
//
//    @Test
//    public void testFindAllChildrenById() throws Exception {
//        List<Resource> list = resourceMapper.findAllChildrenById(1);
//        for(Resource res:list){
//            System.out.println(res.getResName());
//            System.out.println(res.getResourceId());
//        }
//    }
//
//    @Test
//    public void testFindById() throws Exception {
//        Map map = resourceMapper.findById(1);
//        System.out.println(map.get("fullPath"));
//    }

//    @Test
//    public void testInsert() throws Exception {
//        Resource res = new Resource();
//        res.setFullPath("1111");
//        res.setLevels(1);
//        res.setParentId(1l);
//        res.setResName("aaaa");
//        res.setResUrl("111");
//        System.out.println(resourceMapper);
//        resourceMapper.insert(res);
//    }
//
//    @Test
//    public void testFindByResName() throws Exception {
//        Resource res = resourceMapper.findByResName(null,"111",3l);
//        System.out.println(res.getResourceId());
//    }
//
//    @Test
//    public void testUpdateResFullPath() throws Exception {
//        Resource res = resourceMapper.findByResName(null,"111",3l);
//        res.setFullPath("ssss");
//        resourceMapper.updateResFullPath(res);
//    }
//
//    @Test
//    public void testFindByResNameBesideSelf() throws Exception {
//        Resource res = resourceMapper.findByResNameBesideSelf(null,"aaaa",1);
//        System.out.println(res);
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        Resource res = resourceMapper.findByResNameBesideSelf(null,"aaaa",1);
//        res.setResUrl("res");
//        res.setResName("res");
//        int cnt = resourceMapper.update(res);
//        System.out.println(cnt);
//    }

//    @Test
//    public void testDelete() throws Exception {
//        int cnt = resourceMapper.delete(5);
//    }
//
    @Test
    public void testFindFullpathByRoleId() throws Exception {
        System.out.println(resourceMapper.findFullpathByRoleId(3l));
    }
}
